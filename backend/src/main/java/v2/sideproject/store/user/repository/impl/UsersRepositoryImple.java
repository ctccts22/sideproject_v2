package v2.sideproject.store.user.repository.impl;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import v2.sideproject.store.tables.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.models.condition.UsersOrderCondition;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.repository.UsersRepositoryCustom;
import v2.sideproject.store.user.repository.UsersRepositoryCustoms;

import java.util.Optional;

import static v2.sideproject.store.tables.Roles.*;
import static v2.sideproject.store.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImple implements UsersRepositoryCustoms {
    private final DSLContext dsl;
    @Override
    public Optional<UsersDto> findByEmailWithRole(String email) {
        Record sql = dsl.select()
                .from(USERS)
                .join(Roles.ROLES).on(USERS.ROLE_ID.eq(Roles.ROLES.ROLE_ID))
                .where(USERS.EMAIL.eq(email))
                .fetchOne();
        if (sql ==null) {
            throw new RuntimeException("");
        }
        UsersDto usersDto = UsersMapper.mapRecordToUser(sql);
        return Optional.of(usersDto);
    }

}
