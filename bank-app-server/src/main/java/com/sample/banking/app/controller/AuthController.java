package com.sample.banking.app.controller;

import com.sample.banking.app.config.BankUtils;
import com.sample.banking.app.exception.WeakPasswordException;
import com.sample.banking.app.model.Customer;
import com.sample.banking.app.model.ERole;
import com.sample.banking.app.model.Role;
import com.sample.banking.app.model.User;
import com.sample.banking.app.payload.request.LoginRequest;
import com.sample.banking.app.payload.request.SignupRequest;
import com.sample.banking.app.payload.response.JwtResponse;
import com.sample.banking.app.payload.response.MessageResponse;
import com.sample.banking.app.repository.RoleRepository;
import com.sample.banking.app.repository.UserRepository;
import com.sample.banking.app.security.jwt.JwtUtils;
import com.sample.banking.app.security.services.UserDetailsImpl;
import com.sample.banking.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static com.sample.banking.app.config.BankConstant.MAX_LENGTH_NAME;
import static com.sample.banking.app.config.BankConstant.MIN_LENGTH_NAME;
import static com.sample.banking.app.config.ErrorMessageConstant.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomerService customerService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Customer customer = customerService.findByEmail(userDetails.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                customer.getCustomerNumber(),
                customer.getAccount().getAccountNumber()));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(USER_ALREADY_REGISTERED));
        }

        if (signUpRequest.getFirstName().length()<MIN_LENGTH_NAME || signUpRequest.getFirstName().length()>MAX_LENGTH_NAME) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_FIRST_NAME));
        }

        if (signUpRequest.getLastName().length()<MIN_LENGTH_NAME || signUpRequest.getLastName().length()>MAX_LENGTH_NAME) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_LAST_NAME));
        }

        if (signUpRequest.getUsername().length()<MIN_LENGTH_NAME || signUpRequest.getUsername().length()>MAX_LENGTH_NAME) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_USER_NAME));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(ERROR_EMAIL_IS_ALREADY_IN_USE));
        }

        if (!BankUtils.isValidPassword(signUpRequest.getPassword())) {
            throw new WeakPasswordException();
        }


        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getFirstName(),signUpRequest.getLastName());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User userEntity = userRepository.save(user);
        customerService.addCustomer(userEntity);
        return ResponseEntity.ok(new MessageResponse(USER_REGISTERED_SUCCESSFULLY));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

