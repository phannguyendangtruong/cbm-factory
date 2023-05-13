package com.factory.sqldatabase.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.factory.sqldatabase.dto.request.user.UserUpdateDTO;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "full_name", columnDefinition="text")
    private String fullName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", length = 511)
    private String password;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "is_active")
    private Boolean isActive = true;
        
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    public void setInfo(UserUpdateDTO user) {
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
    }

}
