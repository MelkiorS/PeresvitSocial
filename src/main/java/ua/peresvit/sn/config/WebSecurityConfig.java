package ua.peresvit.sn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import ua.peresvit.sn.security.UserDetailsServiceImpl;

import javax.sql.DataSource;

//  <h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean  it's a problem
//    public SpringSecurityDialect securityDialect(){
//        return new SpringSecurityDialect();
//    }

  /*  @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
//                .passwordEncoder(passwordEncoder());
//                jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource);
//                .passwordEncoder(bCryptPasswordEncoder);
    }


  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about", "/webjars/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole(RoleEnum.ADMIN.getCode()
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
                    .antMatchers("/").permitAll()
                    .antMatchers( "/resources/", "/webjars/", "/assets/", "/favicon.ico").permitAll()
                    .antMatchers( "/registration/").permitAll()
                    .antMatchers("/auth/**", "/signup/**").permitAll()
                    .antMatchers("/admin**").access("hasRole('ADMIN')")
                    .antMatchers("/home/**").authenticated()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/login/success")
                    .failureUrl("/login/failure")
//                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/home").permitAll()
//                    .deleteCookies("JSESSIONID")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/accessDenied")
//                    .apply(new SpringSocialConfigurer())
                    .and()
                .rememberMe();
    }



  /*  // For test spring boot security create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser(RoleEnum.ADMIN.getCode().password("password").roles(RoleEnum.ADMIN.getCode();
    }*/
}
