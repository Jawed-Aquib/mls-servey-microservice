package com.marketlogic.serveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionStatusModel {
    private Integer questionId;
    private Boolean questionStatus;
}
