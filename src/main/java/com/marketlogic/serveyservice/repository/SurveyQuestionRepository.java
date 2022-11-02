package com.marketlogic.serveyservice.repository;

import com.marketlogic.serveyservice.entity.SurveyQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends CrudRepository<SurveyQuestionEntity, Integer> {
    List<SurveyQuestionEntity> findByIsActive(Boolean activeStatus);
}
