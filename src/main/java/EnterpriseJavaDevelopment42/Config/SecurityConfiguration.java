package EnterpriseJavaDevelopment42.Config;

import EnterpriseJavaDevelopment42.Service.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
        throws Exception{

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/doctors").permitAll()
                .mvcMatchers(HttpMethod.GET,"/patients").hasAnyRole("NURSE", "DOCTOR", "ADMIN")
                .mvcMatchers(HttpMethod.GET,"/doctors/OFF").hasAnyRole("NURSE", "DOCTOR")
                .mvcMatchers(HttpMethod.POST,"/newDoctor").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/newPatient").hasAnyRole("NURSE", "DOCTOR")
                .mvcMatchers(HttpMethod.PATCH,"/updatePatient/{id}").hasAnyRole("NURSE", "DOCTOR")
                .anyRequest()
                .permitAll();

        return http.build();

    }

}
