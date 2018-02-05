package com.ots.dpel.system.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "scheduled_job_calc", schema = "dp")
public class ScheduledJobCalculation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "dt_calculationdatetime")
    private Date calculationDateTime;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    @Column(name = "v_jobgroup")
    private String jobGroup;
    
    @Column(name = "n_order")
    private Integer order;
    
    public ScheduledJobCalculation() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCalculationDateTime() {
        return calculationDateTime;
    }
    
    public void setCalculationDateTime(Date calculationDateTime) {
        this.calculationDateTime = calculationDateTime;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public String getJobGroup() {
        return jobGroup;
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
}
