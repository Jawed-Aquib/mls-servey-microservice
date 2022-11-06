package com.marketlogic.surveyservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class SurveyModel {
    private Integer surveyId;
    private String surveyName;
    private List<SurveyQuestionModel> surveyQuestionModels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyModel that = (SurveyModel) o;
        boolean surveyQuestionsModelCheck = true;
        for(int i = 0;i < this.surveyQuestionModels.size();i++)
        {
            surveyQuestionsModelCheck = surveyQuestionsModelCheck && this.surveyQuestionModels.get(i).equals(((SurveyModel) o).getSurveyQuestionModels().get(i));
        }
        return Objects.equals(surveyId, that.surveyId) && Objects.equals(surveyName, that.surveyName) && surveyQuestionsModelCheck;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId, surveyName, surveyQuestionModels);
    }
}
