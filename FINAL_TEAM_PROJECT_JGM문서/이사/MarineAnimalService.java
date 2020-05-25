package com.biz.tour.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.biz.tour.dao.MarineAnimalDao;
import com.biz.tour.domain.MarineAnimalVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




/*
 * 	
 * 
 * FishAPIController의 detail method에서 좌표값을 받아와
 * 
 * 이곳에서 산출한 해양생물의 좌표값과 매칭시켜
 * 
 * 해당되는 해양생물의 데이터를 FishAPIController로 다시 return해준다
 * 
 * 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarineAnimalService {
	
	
	private final MarineAnimalDao maDao;
/*	
	double baseMapX = 36;
	double baseMapY = 128;
	

	public JsonArray getAnimals(int pageNo, Map<String ,String> selectedAnimalMap) throws ParseException {
		// TODO Auto-generated method stub
		
		
		
		
		
		String queryURL = "http://apis.data.go.kr/B551979/marineOrganismInhabitInfoService/getHabitatGisList";
		String serviceKey = "2D5rJ2dlm%2BXKIkVprSSgKI0HU08V%2FYBLqD8l%2Furac2yM3d8LozeI%2BZJmfDX9%2BsAZY7abFzCGzXhRWQL%2BcQdgSA%3D%3D";
		
		StringBuffer response = null;
	
		try {
			
			queryURL = queryURL + "?ServiceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + 1000 + "&_type=json";
			
			
			URL url = new URL(queryURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-type", "application/json");
			
			System.out.println("Response code: " + connection.getResponseCode());
			
			int responseCode = connection.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			br.close();
			
			System.out.println("HTTP 응답 코드: " + responseCode);
			System.out.println("HTTP BODY: " + response.toString());
			
			
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	this.jSonStrToMap(response.toString(), selectedAnimalMap, pageNo);
		
	 	
		
		return null;
	}


*/
	
	
	
	
	/*

	public List<MarineAnimalVO> jSonStrToMap(String response, Map<String ,String> selectedAnimalMap, int pageNo) throws ParseException {

		 JsonElement responseJsonElement = JsonParser.parseString(response);
		 
		JsonObject responseJsonObj = responseJsonElement.getAsJsonObject();
		 log.debug("과연: "  + responseJsonObj.toString());
		
		 JsonObject responseGetItem = (JsonObject) responseJsonObj.get("response");
		 responseGetItem = (JsonObject) responseGetItem.get("body");
		 responseGetItem = (JsonObject) responseGetItem.get("items");
		 JsonArray responseJsonArr = (JsonArray) responseGetItem.getAsJsonArray("item");
		 
		
	
		 	
		 
		 	
		 	
		 	Gson gSon = new Gson();
		 	
		 	Type listType = new TypeToken<ArrayList<MarineAnimalVO>>(){}.getType();
	 	
		
		 	
		 	List<MarineAnimalVO> marinList = gSon.fromJson(responseJsonArr, listType);
		 	
		 	log.debug("ABCD:" + marinList.toString());
		 	
		 	
		 	
		 	
		 	String sciKr = "";
		 	
		 	
		 	
		 	// 낚시 base의 위도 경도 degree와 일치하는 해양생물의 리스트 모으기(view에 실제 뿌려질 vo리스트(sciKr, chrtr, distrInh, latD, lonD)) 
		 		for(int i = 0; i < marinList.size()-1; i++) {
		 		

			 		
			 		if(baseMapX == marinList.get(i).getLatD() && baseMapY == marinList.get(i).getLonD()) {
		
			 			// map 저장시 자동으로 key값 중복 피함
			 			selectedAnimalMap.put(marinList.get(i).getSciKr(), marinList.get(i).getSciKr());
			 		}
		 			
		 			
		 			
		 		}
		 		
		 		
		 		log.debug("MAP :" + selectedAnimalMap.toString());
		 		log.debug("MAPSIZE: " + selectedAnimalMap.size());
		 	
		 	
		 		if(selectedAnimalMap.size() < 20) {
		 			
		 			pageNo += 1;
		 			this.getAnimals(pageNo, selectedAnimalMap);
		 			
		 		}
		 	
		 	
		 		
		 		
		 		log.debug("20개 이상 MAP :" + selectedAnimalMap.toString());
		 		log.debug("20개 이상 MAPSIZE: " + selectedAnimalMap.size());
		 		

				 
				 return null;
		 	
		 	
				 
		*/		 
				 
		 	/*
		 	for(int i = 0; i < marinList.size()-1; i++) {
		 		

		 		
		 		log.debug("IF 전 SCIKR 와 비교:" + sciKr + ":" + marinList.get(i).getSciKr());
		 		
		 		// if문 세 번째 조건은 이름이 중복된 해양생물 add를 피하기 위함 
		 		if(baseMapX == marinList.get(i).getLatD() && baseMapY == marinList.get(i).getLonD() && !sciKr.equals(marinList.get(i).getSciKr())) {
		
		 			
		 			
		 			MarineAnimalVO vo = new MarineAnimalVO();
		 			
		 			vo.setSciKr(marinList.get(i).getSciKr());
		 			vo.setChrtr(marinList.get(i).getChrtr());
		 			vo.setDistr(marinList.get(i).getDistr());
		 			vo.setLatD(marinList.get(i).getLatD());
		 			vo.setLonD(marinList.get(i).getLonD());
		 			
		 			
		 			sciKr = marinList.get(i).getSciKr();
		 			
		 			
		 			
		 			selectedMarinList.add(vo);
		 			
		 			
		 			
		 			
		 			
		 			
		 			log.debug("IF 안 SCIKR 와 비교:" + sciKr + ":" + marinList.get(i).getSciKr());
		 			
		 		}
		 		
		 		
		 		
		 		
		 		
		 	}
			
		 	
		 	
		 	log.debug("좌표값이 같은 vo들의 리스트: " + selectedMarinList.toString());
		 	log.debug("그 VO의 사이즈: " + selectedMarinList.size());
		 	*/
		 	
		 	// 해양생물의 도분초(DMS)를 위도 경도로 변환
		 	//this.dmsToDegree(latD, latM, latS, lonD, lonM, lonS);
		 	

		
