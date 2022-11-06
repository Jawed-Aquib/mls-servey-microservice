package com.marketlogic.surveyservice.service;

import com.marketlogic.surveyservice.model.SurveyModel;

public interface SurveyService {
    public SurveyModel createSurvey(SurveyModel surveyModel);
    public SurveyModel getSurveyByName(String name);
}
