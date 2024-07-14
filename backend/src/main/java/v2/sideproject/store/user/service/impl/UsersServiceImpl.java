package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                .birth(usersDetailsRequestVo.getBirth())
                .gender(usersDetailsRequestVo.getGender())
                .status(UsersStatus.PENDING)
                .mobileCarrier(usersDetailsRequestVo.getMobileCarrier())
                .phone(usersDetailsRequestVo.getPhone())
                .build();

        Roles roles = rolesRepository.findByName(RolesName.CUSTOMER) // default value
                .orElseThrow(() -> new IllegalStateException("Default role not found"));


        Users users = UsersMapper.mapToUsersDetailsResponseVo(checkUserVo, roles);
        log.info("user : {} ", users.toString());

        usersRepository.save(users);
    }

    @Override
    public Page<UsersDetailsResponseVo> fetchAllUsersDetails() {
        return null;
    }
}
