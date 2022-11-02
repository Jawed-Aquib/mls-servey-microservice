package com.marketlogic.serveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyAnswerModel {

    private Integer id;

    private String value;

    private Boolean isSelected;
}
