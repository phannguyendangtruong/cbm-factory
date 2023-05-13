package com.amitgroup.sqldatabase.entities;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_token")
public class UserToken extends BaseEntity {
    @Column(name="token")
    public String token;
    @Column(name="expired_time")
    private Date expiredTime;
    @ManyToOne
    @JoinColumn(name = "userId")
    public User user;

}
