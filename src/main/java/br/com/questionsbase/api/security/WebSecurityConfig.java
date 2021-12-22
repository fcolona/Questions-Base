package br.com.questionsbase.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/login", "/oauth/**").permitAll()
            //.antMatchers("/admin/**").hasRole(UserRoles.ADMIN.name())
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll();
            /*
            .and()
            .oauth2Login()
            .loginPage("/login")
            .userInfoEndpoint()
            .userService(oauthUserService) */

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}
