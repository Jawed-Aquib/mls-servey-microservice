package com.marketlogic.surveyservice.service;

import com.marketlogic.surveyservice.entity.SurveyOptionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.repository.SurveyAnswerRepository;
import com.marketlogic.surveyservice.service.impl.SurveyAnswerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SurveyAnswerServiceImplTest {

    @InjectMocks
    SurveyAnswerServiceImpl surveyAnswerService;

    @Mock
    SurveyAnswerRepository surveyAnswerRepository;


    @Test
    public void testCreateSurveyAnswer(){
        SurveyAnswerModel actualSurveyAnswerModel = SurveyAnswerModel.builder()
                .isSelected(false)
                .value("option 1")
                .id(1)
                .build();
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(Boolean.TRUE);
        SurveyOptionEntity surveyOptionEntity = new SurveyOptionEntity();
        surveyOptionEntity.setId(1);
        surveyOptionEntity.setSurveyQuestionEntity(surveyQuestionEntity);
        surveyOptionEntity.setValue("option 1");
        when(surveyAnswerRepository.save(any())).thenReturn(surveyOptionEntity);
        SurveyAnswerModel expectedSurveyAnswerModel = surveyAnswerService.createSurveyAnswer(actualSurveyAnswerModel, surveyQuestionEntity);
        assertEquals(expectedSurveyAnswerModel, actualSurveyAnswerModel);
    }
    @Test
    public void testGetSurveyAnswerModelByQuestion(){
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(Boolean.TRUE);
        List<SurveyOptionEntity> surveyOptionEntities = getSurveyOptionEntities(surveyQuestionEntity);
        when(surveyAnswerRepository.findBySurveyQuestionEntity(surveyQuestionEntity)).thenReturn(surveyOptionEntities);
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
        List<SurveyAnswerModel> actualSurveyModelList = new ArrayList<>();
        actualSurveyModelList.add(answerModel1);
        actualSurveyModelList.add(answerModel2);

        List<SurveyAnswerModel> expectedSurveyModelList = surveyAnswerService.getSurveyAnswerModelByQuestion(surveyQuestionEntity);

        for(int i = 0;i < expectedSurveyModelList.size();i++)
        {
            assertEquals(expectedSurveyModelList.get(i), actualSurveyModelList.get(i));
        }
    }
    private SurveyQuestionEntity getSurveyQuestionEntityWithStatus(Boolean status){
        SurveyQuestionEntity surveyQuestionEntity = new SurveyQuestionEntity();
        surveyQuestionEntity.setQuestion("question 1");
        surveyQuestionEntity.setId(1);
        surveyQuestionEntity.setIsActive(status);
        return surveyQuestionEntity;
    }
    private List<SurveyOptionEntity> getSurveyOptionEntities(SurveyQuestionEntity surveyQuestionEntity){
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
