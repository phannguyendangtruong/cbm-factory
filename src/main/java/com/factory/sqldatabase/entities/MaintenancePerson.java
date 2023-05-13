package com.factory.sqldatabase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "maintenance_person")
public class MaintenancePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_main_person")
    private Boolean isMainPerson = false;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "maintenance_request_id")
    private Long maintenanceRequestId;
    @Column(name = "is_done")
    private Boolean isDone = false;
    private Boolean confirm = false;
    @Column(name = "description", columnDefinition="text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "maintenance_request_id", insertable = false, updatable = false)
    private MaintenanceRequest maintenanceRequest;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

}
