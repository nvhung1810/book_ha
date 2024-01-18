package book.be.gau.book_ha.security.auth;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import book.be.gau.book_ha.dto.response.ErrorResponse;
import book.be.gau.book_ha.dto.response.ResponseData;
import book.be.gau.book_ha.utils.PasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;
  @Autowired
  private MessageSourceAccessor messages;
  @Autowired
  PasswordValidator passwordValidator;
  ZonedDateTime zTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

  @PostMapping("/register")
  public ResponseEntity<?> resgiter(
      @RequestBody RegisterRequest request) {
    String errorMessage = messages.getMessage("register.invalid-password");
    if (!passwordValidator.isValidPassword(request.getLogin_password())) {
      return ResponseEntity
          .badRequest()
          .body(ErrorResponse
              .builder()
              .message(errorMessage)
              .status(HttpStatus.BAD_REQUEST.value())
              .create_date(zTime)
              .data(ResponseData
                  .builder()
                  .data(request.getLogin_password())
                  .build())
              .build());
    }

    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    System.out.println("Log anything");
    try {
      return ResponseEntity.ok(service.authenticate(request));
    } catch (Exception e) {
      e.printStackTrace(); // In ra exception trace
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    service.refreshToken(request, response);
  }
}
