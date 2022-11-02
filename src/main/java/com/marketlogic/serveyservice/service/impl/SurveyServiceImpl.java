package com.marketlogic.serveyservice.service.impl;

import com.marketlogic.serveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.serveyservice.model.SurveyAnswerModel;
import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.serveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.serveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.serveyservice.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    @Override
    public List<SurveyQuestionModel> getSurveyQuestion() {
        List<SurveyQuestionModel> surveyQuestionModelList = new ArrayList<>();
        Iterator<SurveyQuestionEntity> surveyQuestionEntityIterator = surveyQuestionRepository.findByIsActive(true)
                .iterator();
        while(surveyQuestionEntityIterator.hasNext()){
            SurveyQuestionEntity surveyQuestionEntity = surveyQuestionEntityIterator.next();
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
           surveyQuestionRepository.findById(surveyQuestionStatusModel.getQuestionId())
                   .ifPresent(surveyQuestionEntity -> {
                       surveyQuestionEntity.setIsActive(surveyQuestionStatusModel.getQuestionStatus());
                       surveyQuestionRepository.save(surveyQuestionEntity);
                   });
          surveyQuestionRepository.findById(surveyQuestionStatusModel.getQuestionId())
                  .ifPresent(surveyQuestionEntity -> {
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
        return surveyAnswerModelList;
    }
}
