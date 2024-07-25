package v2.sideproject.store.user.repository.jooq.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import v2.sideproject.store.converter.DateFormatter;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;
import v2.sideproject.store.user.repository.jooq.UsersRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static v2.sideproject.store.jooq.JooqStringConditionUtils.*;
import static v2.sideproject.store.tables.Roles.*;
import static v2.sideproject.store.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsersRepositoryImpl implements UsersRepository {
    private final DSLContext dsl;

    @Override
    public Long saveUsers(UsersRegisterRequest usersRegisterRequest, RolesDto rolesDto) {
        String formattedCreatedAt = DateFormatter.format(LocalDateTime.now());

        return dsl.insertInto(USERS)
                .columns(
                        USERS.EMAIL,
                        USERS.PASSWORD,
                        USERS.NAME,
                        USERS.BIRTH,
                        USERS.GENDER,
                        USERS.STATUS,
                        USERS.MOBILE_CARRIER,
                        USERS.PHONE,
                        USERS.CREATED_AT,
                        USERS.ROLE_ID
                )
                .values(
                        usersRegisterRequest.getEmail(),
                        usersRegisterRequest.getPassword(),
                        usersRegisterRequest.getName(),
                        usersRegisterRequest.getBirth(),
                        String.valueOf(usersRegisterRequest.getGender()),
                        String.valueOf(usersRegisterRequest.getStatus()),
                        String.valueOf(usersRegisterRequest.getMobileCarrier()),
                        usersRegisterRequest.getPhone(),
                        formattedCreatedAt,
                        rolesDto.getRoleId()
                )
                .returningResult(USERS.USER_ID)
                .fetchOneInto(Long.class);
    }

    @Override
    public Optional<UsersDto> findByEmail(String email) {
        return dsl.select(USERS).from(USERS).where(eqIfNotBlank(USERS.EMAIL, email)).fetchOptionalInto(UsersDto.class);
    }

    @Override
    public Optional<UsersDto> findByEmailWithRole(String email) {
        return dsl.select(USERS.USER_ID,
                        USERS.EMAIL,
                        USERS.PASSWORD,
                        ROLES.ROLE_ID.as("roles.roleId"),
                        ROLES.NAME.as("roles.name"))
                .from(USERS)
                .join(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(USERS.EMAIL.eq(email))
                .fetchOptionalInto(UsersDto.class);
    }

    @Override
    public Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {
        SelectField<?>[] selectFields = new SelectField<?>[]{
                USERS.EMAIL,
                USERS.NAME,
                USERS.BIRTH,
                USERS.GENDER,
                USERS.STATUS,
                USERS.MOBILE_CARRIER,
                USERS.PHONE,
                ROLES.NAME.as("roles.name")
        };
        // dynamic orderBy method
        OrderField<?>[] orderByFields = buildOrderBy(pageable);

        List<UsersDto> result = dsl.select(selectFields)
                .from(USERS)
                .leftJoin(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(
                        eqIfNotBlank(USERS.NAME, usersSearchParamsDto.getName())
                                .or(eqIfNotBlank(USERS.BIRTH, usersSearchParamsDto.getBirth()))
                )
                .orderBy(orderByFields)
                .limit(pageable.getPageSize())
                .offset((int) pageable.getOffset())
                .fetchInto(UsersDto.class);

        Integer count = dsl.selectCount()
                .from(USERS)
                .leftJoin(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(
                        eqIfNotBlank(USERS.NAME, usersSearchParamsDto.getName())
                                .or(eqIfNotBlank(USERS.BIRTH, usersSearchParamsDto.getBirth()))
                )
                .fetchOne(0, Integer.class);

        count = (count != null) ? count : 0;
        return new PageImpl<>(result, pageable, count);
    }

    private OrderField<?>[] buildOrderBy(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return new OrderField<?>[]{USERS.USER_ID.asc()};
        }

        List<OrderField<?>> orderFields = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            Field<?> field;
            switch (order.getProperty()) {
                case "email":
                    field = USERS.EMAIL;
                    break;
                case "name":
                    field = USERS.NAME;
                    break;
                case "birth":
                    field = USERS.BIRTH;
                    break;
                case "gender":
                    field = USERS.GENDER;
                    break;
                case "status":
                    field = USERS.STATUS;
                    break;
                case "mobileCarrier":
                    field = USERS.MOBILE_CARRIER;
                    break;
                case "phone":
                    field = USERS.PHONE;
                    break;
                case "roleName":
                    field = ROLES.NAME;
                    break;
                default:
                    field = USERS.USER_ID;
            }

            orderFields.add(order.isAscending() ? field.asc() : field.desc());
        });

        return orderFields.toArray(new OrderField<?>[0]);
    }

}
