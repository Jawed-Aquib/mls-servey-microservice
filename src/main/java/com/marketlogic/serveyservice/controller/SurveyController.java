package com.marketlogic.serveyservice.controller;

import com.marketlogic.serveyservice.model.SurveyQuestionModel;
import com.marketlogic.serveyservice.model.SurveyQuestionStatusModel;
import com.marketlogic.serveyservice.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/survey-questions")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @GetMapping()
    public List<SurveyQuestionModel> getSurveyQuestions(){
        return surveyService.getSurveyQuestion();
    }

    @PutMapping
    public List<SurveyQuestionModel> updateSurveyQuestions(@RequestBody List<SurveyQuestionStatusModel> surveyQuestionStatusModels){

        return surveyService.updateSurveyQuestions(surveyQuestionStatusModels);
    }



}
