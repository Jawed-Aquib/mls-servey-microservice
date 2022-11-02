package com.marketlogic.serveyservice.service.impl;

import com.marketlogic.serveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.serveyservice.model.SurveyAnswerModel;
import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.serveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.serveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.serveyservice.service.SurveyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

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
