package com.minitube.auth;

import java.util.Optional;

import com.minitube.common.exception.DuplicateResourceException;
import com.minitube.common.exception.InvalidCredentialsException;
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
            throw new DuplicateResourceException("Email is already in use");
       }



       //check username in use
       if (userRepository.existsByUsername(request.getUsername())) {
        throw new DuplicateResourceException("Username is already in use");
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


    public AuthResponse login(LoginRequest request) {

       
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return new AuthResponse(
            null,
            user.getId(),
            user.getUsername(),
            user.getEmail()
       );
    }


}

