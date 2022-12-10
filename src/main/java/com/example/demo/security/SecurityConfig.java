package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.example.demo.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
public static final String AUTHORITIES_CLAIM_NAME = "roles";
@Autowired
 UserService userDetailsService;
@Autowired
private AccessDeniedHandler accessDeniedHandler;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 auth.userDetailsService(userDetailsService);
 }
@Override
protected void configure(HttpSecurity http) throws Exception {

http.csrf().disable()
/*.authenticationProvider(getProvider())
.formLogin()
.loginProcessingUrl("/login")
.successHandler(new AuthentificationLoginSuccessHandler())
.failureHandler(new SimpleUrlAuthenticationFailureHandler())
.and()
.logout()
.logoutUrl("/logout")
.logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
.invalidateHttpSession(true)
.and()*/
.authorizeRequests()
.antMatchers("/login").permitAll()
.antMatchers("/logout").permitAll()
.antMatchers("/user").authenticated()
.antMatchers("/user").hasAuthority("ADMIN")
.anyRequest().authenticated();
//Ajouter sessionManagement et le filter
http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;
http.addFilter(new JWTAuthenticationFilter(authenticationManager())); 

http.addFilterBefore(new JWTAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class);

}
private class AuthentificationLoginSuccessHandler extends
SimpleUrlAuthenticationSuccessHandler {
@Override
public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
Authentication authentication) throws IOException, ServletException {
response.setStatus(HttpServletResponse.SC_OK);
 }
 } 
private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
@Override
public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
response.setStatus(HttpServletResponse.SC_OK);
 }
 }

@Bean
public AuthenticationProvider getProvider() {
 AppAuthProvider provider = new AppAuthProvider();
provider.setUserDetailsService(userDetailsService);
return provider;
}
@Bean
public AccessDeniedHandler accessDeniedHandler(){
return new AccessDeniedHandlerImpl();
}

@Bean
public BCryptPasswordEncoder encodePWD() {
 return new BCryptPasswordEncoder();

}
}
