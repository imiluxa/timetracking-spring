package ua.iamiluxa.timetrackingspringproject.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.iamiluxa.timetrackingspringproject.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityController extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityController(UserService userService,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/index", "/", "/favicon.ico")
                .permitAll()
                .antMatchers("/users/**", "/activities/request", "/activities/add", "/activities/delete/**", "/activities/edit/**", "/activities/request/approve/**", "/activities/request/decline/**")
                .hasAuthority("ADMIN")
                .antMatchers("/profile", "/activities", "/activities/duration/**", "/activities/request/add/**", "/activities/request/complete/**")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/registration", "/login")
                .anonymous()
                .anyRequest()
                .denyAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .defaultSuccessUrl("/profile")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling();
    }

}
