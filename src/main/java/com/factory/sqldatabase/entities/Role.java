package com.factory.sqldatabase.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "role")
public class Role extends BaseEntity {
    @Column(name = "name", columnDefinition="text")
    private String name;
    private String code;
}
