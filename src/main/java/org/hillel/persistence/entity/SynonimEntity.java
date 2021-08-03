package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "synonim",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}
        ))
public class SynonimEntity extends AbstractEntity<Long>{
        @Column(name = "name")
        private String name;
        @Column(name = "true_name")
        private String trueName;

        @Override
        public String toString() {
                return "SynonimEntity{" +
                        "name='" + name + '\'' +
                        ", trueName='" + trueName + '\'' +
                        '}';
        }
}
