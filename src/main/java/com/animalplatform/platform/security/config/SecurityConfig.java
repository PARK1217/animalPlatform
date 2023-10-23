package com.animalplatform.platform.security.config;

import com.animalplatform.platform.security.filter.LoginApiFilter;
import com.animalplatform.platform.security.handler.AnimalAuthenticationEntrypoint;
import com.animalplatform.platform.security.handler.AnimalAuthenticationFailureHandler;
import com.animalplatform.platform.security.handler.AnimalAuthenticationSuccessHandler;
import com.animalplatform.platform.security.handler.AnimalLogoutSuccessHandler;
import com.animalplatform.platform.security.provider.AnimalAuthenticationProvider;
import com.animalplatform.platform.user.service.UserLoginService;
import com.animalplatform.platform.utils.AesUtil;
import com.animalplatform.platform.utils.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String LOGIN_REQUEST_URL = "/api/v1/login";
    private static final String LOGOUT_REQUEST_URL = "/api/v1/logout";

    private final ObjectMappingUtil objectMappingUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginService userLoginService;
    private final AesUtil aesUtil = new AesUtil();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .parentAuthenticationManager(null)
                .authenticationProvider(authenticationProvider(
                        this.userDetailsService,
                        this.passwordEncoder,
                        this.aesUtil,
                        this.userLoginService
                ));

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http.authenticationManager(authenticationManager);

        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()

                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                /**
                 * Filter Layer에서 동작하는 login, logout은 경로에 매칭되지 않으며,
                 * DispatcherServlet 이후 Layer ( Controller ) 에서부터 적용된다.
                 * MedinoAuthenticationEntryPoint 에서 Path 에 따라 API Path는 444예외 호출
                 */
                .authorizeHttpRequests(httpRequest ->
                        httpRequest
                                .antMatchers("/actuator/**", "/api/v1/mail/**", "/api/v1/sample", "/api/v1/master/signup").permitAll()
                                // Page Mapping Matcher
                                .mvcMatchers("", "/", "/index", "/login", "/sample", "/devTh", "/checkAPI").permitAll()
                                // Error page Sample Matcher
                                .mvcMatchers("/sessionExpire").permitAll()
                                // Static Resource Matcher
                                .antMatchers(
                                        "/static/**",
                                        "/external/**",
                                        "/css/**",
                                        "/js/**",
                                        "/images/**",
                                        "/favicon.**",
                                        "/font/**"
                                ).permitAll()
                                .anyRequest().hasRole("USER")
                )

                .addFilterAt(
                        loginFilter(
                                LOGIN_REQUEST_URL,
                                authenticationManager,
                                authenticationSuccessHandler(this.objectMappingUtil, this.userLoginService),
                                authenticationFailureHandler(this.objectMappingUtil),
                                this.objectMappingUtil,
                                this.userLoginService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )

                .exceptionHandling(exceptionConfig ->
                        exceptionConfig
                                // 인가예외가 필요 없기때문에 인증 및 인가예외는 해당 클래스에서 처리된다.
                                .authenticationEntryPoint(
                                        authenticationEntryPoint(this.objectMappingUtil)
                                )
                )

                .logout(logoutConfig ->
                        logoutConfig
                                .logoutUrl(LOGOUT_REQUEST_URL)
                                .logoutSuccessHandler(
                                        logoutSuccessHandler(this.objectMappingUtil)
                                )
                )

        ;

        return http.build();
    }

    private AbstractAuthenticationProcessingFilter loginFilter(String loginProcessingUrl,
                                                               AuthenticationManager authenticationManager,
                                                               AuthenticationSuccessHandler authenticationSuccessHandler,
                                                               AuthenticationFailureHandler authenticationFailureHandler,
                                                               ObjectMappingUtil objectMappingUtil,
                                                               UserLoginService userLoginService) {
        return new LoginApiFilter(
                loginProcessingUrl,
                authenticationManager,
                authenticationSuccessHandler,
                authenticationFailureHandler,
                objectMappingUtil,
                userLoginService
        );
    }

    private AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                          PasswordEncoder passwordEncoder,
                                                          AesUtil aesUtil,
                                                          UserLoginService userLoginService) {
        return new AnimalAuthenticationProvider(
                userDetailsService,
                passwordEncoder,
                aesUtil,
                userLoginService
        );
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMappingUtil objectMappingUtil) {
        return new AnimalAuthenticationEntrypoint(objectMappingUtil);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(ObjectMappingUtil objectMappingUtil) {
        return new AnimalAuthenticationFailureHandler(objectMappingUtil);
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMappingUtil objectMappingUtil, UserLoginService userLoginService) {
        return new AnimalAuthenticationSuccessHandler(objectMappingUtil, userLoginService);
    }

    private LogoutSuccessHandler logoutSuccessHandler(ObjectMappingUtil objectMappingUtil) {
        return new AnimalLogoutSuccessHandler(objectMappingUtil);
    }

}
