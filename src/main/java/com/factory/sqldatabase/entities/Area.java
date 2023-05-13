package com.factory.sqldatabase.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "area")
public class Area extends BaseEntity {
    @Column(name = "name", columnDefinition="text")
    private String name;
    @Column(name = "description", columnDefinition="text")
    private String description;

}
