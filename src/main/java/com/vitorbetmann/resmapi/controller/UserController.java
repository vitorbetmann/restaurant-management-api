package com.vitorbetmann.resmapi.controller;

import com.vitorbetmann.resmapi.dto.*;
import com.vitorbetmann.resmapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Registers a new user.",
            description = "Registers a new user (OWNER or CUSTOMER). The email must be unique across all users.",
            responses = {
                    @ApiResponse(description = "CREATED", responseCode = "201"),
                    @ApiResponse(description = "BAD REQUEST — invalid user type or validation error", responseCode = "400"),
                    @ApiResponse(description = "CONFLICT — email already in use", responseCode = "409")
            }
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(this.userService.createUser(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Partially updates user data (excluding password).",
            description = "Updates one or more of NAME, EMAIL, LOGIN, and ADDRESS for the user with the given ID. At least one field is required; omitted fields are left unchanged. Password changes go through the dedicated /{id}/password endpoint.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "NOT FOUND — user does not exist", responseCode = "404"),
                    @ApiResponse(description = "CONFLICT — email already in use by another user", responseCode = "409")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(this.userService.updateUser(id, request), HttpStatus.OK);
    }

    @Operation(
            summary = "Changes a user's password.",
            description = "Updates the password of the user with the given ID. Requires the current password for verification.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "UNAUTHORIZED — current password is invalid", responseCode = "401"),
                    @ApiResponse(description = "NOT FOUND — user does not exist", responseCode = "404")
            }
    )
    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(this.userService.changePassword(id, request), HttpStatus.OK);
    }

    @Operation(
            summary = "Deletes a user.",
            description = "Deletes the user with a given ID.",
            responses = {
                    @ApiResponse(description = "NO CONTENT", responseCode = "204"),
                    @ApiResponse(description = "NOT FOUND — user does not exist", responseCode = "404")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Finds users by name.",
            description = "Returns all users whose name contains the given value (partial, case-sensitive match).",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam @NotBlank String name) {
        return new ResponseEntity<>(this.userService.findByName(name), HttpStatus.OK);
    }

    @Operation(
            summary = "Validates a user's login attempt.",
            description = "Validates the provided login and password against the stored credentials. Returns 200 if valid.",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "UNAUTHORIZED — password is invalid", responseCode = "401"),
                    @ApiResponse(description = "NOT FOUND — login does not exist", responseCode = "404")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Void> validateLogin(@Valid @RequestBody ValidateLoginRequest request) {
        this.userService.validateLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}