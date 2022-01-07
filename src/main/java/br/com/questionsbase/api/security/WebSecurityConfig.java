package br.com.questionsbase.api.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.questionsbase.domain.service.CustomOAuth2UserService;
import br.com.questionsbase.domain.service.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserService userService;

    private static final String[] AUTH_WHITELIST = {

        // -- swagger ui
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/", "/login", "/oauth/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/user").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/v1/user").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/user/resetPassword").permitAll()
            .antMatchers(HttpMethod.GET, "/user/changePassword").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/v1/user/updatePassword").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/question").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/v1/question").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/v1/question").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/api/v1/question/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/question/check").authenticated()
            .antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .oauth2Login()
            .loginPage("/login")
            .userInfoEndpoint()
            .userService(oauthUserService)
            .and()
            .successHandler( new AuthenticationSuccessHandler() {
                
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {
                            
                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    String email = oauthUser.getAttribute("email");
                    userService.processOAuthPostLogin(email);
                    response.setStatus(200);
                    response.sendRedirect(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
                }
            });
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**, /materialize/**", "/style/**, /swagger-resources/", "/webjars/").antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
