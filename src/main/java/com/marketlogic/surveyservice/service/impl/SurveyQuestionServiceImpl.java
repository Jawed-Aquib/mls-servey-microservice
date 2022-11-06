package com.marketlogic.surveyservice.service.impl;

import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.surveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.surveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.surveyservice.service.SurveyAnswerService;
import com.marketlogic.surveyservice.service.SurveyQuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;
    @Autowired
    SurveyAnswerService surveyAnswerService;

    @Override
    public List<SurveyQuestionModel> getSurveyQuestion() {
        List<SurveyQuestionModel> surveyQuestionModelList = new ArrayList<>();
        log.info("Fetching all the survey questions which are active");
        Iterator<SurveyQuestionEntity> surveyQuestionEntityIterator = surveyQuestionRepository.findByIsActive(true)
                .iterator();
        while(surveyQuestionEntityIterator.hasNext()){
            SurveyQuestionEntity surveyQuestionEntity = surveyQuestionEntityIterator.next();
            log.info("Fetching all options linked to question with ID :: {}",surveyQuestionEntity.getId() );
            List<SurveyAnswerModel> surveyAnswerModelList = getSurveyAnswerModelsByQuestions(surveyQuestionEntity);
            surveyQuestionModelList.add(new SurveyQuestionModel(surveyQuestionEntity.getId(),
                    surveyQuestionEntity.getQuestion(),surveyAnswerModelList
                    ));
        }

         return surveyQuestionModelList;
    }

    @Override
    public List<SurveyQuestionModel> updateSurveyQuestions(List<SurveyQuestionStatusModel> surveyQuestionStatusModelList) {
      List<SurveyQuestionModel> surveyQuestionModels = new ArrayList<>();
       for(SurveyQuestionStatusModel surveyQuestionStatusModel : surveyQuestionStatusModelList){
           log.info("Active status of question with id {}  being will be updated to :: {}",surveyQuestionStatusModel.getQuestionId(),  surveyQuestionStatusModel.getQuestionStatus());
           surveyQuestionRepository.findById(surveyQuestionStatusModel.getQuestionId())
                   .ifPresent(surveyQuestionEntity -> {
                       log.info("Current active status of the question with id {}  is :: {}", surveyQuestionEntity.getId(),  surveyQuestionEntity.getIsActive());
                       surveyQuestionEntity.setIsActive(surveyQuestionStatusModel.getQuestionStatus());
                       surveyQuestionRepository.save(surveyQuestionEntity);
                   });
          Optional.of(surveyQuestionRepository.findByIdAndIsActive(surveyQuestionStatusModel.getQuestionId(),
                  surveyQuestionStatusModel.getQuestionStatus()))
                  .ifPresent(surveyQuestionEntity -> {
                      log.info("Active status of the question with id {} after updation is :: {}", surveyQuestionEntity.getId(), surveyQuestionEntity.getIsActive());
                      surveyQuestionModels.add(new SurveyQuestionModel(
                              surveyQuestionEntity.getId(), surveyQuestionEntity.getQuestion(),
                              getSurveyAnswerModelsByQuestions(surveyQuestionEntity)
                      ));
                  });

       }
        return surveyQuestionModels;
    }

    @Override
    public SurveyQuestionModel createSurveyQuestionModel(SurveyQuestionModel surveyQuestionModel) {
        SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
        surveyQuestionEntity.setQuestion(surveyQuestionModel.getQuestion());
        surveyQuestionEntity.setIsActive(Boolean.TRUE);
        surveyQuestionEntity = surveyQuestionRepository.save(surveyQuestionEntity);
        log.info("Survey question entity saved in db {}", surveyQuestionEntity.toString());
        surveyQuestionModel.setId(surveyQuestionEntity.getId());
        surveyQuestionModel.setQuestion(surveyQuestionEntity.getQuestion());
        List<SurveyAnswerModel> surveyAnswerModels = new ArrayList<>();
        for(SurveyAnswerModel surveyAnswerModel : surveyQuestionModel.getSurveyAnswerModels()){
            log.info("Adding the answers into the db");
            surveyAnswerModels.add(surveyAnswerService.createSurveyAnswer(surveyAnswerModel, surveyQuestionEntity));
        }
        surveyQuestionModel.setSurveyAnswerModels(surveyAnswerModels);
        return surveyQuestionModel;
    }

    private List<SurveyAnswerModel> getSurveyAnswerModelsByQuestions(SurveyQuestionEntity surveyQuestionEntity) {
        List<SurveyAnswerModel> surveyAnswerModelList = new ArrayList<>();
        surveyAnswerRepository.findBySurveyQuestionEntity(surveyQuestionEntity)
                .iterator()
                .forEachRemaining(surveyAnswerEntity -> surveyAnswerModelList.add(
                        new SurveyAnswerModel(surveyAnswerEntity.getId(),
                                surveyAnswerEntity.getValue(), false)
                ));
        log.info("{} options are linked with question with id {}", surveyAnswerModelList.size(),
                 surveyQuestionEntity.getId());
        return surveyAnswerModelList;
    }
}
