package v2.sideproject.store.users.repository;

import org.jooq.*;
import org.jooq.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.tables.Roles;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;

import java.util.List;
import java.util.stream.Collectors;

import static v2.sideproject.store.tables.Roles.ROLES;
import static v2.sideproject.store.tables.Users.*;

@JooqTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTest {

    @Autowired
    private DSLContext dsl;

    @Test
    void testFindByEmailWithRole_UserExists() {
        String email = "test@test.com";
        Record sql = dsl.select(USERS.EMAIL)
                .from(USERS)
                .join(Roles.ROLES).on(USERS.ROLE_ID.eq(Roles.ROLES.ROLE_ID))
                .where(USERS.EMAIL.eq(email))
                .fetchOne();
        System.out.println(sql);
    }

    @Test
    void testFindAllUsersDetailsByParams() {
        Pageable pageable = PageRequest.of(0,10);
        // Define the select fields
        SelectField<?>[] selectFields = new SelectField<?>[]{
                USERS.EMAIL,
                USERS.NAME,
                USERS.BIRTH,
                ROLES.NAME
        };

        // Fetch the data
        Result<Record> result = dsl.select(selectFields)
                .from(USERS)
                .leftJoin(ROLES).on(USERS.ROLE_ID.eq(ROLES.ROLE_ID))
                .limit(pageable.getPageSize())
                .offset((int) pageable.getOffset())
                .fetch();

        // Map the result to DTOs
        List<UsersDto> users = result.stream()
                .map(record -> UsersDto.builder()
                        .email(record.get(USERS.EMAIL))
                        .name(record.get(USERS.NAME))
                        .birth(record.get(USERS.BIRTH))
                        .roles(RolesDto.builder()
                                .name(RolesName.valueOf(record.get(ROLES.NAME)))
                                .build())
                        .build()
                )
                .collect(Collectors.toList());

        Integer total = dsl.selectCount()
                .from(USERS)
                .fetchOne(0, Integer.class);
        if (total == null) {
            throw new RuntimeException("Total not found");
        }
    }

}
