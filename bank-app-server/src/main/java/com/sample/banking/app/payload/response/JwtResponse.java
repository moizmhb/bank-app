package com.sample.banking.app.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;

  private Long customerNumber;

  private Long accountNumber;

  public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, Long customerNumber, Long accountNumber) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.accountNumber = accountNumber;
    this.customerNumber = customerNumber;
  }

    public JwtResponse(String accessToken, Long id, String username, String email, Long customerNumber, Long accountNumber) {
      this.token = accessToken;
      this.id = id;
      this.username = username;
      this.email = email;
      this.accountNumber = accountNumber;
      this.customerNumber = customerNumber;
    }

    public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public Long getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(Long customerNumber) {
    this.customerNumber = customerNumber;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
}
