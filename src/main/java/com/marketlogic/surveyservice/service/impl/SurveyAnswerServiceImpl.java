package com.marketlogic.surveyservice.service.impl;

import com.marketlogic.surveyservice.entity.SurveyOptionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.surveyservice.service.SurveyAnswerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;
    @Override
    public SurveyAnswerModel createSurveyAnswer(SurveyAnswerModel surveyAnswerModel, SurveyQuestionEntity surveyQuestionEntity) {
        log.info("Adding survey answers entity in the db for question {}", surveyQuestionEntity.toString());
        SurveyOptionEntity surveyOptionEntity = new SurveyOptionEntity();
        surveyOptionEntity.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyOptionEntity.setValue(surveyAnswerModel.getValue());
        SurveyOptionEntity savedSurveyOptionEntity = surveyAnswerRepository.save(surveyOptionEntity);
        log.info("survey answer entity  saved in db is {}", surveyOptionEntity);
        surveyAnswerModel.setId(savedSurveyOptionEntity.getId());
        surveyAnswerModel.setIsSelected(Boolean.FALSE);
        surveyAnswerModel.setValue(savedSurveyOptionEntity.getValue());
        return surveyAnswerModel;
    }

    @Override
    public List<SurveyAnswerModel> getSurveyAnswerModelByQuestion(SurveyQuestionEntity surveyQuestionEntity) {
        log.info("Get survey answers for the question {}", surveyQuestionEntity.toString());
        List<SurveyOptionEntity> surveyAnswerEntities = surveyAnswerRepository.findBySurveyQuestionEntity(surveyQuestionEntity);
        List<SurveyAnswerModel> surveyAnswerModels = new ArrayList<>();
        log.info("Total number of answers linked are {}", surveyAnswerEntities.size());
        for(SurveyOptionEntity surveyOptionEntity : surveyAnswerEntities){
            log.info("Survey Answer being fetched for question id {} is {}", surveyQuestionEntity.getId(),
                    surveyOptionEntity);
            SurveyAnswerModel surveyAnswerModel = SurveyAnswerModel.builder()
                    .isSelected(Boolean.FALSE)
                    .id(surveyOptionEntity.getId())
                    .value(surveyOptionEntity.getValue())
                    .build();
            surveyAnswerModels.add(surveyAnswerModel);
        }

        return surveyAnswerModels;
    }
}
