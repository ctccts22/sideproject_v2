package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.exception.PasswordMismatchException;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.redis.config.RestPage;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.models.condition.UsersOrderCondition;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.response.AddressesResponse;
import v2.sideproject.store.user.models.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.response.UsersRegisterResponse;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.AddressesMapper;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Override
    @Transactional
    public void createUsers(UsersRegisterRequest usersRegisterRequest) {
        checkPassword(usersRegisterRequest);
        checkDuplicatedEmail(usersRegisterRequest);
        // response
        var checkUserDto = UsersRegisterResponse.builder()
                .email(usersRegisterRequest.getEmail())
                .password(passwordEncoder.encode(usersRegisterRequest.getPassword()))
                .name(usersRegisterRequest.getName())
                .birth(usersRegisterRequest.getBirth())
                .gender(usersRegisterRequest.getGender())
                .status(UsersStatus.PENDING)
                .mobileCarrier(usersRegisterRequest.getMobileCarrier())
                .phone(usersRegisterRequest.getPhone())
                .build();

        var roles = rolesRepository.findByName(RolesName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        var users = UsersMapper.mapToUsersDetailsResponseDto(checkUserDto, roles);
        log.info("user : {} ", users.toString());

        var savedUsers = usersRepository.save(users);


        AddressesResponse addressesResponse = AddressesResponse.builder()
                .userId(savedUsers.getUserId())
                .mainAddress(usersRegisterRequest.getAddress().getMainAddress())
                .subAddress(usersRegisterRequest.getAddress().getSubAddress())
                .zipCode(usersRegisterRequest.getAddress().getZipCode())
                .phone(usersRegisterRequest.getAddress().getPhone())
                .build();

        AddressesMapper.mapToAddresses(addressesResponse);
    }

    @Override
    @Cacheable(value = "fetchAllUserByParams", keyGenerator = "customKeyGenerator")
    public RestPage<UsersDetailsResponse> fetchAllUsersDetails(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable, UsersOrderCondition usersOrderCondition) {

        Page<UsersDto> usersDetailsByParams = usersRepository.findAllUsersDetailsByParams(usersSearchParamsDto, pageable, usersOrderCondition);

        List<UsersDetailsResponse> usersDetailsResponseList = usersDetailsByParams.getContent()
                .stream()
                .map(UsersMapper::mapFromUsersDtoToUsersDetailsResponse)
                .toList();
        Page<UsersDetailsResponse> usersDetailsResponsePage = new PageImpl<>(usersDetailsResponseList, pageable, usersDetailsByParams.getTotalElements());

        return new RestPage<>(usersDetailsResponsePage);
    }


    private void checkDuplicatedEmail(UsersRegisterRequest usersRegisterRequest) {
        Optional<Users> checkDuplicated = usersRepository.findByEmail(usersRegisterRequest.getEmail());
        if (checkDuplicated.isPresent()) {
            throw new UsersAlreadyExistsException(UsersConstants.DUPLICATED_EMAIL);
        }
    }

    private void checkPassword(UsersRegisterRequest usersRegisterRequest) {
        if (!usersRegisterRequest.getCheckPassword().equals(usersRegisterRequest.getPassword())) {
            throw new PasswordMismatchException(UsersConstants.PASSWORD_MISMATCH);
        }
    }
}
