package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.DTOs.PasswordRecoveryLinkValidationTransport;
import server.DTOs.PasswordRecoveryTransport;
import server.services.PasswordRecoveryService;

@RestController
@RequestMapping("password-recoveries")
public class PasswordRecoveryController {

    private PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{passwordRecoveryId}")
    public PasswordRecoveryLinkValidationTransport validatePasswordRecoveryLink(@PathVariable String passwordRecoveryId) {
        return passwordRecoveryService.validatePasswordRecoveryLink(passwordRecoveryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void requestPasswordRecovery(@RequestParam("email") String email) {
        passwordRecoveryService.requestPasswordRecovery(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public PasswordRecoveryTransport changePassword(@RequestBody PasswordRecoveryTransport passwordRecovery) {
        return passwordRecoveryService.saveNewPassword(passwordRecovery.getUserId(), passwordRecovery.getNewPassword());
    }

}