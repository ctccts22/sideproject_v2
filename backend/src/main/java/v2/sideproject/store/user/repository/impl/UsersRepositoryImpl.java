package v2.sideproject.store.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.search.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.repository.UsersRepositoryCustom;

import java.util.List;
import java.util.Optional;

import static v2.sideproject.store.user.entity.QUsers.users;
import static v2.sideproject.store.user.entity.QRoles.roles;

public class UsersRepositoryImpl implements UsersRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UsersRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    // .. query
    @Override
    public Optional<Users> findByEmailWithRole(String email) {
        Users findByEmailWithRole = queryFactory
                .selectFrom(users)
                .leftJoin(users.roles, roles)
                .fetchJoin()
                .where(users.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(findByEmailWithRole);
    }

    @Override
    public Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {

//        List<UsersDto> content = queryFactory
//                .select(Projections.fields(UsersDto.class,
//                        users.email.as("email"),
//                        users.name.as("name")
//                .from()
//                .where()
        return null;
    }
}
