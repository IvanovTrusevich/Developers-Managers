package by.itransition.config;

import by.itransition.data.repository.UserRepository;
import by.itransition.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Ilya Ivanov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MessageSource messages;

    private final LocaleResolver localeResolver;

    private UserService userService;

    @Autowired
    public SecurityConfig(MessageSource messages, LocaleResolver localeResolver) {
        this.messages = messages;
        this.localeResolver = localeResolver;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/res/**").permitAll()
                    .antMatchers("/registration", "/index", "/lost/**", "/login/**", "/activate/**",
                            "/recovery/**", "/connector","/project").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("credentials")
                    .passwordParameter("password")
                    .failureHandler(authenticationFailureHandler())
//                    .failureUrl("/login?error=true")
                    .permitAll()
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(3600)     // 1 hour
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .permitAll()
                    .and()
                .csrf()
                    .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
}