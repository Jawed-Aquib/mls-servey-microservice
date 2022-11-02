package com.marketlogic.serveyservice.repository;

import com.marketlogic.serveyservice.entity.SurveyAnswerEntity;
import com.marketlogic.serveyservice.entity.SurveyQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswerEntity, Integer> {
    List<SurveyAnswerEntity> findBySurveyQuestionEntity(SurveyQuestionEntity surveyQuestionEntity);
}
