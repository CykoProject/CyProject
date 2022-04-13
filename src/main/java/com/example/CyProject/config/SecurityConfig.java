package com.example.CyProject.config;

import com.example.CyProject.user.model.UserRepository;
import groovyjarjarantlr4.v4.codegen.model.ExceptionClause;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private UserRepository userRepository;
    @Autowired private HttpSession session;

    @Bean
    public BCryptPasswordEncoder Encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .authorizeRequests()
//                .antMatchers("/home/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .usernameParameter("email")
                .passwordParameter("upw")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error=true")
                .failureHandler(failureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 이 주소로 들어오면 controller 에 없어도 로그아웃 시켜줌
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/user/login"); // 로그아웃 성공 후
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthFailureHandler();
    }
}
