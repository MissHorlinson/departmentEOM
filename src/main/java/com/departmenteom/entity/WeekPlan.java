package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.StudyingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "week_plan")
@Getter
@Setter
public class WeekPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "semester")
    private int semester;

    @Column(name = "start_week")
    private int startWeek;

    @Column(name = "term")
    private int term;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanInfo weekPlanInfo;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private StudyingType studyingType;


    @Override
    public String toString() {
        return "WeekPlan(id = " + id +
                ", semester = " + semester +
                ", startWeek = " + startWeek +
                ", term = " + term +
                ", plan = " + weekPlanInfo +
                ", studyingType = " + studyingType +
                ")";
    }
}
