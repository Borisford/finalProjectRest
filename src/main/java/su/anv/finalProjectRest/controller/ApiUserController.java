package su.anv.finalProjectRest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import su.anv.finalProjectRest.dto.save.IncomingSave;
import su.anv.finalProjectRest.dto.save.OutputSave;
import su.anv.finalProjectRest.dto.auth.JwtAuthenticationResponse;
import su.anv.finalProjectRest.dto.auth.SignInRequest;
import su.anv.finalProjectRest.dto.auth.SignUpRequest;
import su.anv.finalProjectRest.service.auth.AuthenticationService;
import su.anv.finalProjectRest.service.auth.UserService;
import su.anv.finalProjectRest.service.data.LocalBaseInputService;
import su.anv.finalProjectRest.service.data.UserRequestService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "API access")
public class ApiUserController {
    private final AuthenticationService authenticationService;
    private final LocalBaseInputService localBaseInputService;
    private final UserRequestService userRequestService;
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/saved")
    public OutputSave GetSavedInfo(@RequestParam("ticker") String ticker) {
        return userRequestService.getSavedData(userService.getCurrentUser(), ticker);
    }

    @PostMapping("/save")
    public void SaveInfo(@RequestBody IncomingSave incomingSave) {
        localBaseInputService.saveToLocal(incomingSave.getTicker());
        userRequestService.addUserRequest(userService.getCurrentUser(), incomingSave);
    }

    @PostMapping("/add")
    public void addTicker(@RequestParam String ticker) {
        localBaseInputService.addTicker(ticker);
    }

}
