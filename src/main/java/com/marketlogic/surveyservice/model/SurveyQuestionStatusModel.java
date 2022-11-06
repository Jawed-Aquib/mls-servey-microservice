package com.marketlogic.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestionStatusModel {
    private Integer questionId;
    private Boolean questionStatus;
}
