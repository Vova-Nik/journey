package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor

public class ClientEntity extends AbstractEntity<Long> {

    @Column(name="name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "pwd")
    private String pwd;
    static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\".]]+");

    public ClientEntity(String name, String surname, String email) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(surname) || StringUtils.isEmpty(email))
            throw new IllegalArgumentException("UserEntity.UserEntity insufficient initial data");
        Matcher m = emailPattern.matcher(email);
        if (!m.matches())
            throw new IllegalArgumentException("UserEntity.UserEntity insufficient email");
        this.name = name;
        this.surname = surname;
        this.email = email;
        pwd = "qwerty";
    }

    @Override
    public boolean isValid() {
        return super.isValid() && StringUtils.hasText(surname) && StringUtils.hasText(email) && StringUtils.hasText(pwd);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof ClientEntity)) return false;
        ClientEntity entity = (ClientEntity) o;
        return(email.equals(entity.email) && pwd.equals(entity.pwd));
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, pwd);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
