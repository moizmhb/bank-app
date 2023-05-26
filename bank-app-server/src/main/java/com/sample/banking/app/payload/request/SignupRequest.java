package com.sample.banking.app.payload.request;

import lombok.*;

import java.util.Set;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  @Size(max = 120)
  private String firstName;
  @NotBlank
  @Size(max = 120)
  private String lastName;

  private String address;
}