//	}
	
	
	/*

	계산법
	  
	  -dms to degree 
degree = Math.signum(d) * (Math.abs(d) + (m / 60.0) + (s / 3600.0));
	  
	  
	 
	 */
	
	
	
	
	
/*
	public void dmsToDegree(double latD, double latM, double latS, double lonD, double lonM, double lonS) {
		

		

		
		
		
		
		
		double AniMapX = Math.signum(latD) * (Math.abs(latD) +(latM/60.0) + (latS/3600.0));
		double AniMapY = Math.signum(lonD) * (Math.abs(lonD) +(lonM/60.0) + (lonS/3600.0));
		
		
		
		
		
		
		
		log.debug("ANIMAPX 위도: " + AniMapX);
		log.debug("ANIMAPX 경도: " + AniMapY);
		
		
		
		
		
		
		final double R = 6372.8;
		
		double dLat = Math.toRadians(baseMapX-AniMapX);
		double dLon = Math.toRadians(baseMapY-AniMapY);
		
		baseMapX = Math.toRadians(baseMapX);
		AniMapX = Math.toRadians(AniMapX);
		
		double a = Math.pow(Math.sin(dLat/2),2)+Math.pow(Math.sin(dLon/2), 2) * Math.cos(baseMapX) * Math.cos(AniMapX);
		double c = 2 * Math.asin(Math.sqrt(a));
		
		  double distance = R * c;
		 
		 log.debug("DISTANCE: " + distance);
		
		
		
		

	}
*/	
	
	
	
	/*
			위도 경도 두 지점간의 거리 계산

		public class Haversine {
    public static final double R = 6372.8; // In kilometers
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
    public static void main(String[] args) {
        System.out.println(haversine(36.369649, 127.380346, 36.369653,127.382205)); // --> 0.16649327065379554
    }
}



	 */
	
	
	
	
	
	/*
	public void Haversine(double baseMapX, double baseMapY, double AniMapX, double AniMapY) {
		
		
		
		
		
		final double R = 6372.8;
		
		double dLat = Math.toRadians(baseMapX-AniMapX);
		double dLon = Math.toRadians(baseMapY-AniMapY);
		
		baseMapX = Math.toRadians(baseMapX);
		AniMapX = Math.toRadians(AniMapX);
		
		double a = Math.pow(Math.sin(dLat/2),2)+Math.pow(Math.sin(dLon/2), 2) * Math.cos(baseMapX) * Math.cos(AniMapX);
		double c = 2 * Math.asin(Math.sqrt(a));
		
		  double distance = R * c;
		 
		 log.debug("DISTANCE: " + distance);
	}
*/


	public int insertService() {
		// TODO Auto-generated method stub
		
		
		String queryURL = "http://apis.data.go.kr/B551979/marineOrganismInhabitInfoService/getHabitatGisList";
		String serviceKey = "2D5rJ2dlm%2BXKIkVprSSgKI0HU08V%2FYBLqD8l%2Furac2yM3d8LozeI%2BZJmfDX9%2BsAZY7abFzCGzXhRWQL%2BcQdgSA%3D%3D";
		Gson gSon;
		Type listType;
		List<MarineAnimalVO> marinList;
		JsonElement responseJsonElement;
		JsonObject responseJsonObj;
		JsonObject responseGetItem;
		JsonArray responseJsonArr;
		URL url;
		HttpURLConnection connection;
		BufferedReader br;
		String inputLine;
		StringBuffer response;
		//StringBuffer response = null;
		
	
		try {
			
			
			// for문 돌릴 시 두 번째에 JSON 파싱 에러남..... 급하니 그냥 수동으로 페이지 교체하며 저장
			//for(int pageNo = 1; pageNo < 11; pageNo++) {
				
				
				queryURL = queryURL + "?ServiceKey=" + serviceKey + "&pageNo=" + 1 + "&numOfRows=" + 2000 + "&_type=json";
				
				
				url = new URL(queryURL);
				connection = (HttpURLConnection)url.openConnection();
				
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-type", "application/json");
				
				System.out.println("Response code: " + connection.getResponseCode());
				
				int responseCode = connection.getResponseCode();
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				response = new StringBuffer();
				
				while((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				
				br.close();
				
				System.out.println("HTTP 응답 코드: " + responseCode);
				System.out.println("HTTP BODY: " + response.toString());
			
				
				
				
				responseJsonElement = JsonParser.parseString(response.toString());
				 
				responseJsonObj = responseJsonElement.getAsJsonObject();
				 log.debug("과연: "  + responseJsonObj.toString());
				
				 responseGetItem = (JsonObject) responseJsonObj.get("response");
				 responseGetItem = (JsonObject) responseGetItem.get("body");
				 responseGetItem = (JsonObject) responseGetItem.get("items");
				 responseJsonArr = (JsonArray) responseGetItem.getAsJsonArray("item");
				 
				
			
				 	
				 
				 	
				 	
				 	gSon = new Gson();
				 	
				 	listType = new TypeToken<ArrayList<MarineAnimalVO>>(){}.getType();
				 	
				
				 	
				 	marinList = gSon.fromJson(responseJsonArr, listType);
				
				 	 maDao.insertAnimal(marinList);
				 	 
				 	 
			
			//}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 
		
		
	 
		
		return 0;
	}
	
	
	

	
	
	
}
