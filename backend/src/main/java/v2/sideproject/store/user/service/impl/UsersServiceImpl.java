package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.exception.PasswordMismatchException;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.dto.request.AddressesRequestDto;
import v2.sideproject.store.user.dto.response.AddressesResponseDto;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.AddressesMapper;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.dto.request.UsersDetailsRequestDto;
import v2.sideproject.store.user.dto.response.UsersDetailsResponseDto;

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
    public void createUsers(UsersDetailsRequestDto usersDetailsRequestDto, AddressesRequestDto addressRequestDto) {
        checkPassword(usersDetailsRequestDto);
        checkDuplicatedEmail(usersDetailsRequestDto);
        // response
        var checkUserDto = UsersDetailsResponseDto.builder()
                .email(usersDetailsRequestDto.getEmail())
                .password(passwordEncoder.encode(usersDetailsRequestDto.getPassword()))
                .name(usersDetailsRequestDto.getName())
                .birth(usersDetailsRequestDto.getBirth())
                .gender(usersDetailsRequestDto.getGender())
                .status(UsersStatus.PENDING)
                .mobileCarrier(usersDetailsRequestDto.getMobileCarrier())
                .phone(usersDetailsRequestDto.getPhone())
                .build();

        var roles = rolesRepository.findByName(RolesName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        var users = UsersMapper.mapToUsersDetailsResponseDto(checkUserDto, roles);
        log.info("user : {} ", users.toString());

        var savedUsers = usersRepository.save(users);


        AddressesResponseDto addressesResponseDto = AddressesResponseDto.builder()
                .userId(savedUsers.getUserId())
                .mainAddress(addressRequestDto.getMainAddress())
                .subAddress(addressRequestDto.getSubAddress())
                .zipCode(addressRequestDto.getZipCode())
                .phone(addressRequestDto.getPhone())
                .build();

        AddressesMapper.mapToAddresses(addressesResponseDto);
    }

    @Override
    public Page<UsersDetailsResponseDto> fetchAllUsersDetails() {
        return null;
    }


    private void checkDuplicatedEmail(UsersDetailsRequestDto usersDetailsRequestDto) {
        Optional<Users> checkDuplicated = usersRepository.findByEmail(usersDetailsRequestDto.getEmail());
        if (checkDuplicated.isPresent()) {
            throw new UsersAlreadyExistsException(UsersConstants.DUPLICATED_EMAIL);
        }
    }
    private void checkPassword(UsersDetailsRequestDto usersDetailsRequestDto) {
        if (!usersDetailsRequestDto.getCheckPassword().equals(usersDetailsRequestDto.getPassword())) {
            throw new PasswordMismatchException(UsersConstants.PASSWORD_MISMATCH);
        }
    }
}
