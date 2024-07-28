package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.exception.PasswordMismatchException;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.redis.utils.RestPage;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.vo.request.UsersModifyInfoRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.vo.response.UsersOneDetailResponse;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.userDetails.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final CustomUserDetails customUserDetails;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUsers(UsersRegisterRequest usersRegisterRequest) {
        checkPassword(usersRegisterRequest);
        checkDuplicatedEmail(usersRegisterRequest);

        var roles = rolesRepository.findByName(RolesName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        String encodedPassword = passwordEncoder.encode(usersRegisterRequest.getPassword());
        Long userId = usersRepository.saveUsers(usersRegisterRequest, encodedPassword, roles);
        usersRepository.saveAddresses(usersRegisterRequest, userId);
    }

    @Override
    @Cacheable(value = "fetchAllUserByParams", keyGenerator = "customKeyGenerator")
    @Transactional(readOnly = true)
    public RestPage<UsersDetailsResponse> fetchAllUsersDetails(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable) {
        Page<UsersDto> usersDetailsByParams = usersRepository.findAllUsersDetailsByParams(usersSearchParamsDto, pageable);

        List<UsersDetailsResponse> usersDetailsResponseList = usersDetailsByParams.getContent()
                .stream()
                .map(record -> UsersDetailsResponse.builder()
                        .email(record.getEmail())
                        .name(record.getName())
                        .birth(record.getBirth())
                        .status(record.getStatus())
                        .gender(record.getGender())
                        .mobileCarrier(record.getMobileCarrier())
                        .phone(record.getPhone())
                        .roleName(record.getRoles() != null ? record.getRoles().getName() : null)
                        .build()
                )
                .toList();
        Page<UsersDetailsResponse> usersDetailsResponsePage = new PageImpl<>(usersDetailsResponseList, pageable, usersDetailsByParams.getTotalElements());

        return new RestPage<>(usersDetailsResponsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public UsersOneDetailResponse getOneUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());

        log.info("usersDetails id : {}", userDetails.getUsername());
        String email = userDetails.getUsername();
        UsersDto userInfo = usersRepository.findOneUsersInfo(email);

        return UsersOneDetailResponse.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .mobileCarrier(userInfo.getMobileCarrier())
                .phone(userInfo.getPhone())
                .build();
    }

    @Override
    @Transactional
    public void modifyUsersInfo(UsersModifyInfoRequest usersModifyInfoRequest) {

    }


    private void checkDuplicatedEmail(UsersRegisterRequest usersRegisterRequest) {
        Optional<UsersDto> checkDuplicated = usersRepository.findByEmail(usersRegisterRequest.getEmail());
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
