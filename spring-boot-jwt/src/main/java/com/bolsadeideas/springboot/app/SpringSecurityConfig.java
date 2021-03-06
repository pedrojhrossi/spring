/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app;

import com.bolsadeideas.springboot.app.auth.filter.JwtAuthenticationFilter;
import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author pj
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
        //** JDBC
//        builder.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
//                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
//                .authoritiesByUsernameQuery("SELECT u.username, a.authority FROM authorities a inner join users u on (a.user_id=u.id) WHERE u.username = ?");

        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale").permitAll()
                //                .antMatchers("/ver/**").hasAnyRole("USER")
                //                .antMatchers("/uploads/**").hasAnyRole("USER")
                //                .antMatchers("/form/**").hasAnyRole("ADMIN")
                //                .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                //                .antMatchers("/factura/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successHandler).loginPage("/login").permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/error_403")
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
