package com.amitgroup.sqldatabase.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login_activity")
@Data
public class LoginActivity extends BaseEntity {
    @Column(name = "login_at")
    public Date loginAt = new Date();
    @Column(name = "user_agent")
    public String userAgent;
    @Column(name = "login_ip")
    public String loginIp;
    @Column(name = "expired_time")
    public Date expiredTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
