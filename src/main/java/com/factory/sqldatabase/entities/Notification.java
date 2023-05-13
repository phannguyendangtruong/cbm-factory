package com.factory.sqldatabase.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", columnDefinition="text")
    private String content;
    private boolean isViewed;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "maintenance_request_id")
    private MaintenanceRequest maintenanceRequest;

    @ManyToOne
    @JoinColumn(name = "user_receive")
    private User userReceive;

    @ManyToOne
    @JoinColumn(name = "user_create")
    private User userCreate;




}
