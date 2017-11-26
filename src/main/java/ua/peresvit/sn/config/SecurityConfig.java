package ua.peresvit.sn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.social.security.SpringSocialConfigurer;

//  <h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about", "/webjars/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/registration/**", "/", "/resources/**", "/favicon.ico", "/webjars/**").permitAll()
                    .antMatchers("/auth/**", "/signup/**").permitAll()
                    .antMatchers("/admin**").access("hasRole('ADMIN')")
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .antMatchers("/home/**").authenticated()
                .and()
                    .formLogin()
                    .loginPage("/home")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/login/success")
                    .failureUrl("/login/failure")
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout").permitAll()
                    .logoutSuccessUrl("/home")
                    .deleteCookies("JSESSIONID")
                .and()
                    .apply(new SpringSocialConfigurer())
                .and()
                   .rememberMe();
    }


    // For test spring boot security create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }
}
