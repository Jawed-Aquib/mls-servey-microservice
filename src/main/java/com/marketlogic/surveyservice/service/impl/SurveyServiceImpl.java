package com.marketlogic.surveyservice.service.impl;

import com.marketlogic.surveyservice.entity.SurveyEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionMappingEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.model.SurveyModel;
import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.repository.SurveyQuestionMappingRepository;
import com.marketlogic.surveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.surveyservice.repository.SurveyRepository;
import com.marketlogic.surveyservice.service.SurveyAnswerService;
import com.marketlogic.surveyservice.service.SurveyQuestionService;
import com.marketlogic.surveyservice.service.SurveyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    SurveyQuestionMappingRepository surveyQuestionMappingRepository;

    @Autowired
    SurveyQuestionService surveyQuestionService;

    @Autowired
    SurveyAnswerService surveyAnswerService;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;
    @Override
    public SurveyModel createSurvey(SurveyModel surveyModel) {
        log.info("Creating a new survey in db");
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setSurveyName(surveyModel.getSurveyName());
        surveyEntity = surveyRepository.save(surveyEntity);
        log.info("Survey is saved in db {}", surveyEntity);
        surveyModel.setSurveyId(surveyEntity.getId());
        List<SurveyQuestionModel> surveyQuestionModels = new ArrayList<>();
        for(SurveyQuestionModel surveyQuestionModel : surveyModel.getSurveyQuestionModels()){
            SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
            if(null != surveyQuestionModel.getId()){
                Optional<SurveyQuestionEntity> optionalSurveyQuestionEntity = surveyQuestionRepository.findById(surveyQuestionModel.getId());
                if(optionalSurveyQuestionEntity.isPresent()) {
                    surveyQuestionEntity = optionalSurveyQuestionEntity.get();
                    log.info("Question is already present in db no need to to anything just create a survey question mapping :: {}", surveyQuestionEntity);
                    surveyQuestionModel = getSurveyQuestionModelFromEntity(surveyQuestionEntity);
                }
                else{
                    log.info("Survey Question with id {} is not present in db", surveyQuestionModel.getId());
                    surveyQuestionModel = surveyQuestionService.createSurveyQuestionModel(surveyQuestionModel);
                    surveyQuestionEntity = surveyQuestionRepository.findById(surveyQuestionModel.getId()).get();
                }
            }
            else{
                log.info("Question is not present in db create a new one");
                surveyQuestionModel = surveyQuestionService.createSurveyQuestionModel(surveyQuestionModel);
                surveyQuestionEntity = surveyQuestionRepository.findById(surveyQuestionModel.getId()).get();
            }
            log.info("Mapping between Survey {} and question {} added in db", surveyEntity,
                    surveyQuestionEntity);
           saveSurveyQuestionMapping(surveyEntity,
                    surveyQuestionEntity);
            surveyQuestionModels.add(surveyQuestionModel);
        }
        surveyModel.setSurveyQuestionModels(surveyQuestionModels);

        return surveyModel;
    }
    private SurveyQuestionModel getSurveyQuestionModelFromEntity(SurveyQuestionEntity surveyQuestionEntity){
        SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel.builder()
                .id(surveyQuestionEntity.getId())
                .question(surveyQuestionEntity.getQuestion())
                .surveyAnswerModels(surveyAnswerService.getSurveyAnswerModelByQuestion(surveyQuestionEntity))
                .build();
        return surveyQuestionModel;
    }
    private SurveyQuestionMappingEntity saveSurveyQuestionMapping(SurveyEntity surveyEntity, SurveyQuestionEntity surveyQuestionEntity){

        SurveyQuestionMappingEntity surveyQuestionMappingEntity = new SurveyQuestionMappingEntity();
        surveyQuestionMappingEntity.setSurveyEntity(surveyEntity);
        surveyQuestionMappingEntity.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyQuestionMappingEntity = surveyQuestionMappingRepository.save(surveyQuestionMappingEntity);
        return surveyQuestionMappingEntity;
    }


    @Override
    public SurveyModel getSurveyByName(String surveyName) {
        log.info("Fetching survey from db with name {}", surveyName);
        SurveyEntity surveyEntity = surveyRepository.findBySurveyName(surveyName);
        SurveyModel surveyModel = null;
        List<SurveyQuestionModel> surveyQuestionModels = new ArrayList<>();
        if(null != surveyEntity){
            List<SurveyQuestionMappingEntity> surveyQuestionMappingEntities =
                    surveyQuestionMappingRepository.findBySurveyEntity(surveyEntity);
            log.info("Total number of question linked with {} are {}", surveyName, surveyQuestionMappingEntities.size());
            for(SurveyQuestionMappingEntity questionMappingEntity : surveyQuestionMappingEntities){
                if(null != questionMappingEntity && null != questionMappingEntity.getSurveyQuestionEntity()) {
                    SurveyQuestionEntity surveyQuestionEntity = questionMappingEntity.getSurveyQuestionEntity();
                    if(surveyQuestionEntity.getIsActive()) {
                        log.info("Question {} is linked with {}", surveyQuestionEntity, surveyName);
                        SurveyQuestionModel surveyQuestionModel = getSurveyQuestionModelFromEntity(surveyQuestionEntity);
                        log.info("Fetching Answers for question {}", surveyQuestionEntity);
                        List<SurveyAnswerModel> surveyAnswerModels = surveyAnswerService.getSurveyAnswerModelByQuestion(surveyQuestionEntity);
                        surveyQuestionModel.setSurveyAnswerModels(surveyAnswerModels);
                        surveyQuestionModels.add(surveyQuestionModel);
                    }
                }
            }
            surveyModel = SurveyModel.builder()
                    .surveyId(surveyEntity.getId())
                    .surveyName(surveyEntity.getSurveyName())
                    .surveyQuestionModels(surveyQuestionModels).build();
        }
        return surveyModel;
    }
}
