package com.enigma.cashier_application.controller;

import com.enigma.cashier_application.dto.request.AuthRequest;
import com.enigma.cashier_application.dto.request.CustomerRequest;
import com.enigma.cashier_application.dto.response.CommonResponse;
import com.enigma.cashier_application.dto.response.CustomerResponse;
import com.enigma.cashier_application.dto.response.RegisterResponse;
import com.enigma.cashier_application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<RegisterResponse>> create(@RequestBody AuthRequest request){
        RegisterResponse response=authService.register(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .data(response)
                .message("Successfully create data")
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    };
    @PostMapping(path = "/registerAdmin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<RegisterResponse>> createAdmin(@RequestBody AuthRequest request){
        RegisterResponse response=authService.registerAdmin(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .data(response)
                .message("Successfully create data")
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    };
}
