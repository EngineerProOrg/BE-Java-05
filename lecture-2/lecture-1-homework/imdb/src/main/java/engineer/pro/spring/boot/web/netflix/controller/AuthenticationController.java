package engineer.pro.spring.boot.web.netflix.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import engineer.pro.spring.boot.web.netflix.dto.LoginRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationRequest;
import engineer.pro.spring.boot.web.netflix.dto.RegistrationResponse;
import engineer.pro.spring.boot.web.netflix.model.User;
import engineer.pro.spring.boot.web.netflix.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController extends AbstractController {
  private final UserService userService;

  @GetMapping("/inspect")
  public ResponseEntity<User> inspect(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute(SESSION_ATTRIBUTE_USER);
    log.info("inspect user:" + user);
    if (Objects.isNull(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    User user = userService.findUser(loginRequest);
    if (Objects.isNull(user)) {
      return ResponseEntity.unprocessableEntity().build();
    }
    request.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
    return ResponseEntity.ok("");
  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest,
      HttpServletRequest request) {
    RegistrationResponse response = userService.createUser(registrationRequest);
    if (Objects.isNull(response)) {
      return ResponseEntity.unprocessableEntity().build();
    }
    return ResponseEntity.ok(response);
  }
}
