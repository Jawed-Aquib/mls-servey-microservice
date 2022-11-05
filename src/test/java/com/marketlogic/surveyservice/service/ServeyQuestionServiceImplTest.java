package com.marketlogic.surveyservice.service;


import com.marketlogic.surveyservice.entity.SurveyOptionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.surveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.surveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.surveyservice.service.impl.SurveyQuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServeyQuestionServiceImplTest {

    @InjectMocks
    SurveyQuestionServiceImpl surveyQuestionService;

    @Mock
    SurveyQuestionRepository surveyQuestionRepository;

    @Mock
    SurveyAnswerRepository surveyAnswerRepository;

    @Mock
    SurveyAnswerService surveyAnswerService;

    @Test
    public void testGetServeyQuestions(){
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(true);
        when(surveyQuestionRepository.findByIsActive(true)).thenReturn(Arrays.asList(surveyQuestionEntity));

        when(surveyAnswerRepository.findBySurveyQuestionEntity(surveyQuestionEntity)).thenReturn(
                getSurveyAnswerEntities(surveyQuestionEntity)
        );

        List<SurveyQuestionModel> actualSurveyQuestionModelList = surveyQuestionService.getSurveyQuestion();

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
        List<SurveyQuestionModel> actualSurveyQuestionModels = surveyQuestionService.updateSurveyQuestions(surveyQuestionStatusModels);

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
    }

    @Test
    public void testCreateSurveyQuestionModel(){
        SurveyAnswerModel answerModel1 = SurveyAnswerModel.builder()
                .id(1)
                .value("option 1")
                .isSelected(Boolean.FALSE)
                .build();
        SurveyAnswerModel answerModel2 = SurveyAnswerModel.builder()
                .id(2)
                .value("option 2")
                .isSelected(Boolean.FALSE)
                .build();

        SurveyQuestionModel expectedSurveyQuestionModel = SurveyQuestionModel.builder()
                .id(1)
                .question("question 1")
                .surveyAnswerModels(Arrays.asList(answerModel1, answerModel2))
                .build();
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(Boolean.TRUE);
        when(surveyQuestionRepository.save(any())).thenReturn(surveyQuestionEntity);
        when(surveyAnswerService.createSurveyAnswer(answerModel1, surveyQuestionEntity)).thenReturn(answerModel1);
        when(surveyAnswerService.createSurveyAnswer(answerModel2, surveyQuestionEntity)).thenReturn(answerModel2);
        SurveyQuestionModel actualSurveyQuestionModel = surveyQuestionService.createSurveyQuestionModel(expectedSurveyQuestionModel);
        assertEquals(expectedSurveyQuestionModel, actualSurveyQuestionModel);
    }

    private SurveyQuestionEntity getSurveyQuestionEntityWithStatus(Boolean status){
        SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
        surveyQuestionEntity.setQuestion("question 1");
        surveyQuestionEntity.setId(1);
        surveyQuestionEntity.setIsActive(status);
        return surveyQuestionEntity;
    }
    private  List<SurveyOptionEntity> getSurveyAnswerEntities(SurveyQuestionEntity surveyQuestionEntity){
        List<SurveyOptionEntity> answerEntityList = new ArrayList<>();
        SurveyOptionEntity surveyOptionEntity1 = new SurveyOptionEntity();
        surveyOptionEntity1.setId(1);
        surveyOptionEntity1.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyOptionEntity1.setValue("option 1");

        SurveyOptionEntity surveyOptionEntity2 = new SurveyOptionEntity();
        surveyOptionEntity2.setId(2);
        surveyOptionEntity2.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyOptionEntity2.setValue("option 2");
       // List<SurveyAnswerEntity> answerEntityList = new ArrayList<>();
        answerEntityList.add(surveyOptionEntity1);
        answerEntityList.add(surveyOptionEntity2);
        return answerEntityList;
    }

}
