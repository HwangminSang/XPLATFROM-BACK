package kr.co.seoulit.system.base.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.system.base.to.ApiAirTo;
import kr.co.seoulit.system.base.to.ApiTO;
import kr.co.seoulit.system.base.to.ApiTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/base/*")
public class ApiExplorer {
	
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	
	
	private static Gson gson = new GsonBuilder().serializeNulls().create();


	
    // tag값의 정보를 가져오는 메소드

	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}


	@GetMapping(value = "/openapi")
	public void openapi1(@RequestAttribute("reqData") PlatformData reqData,
						 @RequestAttribute("resData") PlatformData resData) throws Exception {
		StringBuilder urlBuilder
				= new StringBuilder("http://apis.data.go.kr/B551177/CgoFltSched/getCgoFltSchedDepartures"); /*URL*/
		urlBuilder
				.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=%2BGyQgpIBRtS%2BqRZIfQoOEq1rgttG3kgs8%2B6hCjZUqHSJwrooOE5Cu%2BKUVtLy7QCPQ2Ah9ZJn3t2dPba7t%2BSJJQ%3D%3D"); /*Service Key*/
		urlBuilder
				.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*데이터 행*/
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*데이터 표출 페이지 수*/
		urlBuilder
				.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode("K", "UTF-8")); /*언어 구분값 K: 국문, E: 영문, C: 중문, J: 일문*/
		urlBuilder
				.append("&" + URLEncoder.encode("airport", "UTF-8") + "=" + URLEncoder.encode("FRA", "UTF-8")); /*출발지공항(IATA) 코드*/
		urlBuilder
				.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*응답유형 [xml, json] default=xml*/
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(urlBuilder.toString());
		// root tag
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("item");
		ArrayList<ApiAirTo> apiAirToList = new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			ApiAirTo apiAirTo = new ApiAirTo();
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				apiAirTo.setAirLine(getTagValue("airline", eElement));
				apiAirTo.setAirPort(getTagValue("airport", eElement));
				apiAirTo.setFirstDate(getTagValue("firstdate", eElement));
				apiAirTo.setFlightId(getTagValue("flightid", eElement));
				apiAirTo.setStartTime(getTagValue("st", eElement));
				apiAirTo.setLastDate(getTagValue("lastdate", eElement));
				apiAirTo.setMonday(getTagValue("monday", eElement));
				apiAirTo.setTuesday(getTagValue("tuesday", eElement));
				apiAirTo.setWednesday(getTagValue("wednesday", eElement));
				apiAirTo.setThursday(getTagValue("thursday", eElement));
				apiAirTo.setFriday(getTagValue("friday", eElement));
				apiAirTo.setSaturday(getTagValue("saturday", eElement));
				apiAirTo.setSunday(getTagValue("sunday", eElement));
				apiAirToList.add(apiAirTo);
			}}
		datasetBeanMapper.beansToDataset(resData, apiAirToList, ApiAirTo.class);

	}
	

	@RequestMapping(value="/openapiTest")
	public void openapi(@RequestAttribute("reqData")PlatformData reqData,
			                 @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		String today=reqData.getVariableList().getString("today");
		
		 StringBuilder urlBuilder = new StringBuilder("https://koreaexim.go.kr/site/program/financial/exchangeJSON"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("authkey","UTF-8") + "=PhZE5ZUhFphS9xVAZdEdthgn2nVRJUur"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("searchdate","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*페이지번호 기본값=1*/
	        urlBuilder.append("&" + URLEncoder.encode("data","UTF-8") + "=" + URLEncoder.encode("AP01", "UTF-8")); /*한 페이지 결과 수 기본값=10 최대값=500*/
	       
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();


	      
	        ArrayList<ApiTO> apiList=gson.fromJson(sb.toString(),new TypeToken<ArrayList<ApiTO>>(){}.getType());
	        System.out.println(apiList.toString());
	        
	        datasetBeanMapper.beansToDataset(resData, apiList, ApiTO.class);
	    
			
			
	}
	


}
