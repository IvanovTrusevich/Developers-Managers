package by.itransition.config;

import by.itransition.data.model.User;
import by.itransition.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Ilya Ivanov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LocaleResolver localeResolver;

    private final ThemeResolver themeResolver;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private MessageSource messages;

    @Autowired
    public SecurityConfig(LocaleResolver localeResolver, ThemeResolver themeResolver, PasswordEncoder passwordEncoder, UserService userService) {
        this.localeResolver = localeResolver;
        this.themeResolver = themeResolver;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/res/**").permitAll()
                    .antMatchers("/index/**").permitAll()
                    .antMatchers("/registration", "/lost/**", "/login/**", "/activate/**", "/recovery/**").permitAll()
                    .antMatchers("/projects/**", "/connector/**").permitAll()
                    .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/profile/*", "/settings/*").access("hasRole('ROLE_DEVELOPER')")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler(authenticationFailureHandler())
                    .successHandler(authenticationSuccessHandler())
                    .permitAll()
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(3600)     // 1 hour
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                setDefaultFailureUrl("/login?error=true");
                super.onAuthenticationFailure(request, response, exception);
                Locale locale = localeResolver.resolveLocale(request);
                String errorMessage = exception.getMessage();
                if (exception instanceof BadCredentialsException) {
                    errorMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, locale);
                } else if (exception instanceof DisabledException) {
                    errorMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", null, locale);
                } else if (exception instanceof AccountExpiredException) {
                    errorMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", null, locale);
                } else if (exception instanceof LockedException) {
                    errorMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", null, locale);
                }
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null && principal instanceof User) {
                    User user = (User) principal;
                    localeResolver.setLocale(request, response, new Locale(user.getLocale()));
                    themeResolver.setThemeName(request, response, user.getTheme());
                }
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Autowired
    public void setMessages(MessageSource messages) {
        this.messages = messages;
    }
}