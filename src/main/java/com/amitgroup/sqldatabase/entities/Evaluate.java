package com.amitgroup.sqldatabase.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "evaluate")
public class Evaluate extends BaseEntity{
    @Column(name = "content", columnDefinition="text")
    private String content;
    private Integer quality;
    private Date endDate;
    @Column(name = "maintenance_request_id", insertable = false, updatable = false)
    private Long maintenanceRequestId;
    @Column(name = "area_id", insertable = false, updatable = false)
    private Long areaId;
    
    @ManyToOne  
    @JoinColumn(name = "maintenance_request_id")
    private MaintenanceRequest maintenanceRequest;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

}
