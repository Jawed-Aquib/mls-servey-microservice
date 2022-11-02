package com.marketlogic.serveyservice.controller;

import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.serveyservice.service.SurveyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/survey-questions")
@Log4j2
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @GetMapping()
    public List<SurveyQuestionModel> getSurveyQuestions(){
        log.info("Survey questions being fetched");
        return surveyService.getSurveyQuestion();
    }

    @PutMapping
    public List<SurveyQuestionModel> updateSurveyQuestions(@RequestBody List<SurveyQuestionStatusModel> surveyQuestionStatusModels){
        log.info("Updating the survey questions status");
        return surveyService.updateSurveyQuestions(surveyQuestionStatusModels);
    }



}
