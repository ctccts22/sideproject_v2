package v2.sideproject.store.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.dto.response.UsersRegisterResponseDto;
import v2.sideproject.store.user.dto.search.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.repository.UsersRepositoryCustom;

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
    public Page<UsersRegisterResponseDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {

//        List<UsersRegisterResponseDto> content = queryFactory
//                .select(Projections.fields(UsersRegisterResponseDto.class,
//                        users.email.as("email"),
//                        users.name.as("name"),
//                        users.
//                        ))
//                .from()
//                .where()
        return null;
    }
}
