package v2.sideproject.store.users.repository;

import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import v2.sideproject.store.tables.Roles;
import v2.sideproject.store.tables.Users;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.repository.UsersRepositoryCustoms;
import v2.sideproject.store.user.repository.impl.UsersRepositoryImple;

import java.util.Optional;

import static v2.sideproject.store.tables.Users.*;

@JooqTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryCustomsTest {

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

}
