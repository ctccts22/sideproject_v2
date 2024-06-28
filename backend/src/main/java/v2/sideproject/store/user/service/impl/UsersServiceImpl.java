package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.company.repository.CompaniesRepository;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.response.UsersDetailsResponseVo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final CompaniesRepository companiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Override
    @Transactional
    public void createUsers(UsersDetailsRequestVo usersDetailsRequestVo) {
        // response
        var checkUserVo = UsersDetailsResponseVo.builder()
                .email(usersDetailsRequestVo.getEmail())
                .password(passwordEncoder.encode(usersDetailsRequestVo.getPassword()))
                .name(usersDetailsRequestVo.getName())
                .status(UsersStatus.DELETED)
                .department(usersDetailsRequestVo.getDepartment())
                .position(usersDetailsRequestVo.getPosition())
                .cellphone(usersDetailsRequestVo.getCellphone())
                .telephone(usersDetailsRequestVo.getTelephone())
                .companyName(usersDetailsRequestVo.getCompanyName())
                .parentCompany(usersDetailsRequestVo.getParentCompany())
                .address(usersDetailsRequestVo.getAddress())
                .build();

        Optional<Users> optionalUsers = usersRepository.findByCellphone(checkUserVo.getCellphone());
        if (optionalUsers.isPresent()) {
            throw new UsersAlreadyExistsException("Users already registered with given phone number");
        }

        // Check if the company already exists
        Optional<Companies> existingCompany = companiesRepository.findByName(checkUserVo.getCompanyName());
        Companies savedCompany;
        if (existingCompany.isPresent()) {
            savedCompany = existingCompany.get();
        } else {
            Companies companies = Companies.builder()
                    .name(checkUserVo.getCompanyName())
                    .address(checkUserVo.getAddress())
                    .build();
            try {
                savedCompany = companiesRepository.save(companies);
            } catch (Exception e) {
                log.error("Saved companies error", e);
                throw e;
            }
        }

        Roles roles = rolesRepository.findByName(RolesName.PENDING) // default value
                .orElseThrow(() -> new IllegalStateException("Default role not found"));


        Users users = UsersMapper.mapToUsersDetailsResponseVo(checkUserVo, roles, savedCompany);

        usersRepository.save(users);
    }

    @Override
    public Page<UsersDetailsResponseVo> fetchAllUsersDetails() {
        return null;
    }
}
