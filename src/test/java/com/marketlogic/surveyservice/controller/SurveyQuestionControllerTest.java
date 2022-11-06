package com.marketlogic.surveyservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketlogic.surveyservice.model.SurveyAnswerModel;
import com.marketlogic.surveyservice.model.SurveyModel;
import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.service.SurveyQuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SurveyQuestionController.class)
public class SurveyQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SurveyQuestionService surveyQuestionService;

    @Test
    public void testGetSurveyQuestions() throws Exception {

        List<SurveyQuestionModel> surveyQuestionModels = getSurveyQuestionModels();
        when(surveyQuestionService.getSurveyQuestion()).thenReturn(surveyQuestionModels);
        mockMvc.perform(get("/survey/survey-questions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].question", is("question 1")))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].surveyAnswerModels[0].id", is(1)))
                .andExpect(jsonPath("$[0].surveyAnswerModels[0].value", is("option 1")))
                .andExpect(jsonPath("$[0].surveyAnswerModels[0].isSelected", is(Boolean.FALSE)))
                .andExpect(jsonPath("$[0].surveyAnswerModels[1].id", is(2)))
                .andExpect(jsonPath("$[0].surveyAnswerModels[1].value", is("option 2")))
                .andExpect(jsonPath("$[0].surveyAnswerModels[1].isSelected", is(Boolean.FALSE)))
                .andReturn();
    }

    @Test
    public void testCreateSurveyQuestionModel() throws Exception {
        SurveyQuestionModel surveyQuestionModel = getSurveyQuestionModel();
        when(surveyQuestionService.createSurveyQuestionModel(any())).thenReturn(surveyQuestionModel);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/survey/survey-questions")
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsBytes(surveyQuestionModel));
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is("question 1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.surveyAnswerModels[0].id", is(1)))
                .andExpect(jsonPath("$.surveyAnswerModels[0].value", is("option 1")))
                .andExpect(jsonPath("$.surveyAnswerModels[0].isSelected", is(Boolean.FALSE)))
                .andExpect(jsonPath("$.surveyAnswerModels[1].id", is(2)))
                .andExpect(jsonPath("$.surveyAnswerModels[1].value", is("option 2")))
                .andExpect(jsonPath("$.surveyAnswerModels[1].isSelected", is(Boolean.FALSE)))
                .andReturn();
    }
    private SurveyQuestionModel getSurveyQuestionModel(){
        return SurveyQuestionModel.builder()
                .id(1)
                .question("question 1")
                .surveyAnswerModels(getSurveyAnswerModels())
                .build();
    }
    private List<SurveyQuestionModel> getSurveyQuestionModels(){
        SurveyQuestionModel surveyQuestionModel = SurveyQuestionModel.builder()
                .id(1)
                .question("question 1")
                .surveyAnswerModels(getSurveyAnswerModels())
                .build();
        return Arrays.asList(surveyQuestionModel);
    }
    private List<SurveyAnswerModel> getSurveyAnswerModels(){
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
}
