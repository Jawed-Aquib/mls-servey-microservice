package com.marketlogic.surveyservice.repository;

import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends CrudRepository<SurveyQuestionEntity, Integer> {
    List<SurveyQuestionEntity> findByIsActive(Boolean activeStatus);
    SurveyQuestionEntity findByIdAndIsActive(Integer id, Boolean isActive);
}
