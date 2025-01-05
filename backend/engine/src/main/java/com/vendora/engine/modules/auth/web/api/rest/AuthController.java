package com.vendora.engine.modules.auth.web.api.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.auth.AuthUseCase;
import com.vendora.engine.modules.auth.web.validator.LoginWebRequest;
import com.vendora.engine.modules.auth.web.validator.PasswordRecoveryWebRequest;
import com.vendora.engine.modules.auth.web.validator.SignUpWebRequest;
import com.vendora.engine.modules.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthUseCase useCase;
  private final AuthenticationManager authManager;
  private final Scrooge<? extends Credentials> scrooge;

  public AuthController(
    AuthUseCase useCase,
    AuthenticationManager authManager,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.authManager = authManager;
    this.scrooge = scrooge;
  }

  @PostMapping("/log-in")
  public ResponseEntity<? extends Credentials> logInUser(
    @Valid @RequestBody final LoginWebRequest payload
  ) {
    this.authenticate(payload);

    var user = this.useCase.logIn(payload.buildRequest());
    return this.performAuthorization(user);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<? extends Credentials> signUp(
    @Valid @RequestBody final SignUpWebRequest payload
  ) {
    var user = this.useCase.signUp(payload.buildRequest());
    return this.performAuthorization(user);
  }

  @PutMapping("/log-out")
  public ResponseEntity<Void> logOut() {
    this.scrooge.setContext();

    this.scrooge.destroyKeys();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/password-recovery")
  public ResponseEntity<Void> passwordRecovery(
    @Valid @RequestBody final PasswordRecoveryWebRequest payload
  ) {
    this.useCase.passwordRecovery(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void authenticate(LoginWebRequest payload) {
    var auth = new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword());
    authManager.authenticate(auth);
  }

  private ResponseEntity<? extends Credentials> performAuthorization(User user) {
    var credentials = this.scrooge.generateKeys(user);
    return ResponseEntity.ok(credentials);
  }
}
