package server.security;

import org.springframework.http.HttpMethod;

import static server.security.SecurityConstants.PASSWORD_RECOVERY_URL;
import static server.security.SecurityConstants.SIGN_UP_URL;

//@EnableWebSecurity
public class WebSecurity
        //extends WebSecurityConfigurerAdapter
        {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, SIGN_UP_URL, PASSWORD_RECOVERY_URL).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }

}
