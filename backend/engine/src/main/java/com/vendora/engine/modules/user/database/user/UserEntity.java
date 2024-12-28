package com.vendora.engine.modules.user.database.user;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.user.model.UserType;
import com.vendora.engine.modules.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements MappedModel<User>, UserDetails {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long userId;
  @Enumerated(EnumType.STRING) private UserType userType;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @NotBlank private String email;
  @NotBlank private String username;
  @NotBlank private String password;
  @Column(insertable = false) private LocalDateTime createdAt;
  @Column(insertable = false) private LocalDateTime updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.userType));
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

  @Override
  public User toModel() {
    return MAPPER.map(this, User.class);
  }
}
