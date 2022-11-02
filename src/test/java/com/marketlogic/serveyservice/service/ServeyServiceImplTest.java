package com.marketlogic.serveyservice.service;


import com.marketlogic.serveyservice.entity.SurveyAnswerEntity;
import com.marketlogic.serveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.serveyservice.model.SurveyAnswerModel;
import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.serveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.serveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.serveyservice.service.impl.SurveyServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServeyServiceImplTest {

    @InjectMocks
    SurveyServiceImpl surveyService;

    @Mock
    SurveyQuestionRepository surveyQuestionRepository;

    @Mock
    SurveyAnswerRepository surveyAnswerRepository;

    @Test
    public void testGetServeyQuestions(){
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(true);
        when(surveyQuestionRepository.findByIsActive(true)).thenReturn(Arrays.asList(surveyQuestionEntity));

        when(surveyAnswerRepository.findBySurveyQuestionEntity(surveyQuestionEntity)).thenReturn(
                getSurveyAnswerEntities(surveyQuestionEntity)
        );

        List<SurveyQuestionModel> actualSurveyQuestionModelList = surveyService.getSurveyQuestion();

        SurveyAnswerModel surveyAnswerModel1 =  SurveyAnswerModel.builder()
                .id(1)
                .value("option 1")
                .isSelected(false)
                .build();
        SurveyAnswerModel surveyAnswerModel2 =  SurveyAnswerModel.builder()
                .id(2)
                .value("option 2")
                .isSelected(false)
                .build();

        SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel.builder()
                .id(1)
                .question("question 1")
                .surveyAnswerModels(Arrays.asList(surveyAnswerModel1, surveyAnswerModel2))
                .build();

        List<SurveyQuestionModel> expectedQuestionsModelList = Arrays.asList(surveyQuestionModel);

        for(int i = 0; i < expectedQuestionsModelList.size();i++){
            SurveyQuestionModel  actualSurveyQuestionModel = actualSurveyQuestionModelList.get(i);
            SurveyQuestionModel expectedSurveyQuestionModel = expectedQuestionsModelList.get(i);
            assertEquals(actualSurveyQuestionModel, expectedSurveyQuestionModel);
        }

    }
    @Test
    public void testUpdateServeyQuestions(){

        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(false);
        SurveyQuestionStatusModel surveyQuestionStatusModel = SurveyQuestionStatusModel
                .builder()
                .questionId(1)
                .questionStatus(true)
                .build();
        List<SurveyQuestionStatusModel> surveyQuestionStatusModels = Arrays.asList(surveyQuestionStatusModel);
        when(surveyQuestionRepository.save(any())).thenReturn(surveyQuestionEntity);
        when(surveyQuestionRepository.findByIdAndIsActive(any(), any())).thenReturn(surveyQuestionEntity);
        when(surveyAnswerRepository.findBySurveyQuestionEntity(any())).thenReturn(
                getSurveyAnswerEntities(surveyQuestionEntity)
        );
        List<SurveyQuestionModel> actualSurveyQuestionModels = surveyService.updateSurveyQuestions(surveyQuestionStatusModels);

        SurveyAnswerModel surveyAnswerModel1 =  SurveyAnswerModel.builder()
                .id(1)
                .value("option 1")
                .isSelected(false)
                .build();
        SurveyAnswerModel surveyAnswerModel2 =  SurveyAnswerModel.builder()
                .id(2)
                .value("option 2")
                .isSelected(false)
                .build();

        SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel.builder()
                .id(1)
                .question("question 1")
                .surveyAnswerModels(Arrays.asList(surveyAnswerModel1, surveyAnswerModel2))
                .build();

        List<SurveyQuestionModel> expectedQuestionsModelList = Arrays.asList(surveyQuestionModel);

        for(int i = 0; i < expectedQuestionsModelList.size();i++){
            SurveyQuestionModel  actualSurveyQuestionModel = expectedQuestionsModelList.get(i);
            SurveyQuestionModel expectedSurveyQuestionModel = expectedQuestionsModelList.get(i);
            assertEquals(actualSurveyQuestionModel, expectedSurveyQuestionModel);
        }
      /*  for(int i = 0;i < surveyQuestionModels.size();i++){
            SurveyQuestionModel surveyQuestionModel = surveyQuestionModels.get(i);
            System.out.println(surveyQuestionModel.getId() + " " + surveyQuestionModel.getQuestion());
            for(SurveyAnswerModel answerModel : surveyQuestionModel.getSurveyAnswerModels()){
                System.out.println(answerModel.getId() + " " + answerModel.getValue() + " " + answerModel.getIsSelected());
            }
        }*/
    }

    private SurveyQuestionEntity getSurveyQuestionEntityWithStatus(Boolean status){
        SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
        surveyQuestionEntity.setQuestion("question 1");
        surveyQuestionEntity.setId(1);
        surveyQuestionEntity.setIsActive(status);
        return surveyQuestionEntity;
    }
    private  List<SurveyAnswerEntity> getSurveyAnswerEntities(SurveyQuestionEntity surveyQuestionEntity){
        List<SurveyAnswerEntity> answerEntityList = new ArrayList<>();
        SurveyAnswerEntity surveyAnswerEntity1 = new SurveyAnswerEntity();
        surveyAnswerEntity1.setId(1);
        surveyAnswerEntity1.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyAnswerEntity1.setValue("option 1");

        SurveyAnswerEntity surveyAnswerEntity2 = new SurveyAnswerEntity();
        surveyAnswerEntity2.setId(2);
        surveyAnswerEntity2.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyAnswerEntity2.setValue("option 2");
       // List<SurveyAnswerEntity> answerEntityList = new ArrayList<>();
        answerEntityList.add(surveyAnswerEntity1);
        answerEntityList.add(surveyAnswerEntity2);
        return answerEntityList;
    }

}
