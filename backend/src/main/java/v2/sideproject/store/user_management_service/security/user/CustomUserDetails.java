package v2.sideproject.store.user_management_service.security.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.repository.UsersRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetails implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername member ID : " + email);
        UsersDto usersDto = usersRepository.findByEmailWithRole(email)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자 입니다"));
        log.info("userdto: {}", usersDto);
        return new UserDetailsImpl(usersDto);
    }
}
