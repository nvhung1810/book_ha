package book.be.gau.book_ha.security.auth;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import book.be.gau.book_ha.configs.auth.JwtService;
import book.be.gau.book_ha.enums.Role;
import book.be.gau.book_ha.enums.TokenType;
import book.be.gau.book_ha.models.Customer;
import book.be.gau.book_ha.models.Token;
import book.be.gau.book_ha.repositories.CustomerRepository;
import book.be.gau.book_ha.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final CustomerRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  private MessageSourceAccessor messages;

  public AuthenticationResponse register(RegisterRequest request) {
    String registerMessagecsSuccess = messages.getMessage("register.success");
    ZonedDateTime zTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

    var user = Customer
        .builder()
        .customer_post_code(request.getCustomer_post_code())
        .customer_staff_email(request.getCustomer_staff_email())
        .login_password(
            passwordEncoder.encode(request.getLogin_password()))
        .role(Role.USER)
        .build();

    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse
        .builder()
        .message(registerMessagecsSuccess)
        .status(HttpStatus.BAD_REQUEST.value())
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .create_date(zTime)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getCustomer_staff_email(),
              request.getLogin_password()));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException(
          "Invalid username or password!!!!!!!!" +
              request.getCustomer_staff_email() +
              request.getLogin_password());
    }

    var user = repository
        .findByCustomerStaffEmail(request.getCustomer_staff_email())
        .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse
        .builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(Customer customer, String jwtToken) {
    var token = Token.builder()
        .customer(customer)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Customer customer) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(customer.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);

    System.out.println(authHeader);
    System.out.println(refreshToken);
    System.out.println(userEmail);

    if (userEmail != null) {
      var user = this.repository
          .findByCustomerStaffEmail(userEmail)
          .orElseThrow();

      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
