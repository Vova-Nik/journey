package org.hillel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/static/*").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/tl/index", "/tl/vehicles", "/tl/stations", "/tl/routes", "/tl/trips", "/tl/help").permitAll()
                .antMatchers("/tl/users", "/tl/user", "/tl/user/*").hasAuthority("ROLE_TICKET")
                .antMatchers("/rest").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

/*
   @Bean
    public UserDetailsService userDetailsServiceInDB(DataSource dataSource){
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        CustomUserDetailService manager = new CustomUserDetailService();
        return manager;
    }
    */

/*
    public static void main(String[] args) {
        System.out.println( new BCryptPasswordEncoder().encode("123"));
    }
*/

}


//ROLE_TICKET ROLE_ADMIN ROLE_CLIENT