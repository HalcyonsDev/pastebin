package com.halcyon.pastebin.service.auth;

import com.halcyon.pastebin.dto.auth.SignUpDto;
import com.halcyon.pastebin.model.Token;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.payload.AuthRequest;
import com.halcyon.pastebin.payload.AuthResponse;
import com.halcyon.pastebin.service.token.TokenService;
import com.halcyon.pastebin.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private static User user;

    @BeforeAll
    public static void BeforeAll() {
        user = User.builder()
                .email("test_user@gmail.com")
                .name("test_name")
                .password("test_password123")
                .build();
    }

    @Test
    public void AuthService_SignUp_ReturnsAuthResponse() {
        Token token = new Token();
        token.setValue("TOKEN.TOKEN.TOKEN");

        SignUpDto dto = new SignUpDto(
                user.getEmail(),
                user.getName(),
                user.getPassword()
        );

        when(userService.create(Mockito.any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("HASHED_PASSWORD");
        when(jwtProvider.generateToken(Mockito.any(User.class), Mockito.anyBoolean())).thenReturn("TOKEN.TOKEN.TOKEN");
        when(tokenService.create(Mockito.any(Token.class))).thenReturn(token);

        AuthResponse authResponse = authService.signup(dto);

        Assertions.assertNotNull(authResponse.getAccessToken());
        Assertions.assertNotNull(authResponse.getRefreshToken());
        Assertions.assertEquals("Bearer", authResponse.getTYPE());
    }

    @Test
    public void AuthService_Login_ReturnsAuthResponse() {
        Token token = new Token();
        token.setValue("TOKEN.TOKEN.TOKEN");

        AuthRequest authRequest = new AuthRequest(user.getEmail(), user.getPassword());

        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(jwtProvider.generateToken(Mockito.any(User.class), Mockito.anyBoolean())).thenReturn("TOKEN.TOKEN.TOKEN");
        when(tokenService.create(Mockito.any(Token.class))).thenReturn(token);

        AuthResponse authResponse = authService.login(authRequest);

        Assertions.assertNotNull(authResponse.getAccessToken());
        Assertions.assertNotNull(authResponse.getRefreshToken());
        Assertions.assertEquals("Bearer", authResponse.getTYPE());
    }
}
