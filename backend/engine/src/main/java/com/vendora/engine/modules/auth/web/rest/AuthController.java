package com.vendora.engine.modules.auth.web.rest;

import com.vendora.engine.modules.auth.AuthUseCase;
import com.vendora.engine.modules.auth.model.Session;
import com.vendora.engine.modules.auth.web.rest.validator.LoginRestRequest;
import com.vendora.engine.modules.auth.web.rest.validator.SignUpRestRequest;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.security.cerberus.Cerberus;
import com.vendora.engine.security.cerberus.engines.JwtCerberus;
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
  private final Cerberus<Session, JwtCerberus> cerberus;

  public AuthController(
    AuthUseCase useCase,
    AuthenticationManager authManager,
    @Qualifier("Jwt") Cerberus<Session, JwtCerberus> cerberus
  ) {
    this.useCase = useCase;
    this.authManager = authManager;
    this.cerberus = cerberus;
  }

  @PostMapping("/log-in")
  public ResponseEntity<Session> logInUser(
    @Valid @RequestBody LoginRestRequest restRequest
  ) {
    this.authenticate(restRequest);

    var user = this.useCase.logIn(restRequest.adapt());
    return this.performAuthorization(user);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Session> signUp(
    @Valid @RequestBody SignUpRestRequest payload
  ) {
    var user = this.useCase.signUp(payload.adapt());
    return this.performAuthorization(user);
  }

  @PutMapping("/log-out")
  public ResponseEntity<?> logOut() {
    this.cerberus.context().destroyKeys();

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void authenticate(LoginRestRequest restRequest) {
    var auth = new UsernamePasswordAuthenticationToken(restRequest.getUsername(), restRequest.getPassword());
    authManager.authenticate(auth);
  }

  private ResponseEntity<Session> performAuthorization(User user) {
    var session = this.cerberus.generateKeys(user);
    return ResponseEntity.ok(session);
  }
}
