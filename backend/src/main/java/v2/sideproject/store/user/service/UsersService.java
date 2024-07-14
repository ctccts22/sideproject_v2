package v2.sideproject.store.user.service;

import org.springframework.data.domain.Page;
import v2.sideproject.store.user.dto.request.AddressesRequestDto;
import v2.sideproject.store.user.dto.request.UsersDetailsRequestDto;
import v2.sideproject.store.user.dto.response.UsersDetailsResponseDto;

public interface UsersService {
    void createUsers(UsersDetailsRequestDto usersDetailsRequestDto, AddressesRequestDto addressRequestDto);

    Page<UsersDetailsResponseDto> fetchAllUsersDetails();

}
