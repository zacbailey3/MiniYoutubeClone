package com.minitube.auth;

import com.minitube.users.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.minitube.users.UserRepository;



@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    check dupe email/username
    hashpassword
    create user
    save user
    return response
    */
    public AuthResponse register(RegisterRequest request) {
       
       //check email in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
       }

       //check username in use
       if (userRepository.existsByUsername(request.getUsername())) {
        throw new IllegalArgumentException("Username is already in use");
       }
       String passwordHash = passwordEncoder.encode(request.getPassword());

      //create new user
       User user = new User (
        request.getUsername(),
        request.getEmail(),
        passwordHash
       );

       //save user
       User savedUser = userRepository.save(user);

       return new AuthResponse(
            null,
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail()
       );
       
    }

}
