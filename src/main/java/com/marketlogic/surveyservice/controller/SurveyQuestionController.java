package com.marketlogic.surveyservice.controller;

import com.marketlogic.surveyservice.model.SurveyQuestionModel;
import com.marketlogic.surveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.surveyservice.service.SurveyQuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/survey/survey-questions")
@Log4j2
public class SurveyQuestionController {

    @Autowired
    SurveyQuestionService surveyQuestionService;


    @GetMapping()
    public List<SurveyQuestionModel> getSurveyQuestions(){
        log.info("Survey questions being fetched");
        return surveyQuestionService.getSurveyQuestion();
    }

    @PutMapping
    public List<SurveyQuestionModel> updateSurveyQuestions(@RequestBody List<SurveyQuestionStatusModel> surveyQuestionStatusModels){
        log.info("Updating the survey questions status");
        return surveyQuestionService.updateSurveyQuestions(surveyQuestionStatusModels);
    }

    @PostMapping
    public SurveyQuestionModel createSurveyQuestionModel(SurveyQuestionModel surveyQuestionModel){

        log.info("Creation a question with name {}", surveyQuestionModel.getQuestion());
        return surveyQuestionService.createSurveyQuestionModel(surveyQuestionModel);
    }

}
