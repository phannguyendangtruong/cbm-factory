package com.amitgroup.sqldatabase.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "machine")
public class Machine extends BaseEntity{
    private String name;
    @Column(name = "code", unique = true)
    private String code;
    private String description;

}
