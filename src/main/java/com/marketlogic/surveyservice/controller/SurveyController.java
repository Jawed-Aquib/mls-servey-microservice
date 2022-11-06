package com.marketlogic.surveyservice.controller;

import com.marketlogic.surveyservice.model.SurveyModel;
import com.marketlogic.surveyservice.service.SurveyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/survey")
@Log4j2
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @PostMapping
    public SurveyModel createSurvey(@RequestBody SurveyModel surveyModel){
        log.info("Creating the survey with name {}", surveyModel.getSurveyName());
        return surveyService.createSurvey(surveyModel);
    }
    @GetMapping
    public SurveyModel getSurveyByName(@RequestParam String surveyName){
      log.info("Getting survey with  name {}", surveyName);
      return surveyService.getSurveyByName(surveyName);
    }
}
