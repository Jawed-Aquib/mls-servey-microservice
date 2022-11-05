package com.marketlogic.surveyservice.repository;

import com.marketlogic.surveyservice.entity.SurveyEntity;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Integer> {
    public SurveyEntity findBySurveyName(String surveyName);
}
