package org.hillel.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Pair<String, Boolean> paswordAndStatus = jdbcTemplate.queryForObject("select pwd, active from users where email=?",
                (row, iteration) -> Pair.of(row.getString(1), row.getBoolean(2)), email);
        List<String> authorities = jdbcTemplate.queryForList("select authority from authorities where email=?", String.class, email);

        //        String[] auth = authorities.toArray(new String[0]);
        String[] auth = authorities.toArray(String[]::new);

        User.UserBuilder builder = User.builder();
        return  builder
                .password(paswordAndStatus.getFirst())
                .username(email)
                .disabled(!paswordAndStatus.getSecond())
                .authorities(auth).build();
    }
}
