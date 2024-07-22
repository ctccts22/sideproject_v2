package v2.sideproject.store.user.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import v2.sideproject.store.user.models.condition.UsersOrderCondition;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.repository.UsersRepositoryCustom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static v2.sideproject.store.user.entity.QUsers.users;
import static v2.sideproject.store.user.entity.QRoles.roles;
import static v2.sideproject.store.user.entity.QAddresses.addresses;

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
    public Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable, UsersOrderCondition usersOrderCondition) {
        OrderSpecifier<?>[] orderSpecifiers = createOrderSpecifiers(usersOrderCondition);
        List<UsersDto> content = queryFactory
                .select(Projections.fields(UsersDto.class,
                        users.email.as("email"),
                        users.name.as("name"),
                        users.birth.as("birth"),
                        users.status.as("status"),
                        users.gender.as("gender"),
                        users.mobileCarrier.as("mobileCarrier"),
                        users.phone.as("phone"),
                        users.roles.name.as("roleName"),
                        users.addressesSet.as("addressesSet")
                ))
                .from(users)
                .orderBy(orderSpecifiers)
                .leftJoin(users.roles, roles)
                .leftJoin(users.addressesSet, addresses)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Users> count = queryFactory
                .select(users)
                .from(users);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }


    private OrderSpecifier<?>[] createOrderSpecifiers(UsersOrderCondition orderCondition) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (Objects.nonNull(orderCondition.getName())) {
            PathBuilder<String> path = new PathBuilder<>(String.class, users.getMetadata().getName() + ".name");
            orderSpecifiers.add(new OrderSpecifier<>(orderCondition.getOrderDirection(), path));
        }

//        if (Objects.nonNull(orderCondition.getOrderByField()) && Objects.nonNull(orderCondition.getOrderDirection())) {
//            PathBuilder<Object> path = new PathBuilder<>(users.getType(), users.getMetadata());
//            orderSpecifiers.add(new OrderSpecifier<>(orderCondition.getOrderDirection(), path.get(orderCondition.getOrderByField())));
//        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

}
