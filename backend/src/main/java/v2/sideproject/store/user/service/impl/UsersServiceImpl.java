package v2.sideproject.store.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
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
import v2.sideproject.store.user.models.vo.request.ConfirmChangesRequest;
import v2.sideproject.store.user.models.vo.request.EmailVerificationRequest;
import v2.sideproject.store.user.models.vo.request.UpdateUserInfoRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.vo.response.UsersOneDetailResponse;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.UsersService;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.userDetails.CustomUserDetails;

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
        String email = getEmail();
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
    public void modifyUsersEmail(EmailVerificationRequest request) {
        String existingEmail = getEmail();

        // Perform SMTP verification (implementation depends on your SMTP library)
//        boolean isEmailValid = smtpVerify(newEmail);
//        if (isEmailValid) {
//            String verificationCode = generateVerificationCode();
//            emailService.sendVerificationEmail(newEmail, verificationCode);
//
//            // Save the new email to a temporary table with a pending status
//            TemporaryUser tempUser = new TemporaryUser();
//            tempUser.setEmail(newEmail);
//            tempUser.setVerificationCode(verificationCode);
//            tempUser.setStatus("PENDING");
//            temporaryUserRepository.save(tempUser);
//        } else {
//            throw new IllegalArgumentException("Invalid email address");
//        }
    }

//    private boolean smtpVerify(String email) {
//        // Implement SMTP verification logic here
//        return true; // Placeholder
//    }
//
//    private String generateVerificationCode() {
//        // Generate a random verification code
//        return UUID.randomUUID().toString();
//    }
//    }

    @Override
    public void modifyUsersInfo(UpdateUserInfoRequest request) {
//        TemporaryUser tempUser = temporaryUserRepository.findByName(name);
//        if (tempUser == null) {
//            tempUser = new TemporaryUser();
//        }
//        tempUser.setName(name);
//        tempUser.setMobileCarrier(mobileCarrier);
//        tempUser.setPhone(phone);
//        tempUser.setStatus("PENDING");
//        temporaryUserRepository.save(tempUser);
    }

    @Override
    public void confirmModifyUsersInfo(ConfirmChangesRequest request) {
//        TemporaryUser tempUser = temporaryUserRepository.findByVerificationCode(verificationCode);
//        if (tempUser != null && "PENDING".equals(tempUser.getStatus())) {
//            // Move changes from temporary table to main user table
//            User user = userRepository.findById(tempUser.getId()).orElse(new User());
//            user.setEmail(tempUser.getEmail());
//            user.setName(tempUser.getName());
//            user.setMobileCarrier(tempUser.getMobileCarrier());
//            user.setPhone(tempUser.getPhone());
//            userRepository.save(user);
//
//            // Mark the temporary record as confirmed
//            tempUser.setStatus("CONFIRMED");
//            temporaryUserRepository.save(tempUser);
//        } else {
//            throw new IllegalArgumentException("Invalid verification code");
//        }
    }

    @Override
    public void rollbackChanges() {
//        // Discard the changes in the temporary table
//        List<TemporaryUser> tempUsers = temporaryUserRepository.findByStatus("PENDING");
//        temporaryUserRepository.deleteAll(tempUsers);
    }


    // internal method
    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());

        log.info("usersDetails id : {}", userDetails.getUsername());
        return userDetails.getUsername();
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
