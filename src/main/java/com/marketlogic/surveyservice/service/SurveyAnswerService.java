package com.marketlogic.surveyservice.service;

import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;

import java.util.List;

public interface SurveyAnswerService {
    public SurveyAnswerModel createSurveyAnswer(SurveyAnswerModel surveyAnswerModel, SurveyQuestionEntity surveyQuestionEntity);
    public List<SurveyAnswerModel> getSurveyAnswerModelByQuestion(SurveyQuestionEntity surveyQuestionEntity);
}
