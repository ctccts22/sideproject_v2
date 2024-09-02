package v2.sideproject.store.user.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import v2.sideproject.store.common.jooq.JooqStringConditionUtils;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.repository.RolesRepository;

import java.util.Optional;

import static v2.sideproject.store.tables.Roles.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RolesRepositoryImpl implements RolesRepository {
    private final DSLContext dsl;


    @Override
    public Optional<RolesDto> findByName(RolesName rolesName) {
        return dsl.select(ROLES.ROLE_ID).from(ROLES).where(JooqStringConditionUtils.eqIfNotBlank(ROLES.NAME, String.valueOf(rolesName))).fetchOptionalInto(RolesDto.class);
    }
}
