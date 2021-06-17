package org.hillel.config;

import org.hillel.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@ComponentScan({"org.hillel.util", "org.hillel.filter"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/V2/api-docs", "/configuration/ui", "/configuration/**",
                        "/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
    }

/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/resources/static/*").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/api/auth").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
  */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/static/*").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/tl/index", "/tl/vehicles", "/tl/stations", "/tl/routes", "/tl/trips", "/tl/help").permitAll()
                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "swagger-resources/configuration/ui").permitAll()
                .antMatchers("/tl/vehicles/*", "/tl/stations/*", "/tl/routes/*", "/tl/trips/*", "/tl/help").permitAll()
                .antMatchers("/tl/users", "/tl/user", "/tl/user/*").hasAuthority("ROLE_TICKET")
                .antMatchers("/rest").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
    }
 /*
                        .antMatchers("/V2/api-docs",
                "/configuration/ui",
                "/configuration/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**"
                *//*, "swagger-resources/configuration/ui"*//*
        ).permitAll()
            */


    @Bean
    public PasswordEncoder passwordEncoder() {
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