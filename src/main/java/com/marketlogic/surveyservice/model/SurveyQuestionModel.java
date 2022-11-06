package com.marketlogic.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyQuestionModel {
    private Integer id;
    private String question;
    private List<SurveyAnswerModel> surveyAnswerModels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionModel that = (SurveyQuestionModel) o;
        boolean surveyAnswerModelsCheck = true;
        for(int i = 0; i < this.surveyAnswerModels.size();i++){
            SurveyAnswerModel thisModel = this.surveyAnswerModels.get(0);
            SurveyAnswerModel thatModel = that.surveyAnswerModels.get(0);
            surveyAnswerModelsCheck = surveyAnswerModelsCheck
                    && Objects.equals(thisModel.getId(), thatModel.getId())
                    && Objects.equals(thisModel.getIsSelected(), thatModel.getIsSelected())
                    && Objects.equals(thisModel.getValue(), thatModel.getValue());
        }
        return Objects.equals(id, that.id) && Objects.equals(question, that.question) && surveyAnswerModelsCheck;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, surveyAnswerModels);
    }
}
