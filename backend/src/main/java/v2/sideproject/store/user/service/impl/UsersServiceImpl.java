package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.exception.PasswordMismatchException;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.redis.config.RestPage;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.dto.response.AddressesResponseDto;
import v2.sideproject.store.user.dto.response.UsersRegisterResponseDto;
import v2.sideproject.store.user.dto.search.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.AddressesMapper;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.dto.request.UsersRegisterRequestDto;

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
    public void createUsers(UsersRegisterRequestDto usersRegisterRequestDto) {
        checkPassword(usersRegisterRequestDto);
        checkDuplicatedEmail(usersRegisterRequestDto);
        // response
        var checkUserDto = UsersRegisterResponseDto.builder()
                .email(usersRegisterRequestDto.getEmail())
                .password(passwordEncoder.encode(usersRegisterRequestDto.getPassword()))
                .name(usersRegisterRequestDto.getName())
                .birth(usersRegisterRequestDto.getBirth())
                .gender(usersRegisterRequestDto.getGender())
                .status(UsersStatus.PENDING)
                .mobileCarrier(usersRegisterRequestDto.getMobileCarrier())
                .phone(usersRegisterRequestDto.getPhone())
                .build();

        var roles = rolesRepository.findByName(RolesName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        var users = UsersMapper.mapToUsersDetailsResponseDto(checkUserDto, roles);
        log.info("user : {} ", users.toString());

        var savedUsers = usersRepository.save(users);


        AddressesResponseDto addressesResponseDto = AddressesResponseDto.builder()
                .userId(savedUsers.getUserId())
                .mainAddress(usersRegisterRequestDto.getAddress().getMainAddress())
                .subAddress(usersRegisterRequestDto.getAddress().getSubAddress())
                .zipCode(usersRegisterRequestDto.getAddress().getZipCode())
                .phone(usersRegisterRequestDto.getAddress().getPhone())
                .build();

        AddressesMapper.mapToAddresses(addressesResponseDto);
    }

    @Override
    @Cacheable(value = "fetchAllUserByParams", keyGenerator = "customKeyGenerator")
    public RestPage<UsersRegisterResponseDto> fetchAllUsersDetails(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {
        Page<UsersRegisterResponseDto> usersDetailsByParams = usersRepository.findAllUsersDetailsByParams(usersSearchParamsDto, pageable);
        return new RestPage<>(usersDetailsByParams);
    }


    private void checkDuplicatedEmail(UsersRegisterRequestDto usersRegisterRequestDto) {
        Optional<Users> checkDuplicated = usersRepository.findByEmail(usersRegisterRequestDto.getEmail());
        if (checkDuplicated.isPresent()) {
            throw new UsersAlreadyExistsException(UsersConstants.DUPLICATED_EMAIL);
        }
    }

    private void checkPassword(UsersRegisterRequestDto usersRegisterRequestDto) {
        if (!usersRegisterRequestDto.getCheckPassword().equals(usersRegisterRequestDto.getPassword())) {
            throw new PasswordMismatchException(UsersConstants.PASSWORD_MISMATCH);
        }
    }
}
