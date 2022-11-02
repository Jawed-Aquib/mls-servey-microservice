package com.marketlogic.serveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnswerModel {

    private Integer id;

    private String value;

    private Boolean isSelected;
}
