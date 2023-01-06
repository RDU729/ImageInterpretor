//package com.api.imageinterpretor.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static com.api.imageinterpretor.config.MyCustomDsl.customDsl;
//
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    //    @Bean
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests().anyRequest()
////                .permitAll()
////                .and().cors()
////                .and().csrf()
////                .disable();
////
////    }
////
////    @Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("*"));
////        configuration.setAllowedMethods(Arrays.asList("*"));
////        configuration.setAllowedHeaders(Arrays.asList("*"));
////        configuration.setAllowCredentials(true);
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        //http.csrf(AbstractHttpConfigurer::disable);
//
//        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/api/v1/hi").permitAll()
//        );
////                .formLogin((form) -> form
////                        .loginPage("/login")
////                        .permitAll()
////                )
////                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user =
////                User.withDefaultPasswordEncoder()
////                        .username("user")
////                        .password("password")
////                        .roles("USER")
////                        .build();
////
////        return new InMemoryUserDetailsManager(user);
////    }
//}