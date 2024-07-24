package v2.sideproject.store.user.repository.jooq.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.repository.jooq.UsersRepositoryCustom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static v2.sideproject.store.tables.Roles.*;
import static v2.sideproject.store.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsersRepositoryImpl implements UsersRepositoryCustom {
    private final DSLContext dsl;

    @Override
    public UsersDto saveUsers(UsersDto usersDto) {
//        dsl.insertInto(USERS)
//                .values(USERS.EMAIL,USERS.PASSWORD,USERS.BIRTH );
//

        return null;
    }

    @Override
    public Optional<UsersDto> findByEmail(String email) {
        Record record = dsl.select(USERS.EMAIL)
                .from(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne();
        return Optional.ofNullable(record)
                .map(r -> UsersDto.builder().email(r.get(USERS.EMAIL)).build());
    }

    @Override
    public Optional<UsersDto> findByEmailWithRole(String email) {
        SelectField<?>[] selectFields = new SelectField<?>[]{
                USERS.USER_ID,
                USERS.EMAIL,
                USERS.PASSWORD,
                ROLES.ROLE_ID,
                ROLES.NAME,
        };
        Record result = dsl.select(selectFields)
                .from(USERS)
                .join(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(USERS.EMAIL.eq(email))
                .fetchOne();
        return Optional.ofNullable(result).map(r ->  UsersDto.builder()
                .userId(r.get(USERS.USER_ID))
                .password(r.get(USERS.PASSWORD))
                .email(r.get(USERS.EMAIL))
                .roles(RolesDto.builder()
                        .roleId(r.get(ROLES.ROLE_ID))
                        .name(RolesName.valueOf(r.get(ROLES.NAME)))
                        .build())
                .build());
    }

    @Override
    public Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {
        log.info("usersSearchParamsDto : {}", usersSearchParamsDto);
        SelectField<?>[] selectFields = new SelectField<?>[]{
                USERS.EMAIL,
                USERS.NAME,
                USERS.BIRTH,
                USERS.GENDER,
                USERS.STATUS,
                USERS.MOBILE_CARRIER,
                USERS.PHONE,
                ROLES.NAME
        };

        Result<Record> result = dsl.select(selectFields)
                .from(USERS)
                .leftJoin(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(
                        USERS.NAME.eq(usersSearchParamsDto.getName())
                                .or(USERS.BIRTH.eq(usersSearchParamsDto.getBirth()))
                )
                .orderBy(USERS.USER_ID)
                .limit(pageable.getPageSize())
                .offset((int) pageable.getOffset())
                .fetch();


        List<UsersDto> users = result.stream()
                .map(record -> UsersDto.builder()
                        .email(record.get(USERS.EMAIL))
                        .name(record.get(USERS.NAME))
                        .birth(record.get(USERS.BIRTH))
                        .gender(Gender.valueOf(record.get(USERS.GENDER)))
                        .status(UsersStatus.valueOf(record.get(USERS.STATUS)))
                        .mobileCarrier(MobileCarrier.valueOf(record.get(USERS.MOBILE_CARRIER)))
                        .phone(record.get(USERS.PHONE))
                        .roles(RolesDto.builder()
                                .name(RolesName.valueOf(record.get(ROLES.NAME)))
                                .build())
                        .build()
                )
                .collect(Collectors.toList());

        Integer count = dsl.selectCount()
                .from(USERS)
                .leftJoin(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .where(
                        USERS.NAME.eq(usersSearchParamsDto.getName())
                                .or(USERS.BIRTH.eq(usersSearchParamsDto.getBirth()))
                )
                .fetchOne(0, Integer.class);
        count = (count != null) ? count : 0;

        return new PageImpl<>(users, pageable, count);
    }

}
