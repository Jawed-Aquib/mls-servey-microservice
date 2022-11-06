package com.marketlogic.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyAnswerModel {

    private Integer id;

    private String value;

    private Boolean isSelected;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyAnswerModel that = (SurveyAnswerModel) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value) && Objects.equals(isSelected, that.isSelected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, isSelected);
    }
}
