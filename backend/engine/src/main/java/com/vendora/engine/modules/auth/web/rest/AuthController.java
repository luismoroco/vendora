package com.vendora.engine.modules.auth.web.rest;

import com.vendora.engine.modules.auth.AuthUseCase;
import com.vendora.engine.modules.auth.model.Session;
import com.vendora.engine.modules.auth.web.rest.validator.LoginPayload;
import com.vendora.engine.modules.auth.web.rest.validator.SignUpPayload;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.security.authorization.SessionManager;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthUseCase useCase;
  private final AuthenticationManager authenticationManager;
  private final SessionManager sessionManager;

  public AuthController(
    AuthUseCase useCase,
    AuthenticationManager authenticationManager,
    SessionManager sessionManager
  ) {
    this.useCase = useCase;
    this.authenticationManager = authenticationManager;
    this.sessionManager = sessionManager;
  }

  @PostMapping("/log-in")
  public ResponseEntity<Session> logIn(@Valid @RequestBody LoginPayload payload) {
    var auth = new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword());
    this.authenticationManager.authenticate(auth);

    var user = this.useCase.logIn(payload.map());

    return ResponseEntity.status(HttpStatus.OK).body(this.performAuthorization(user));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Session> signUp(@Valid @RequestBody SignUpPayload payload) {
    var user = this.useCase.signUp(payload.map());

    return ResponseEntity.status(HttpStatus.CREATED).body(this.performAuthorization(user));
  }

  @PutMapping("/log-out")
  public ResponseEntity<?> logOut() {
    var user = this.sessionManager.getSession();

    this.sessionManager.destroy();

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private Session performAuthorization(User user) {
    return this.sessionManager.createSession(user);
  }
}
