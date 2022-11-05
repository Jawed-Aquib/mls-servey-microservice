package com.marketlogic.surveyservice.service;

import com.marketlogic.surveyservice.entity.SurveyEntity;
import com.marketlogic.surveyservice.entity.SurveyOptionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionEntity;
import com.marketlogic.surveyservice.entity.SurveyQuestionMappingEntity;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.model.SurveyModel;
import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.repository.SurveyQuestionMappingRepository;
import com.marketlogic.surveyservice.repository.SurveyQuestionRepository;
import com.marketlogic.surveyservice.repository.SurveyRepository;
import com.marketlogic.surveyservice.service.impl.SurveyServiceImpl;
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
public class SurveyServiceImplTest {

    @InjectMocks
    SurveyServiceImpl surveyService;

    @Mock
    SurveyQuestionMappingRepository surveyQuestionMappingRepository;

    @Mock
    SurveyQuestionService surveyQuestionService;

    @Mock
    SurveyAnswerService surveyAnswerService;

    @Mock
    SurveyQuestionRepository surveyQuestionRepository;

    @Mock
    SurveyRepository surveyRepository;

    @Test
    public void testCreateSurvey(){
        SurveyEntity surveyEntity = getSurveyEntity();
        when(surveyRepository.save(any())).thenReturn(surveyEntity);
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(Boolean.TRUE);
        when(surveyQuestionRepository.findById(any())).thenReturn(Optional.of(surveyQuestionEntity));
        SurveyQuestionMappingEntity surveyQuestionMappingEntity = new SurveyQuestionMappingEntity();
        surveyQuestionMappingEntity.setSurveyEntity(surveyEntity);
        surveyQuestionMappingEntity.setSurveyQuestionEntity(surveyQuestionEntity);
        when(surveyQuestionMappingRepository.save(any())).thenReturn(surveyQuestionMappingEntity);
        SurveyModel expectedSurveyModel = createSurveyModel();
        SurveyModel actualSurveyModel = surveyService.createSurvey(expectedSurveyModel);
        assertEquals(expectedSurveyModel, actualSurveyModel);
    }

    @Test
    public void testGetSurveyByName(){
        SurveyEntity surveyEntity = getSurveyEntity();
        when(surveyRepository.findBySurveyName("first service")).thenReturn(surveyEntity);
        SurveyQuestionEntity surveyQuestionEntity = getSurveyQuestionEntityWithStatus(Boolean.TRUE);
        SurveyQuestionMappingEntity surveyQuestionMappingEntity = new SurveyQuestionMappingEntity();
        surveyQuestionMappingEntity.setSurveyEntity(surveyEntity);
        surveyQuestionMappingEntity.setSurveyQuestionEntity(surveyQuestionEntity);
        when(surveyQuestionMappingRepository.findBySurveyEntity(surveyEntity)).thenReturn(Arrays.asList(surveyQuestionMappingEntity));
        when(surveyAnswerService.getSurveyAnswerModelByQuestion(surveyQuestionEntity)).thenReturn(getSurveyAnswerModels());
       SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel
               .builder()
               .surveyAnswerModels(getSurveyAnswerModels())
               .question("question 1")
               .id(1)
               .build();
       SurveyModel expectedSurveyModel = SurveyModel.builder()
               .surveyId(1)
               .surveyName("first survey")
               .surveyQuestionModels(Arrays.asList(surveyQuestionModel))
               .build();
       SurveyModel actualSurveyModel = surveyService.getSurveyByName("first service");
       assertEquals(expectedSurveyModel, actualSurveyModel);

    }
    private SurveyEntity getSurveyEntity(){
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setId(1);
        surveyEntity.setSurveyName("first survey");
        return surveyEntity;
    }

    private SurveyModel createSurveyModel(){

        List<SurveyAnswerModel> surveyAnswerModels = getSurveyAnswerModels();
        SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel.builder()
                .surveyAnswerModels(surveyAnswerModels)
                .question("question1 ")
                .id(1).build();
        SurveyModel surveyModel = SurveyModel.builder()
                .surveyQuestionModels(Arrays.asList(surveyQuestionModel))
                .surveyName("first survey")
                .build();
        return surveyModel;
    }

    private  List<SurveyAnswerModel> getSurveyAnswerModels(){
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
        List<SurveyAnswerModel> surveyAnswerModels = new ArrayList<>();
        surveyAnswerModels.add(answerModel1);
        surveyAnswerModels.add(answerModel2);
        return surveyAnswerModels;
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
