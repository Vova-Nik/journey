package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
@Getter
@Setter
//@NoArgsConstructor

public class UserEntity extends AbstractEntity<Long> {

    @Column(name="name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "pwd")
    private String pwd;
    static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\".]]+");

    public UserEntity() {
        super();
        this.name = "";
        this.surname = "";
        this.email = "";
        this.pwd = "";
    }

    public void update(UserEntity user) {
        //        super();
        this.name = user.name;
        this.surname = user.surname;
        this.email = user.email;
        this.pwd = user.pwd;
    }

    public Instant getCreationDate(){
        return super.getCreationDate();
    }

    public void setCreationDate(Instant date){
        super.setCreationDate(date);
    }

    public UserEntity(String name, String surname, String email) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(surname) || StringUtils.isEmpty(email))
            throw new IllegalArgumentException("UserEntity.UserEntity insufficient initial data");
        email = email.toLowerCase();
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
        if(super.isValid() && StringUtils.hasText(surname) && StringUtils.hasText(email) && StringUtils.hasText(pwd)){
            Matcher m = emailPattern.matcher(email);
            return m.matches();
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof UserEntity)) return false;
        UserEntity entity = (UserEntity) o;
        if(Objects.isNull(entity.getEmail())) return false;
        return(email.equals(entity.email));
    }


    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
