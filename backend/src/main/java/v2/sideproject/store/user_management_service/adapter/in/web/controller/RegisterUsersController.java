package v2.sideproject.store.user_management_service.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v2.sideproject.store.common.annotation.WebAdapter;
import v2.sideproject.store.user_management_service.application.port.in.RegisterUsersUseCase;

@WebAdapter
@RestController
@RequestMapping("/api/users-service")
@RequiredArgsConstructor
public class RegisterUsersController {

    private final RegisterUsersUseCase registerUsersUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> registerUsers() {

        return null;
    }
}
