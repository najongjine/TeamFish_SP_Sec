package com.biz.tour.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.tour.service.MarineAnimalService;
import com.google.gson.JsonArray;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MarineAnimalController {
	
	private final MarineAnimalService animalService;

	
	
	
	/*
	@RequestMapping(value="/animal",method=RequestMethod.GET)
	public String getAnimals() throws ParseException {
		
		
		// 해양생물 map에 일정 정보를 모으지 못했을 경우 api 재호출 위해 필요
		int pageNo = 1;
		
		// 해양생물 map에 일정 정보를 모으지 못했을 경우 api 재호출 위해 필요
		Map<String ,String> selectedAnimalMap = new HashMap<String,String>();
		animalService.getAnimals(pageNo, selectedAnimalMap);
		
		return null;
	}
	*/
	
	
	
	// 해양생물 API의 데이터를 DB에 저장하는 작업
	@RequestMapping(value="/animaltable",method=RequestMethod.GET)
	public String animalTable() throws ParseException, IOException {
		
		
		
		int ret = animalService.insertService();
		
		return null;
	}
	
}
