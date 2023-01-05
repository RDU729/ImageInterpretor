//package com.api.imageinterpretor.service;
//
//import com.api.imageinterpretor.config.CustomUserDetails;
//import com.api.imageinterpretor.model.User;
//import com.api.imageinterpretor.model.repository.UserRepo;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@AllArgsConstructor
//@Slf4j
//@Service
//@NoArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private  UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepo.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new CustomUserDetails(userOptional.get());
//    }
//
//}