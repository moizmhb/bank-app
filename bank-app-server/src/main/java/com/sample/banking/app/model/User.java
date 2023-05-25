package com.sample.banking.app.model;

import com.sample.banking.app.config.ErrorMessageConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = ErrorMessageConstant.INVALID_LAST_NAME)
  @Size(min = 3, max = 20, message = ErrorMessageConstant.INVALID_LAST_NAME)
  private String username;

  @NotBlank
  @Email
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+).com$", message = ErrorMessageConstant.INVALID_EMAIL)
  @Column(unique = true)
  private String email;

  @NotBlank
  @Size(max = 20)
  private String password;

  @NotBlank(message = ErrorMessageConstant.INVALID_FIRST_NAME)
  @Size(min = 3, max = 10, message = ErrorMessageConstant.INVALID_FIRST_NAME)
  private String firstName;


  @NotBlank(message = ErrorMessageConstant.INVALID_LAST_NAME)
  @Size(min = 3, max = 10, message = ErrorMessageConstant.INVALID_LAST_NAME)
  private String lastName;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String username, String email, String password, String firstName, String lastName) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
