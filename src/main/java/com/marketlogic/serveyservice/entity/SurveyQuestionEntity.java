package com.marketlogic.serveyservice.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "survey_question")
public class SurveyQuestionEntity {

    @Id
    private Integer id;
    private String question;
    @Column(name = "is_active")
    private Boolean isActive;


}
