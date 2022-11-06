package com.marketlogic.surveyservice.repository;

import com.marketlogic.surveyservice.entity.SurveyOptionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyOptionEntity, Integer> {
    List<SurveyOptionEntity> findBySurveyQuestionEntity(SurveyQuestionEntity surveyQuestionEntity);
}
