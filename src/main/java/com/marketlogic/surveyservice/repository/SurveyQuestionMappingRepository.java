package com.marketlogic.surveyservice.repository;

import com.marketlogic.surveyservice.entity.SurveyEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionMappingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurveyQuestionMappingRepository extends CrudRepository<SurveyQuestionMappingEntity, Integer> {
    List<SurveyQuestionMappingEntity> findBySurveyEntity(SurveyEntity surveyEntity);
}
