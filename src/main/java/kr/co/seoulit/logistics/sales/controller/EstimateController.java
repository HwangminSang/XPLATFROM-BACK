package kr.co.seoulit.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;

@RestController
@RequestMapping("/sales/*")
public class EstimateController{
	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesSF;
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchEstimate.do", method=RequestMethod.GET)
	public ModelAndView searchEstimateInfo(HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateSearchCondition = request.getParameter("dateSearchCondition");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<EstimateTO> estimateTOList = salesSF.getEstimateList(dateSearchCondition, startDate, endDate);

		map.put("gridRowJson", estimateTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value= "/searchEstimateDetailInfo.do", method=RequestMethod.GET)
	public ModelAndView searchEstimateDetailInfo(HttpServletRequest request) {

		String estimateNo = request.getParameter("estimateNo");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<EstimateDetailTO> estimateDetailTOList = salesSF.getEstimateDetailList(estimateNo);

		map.put("gridRowJson", estimateDetailTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/addNewEstimate.do", method=RequestMethod.POST)
	public ModelAndView addNewEstimate(HttpServletRequest request) {

		String estimateDate = request.getParameter("estimateDate");
		System.out.println("            estimateDate : " + estimateDate); //견적일자
		String newEstimateInfo = request.getParameter("newEstimateInfo"); //견적과 견적상세의 데이터값의 배열
		System.out.println("            newEstimateInfo : " + newEstimateInfo);
		EstimateTO newEstimateTO = gson.fromJson(newEstimateInfo, EstimateTO.class);
		// newEstimateInfo 는 뷰단에서 보낸 json 문자열 => 문자열을 자바 객체로 변환

		HashMap<String, Object> map = new HashMap<>();
		System.out.println(estimateDate+"******"+newEstimateTO);
		HashMap<String, Object> resultList = salesSF.addNewEstimate(estimateDate, newEstimateTO);
		System.out.println(estimateDate+"%%%%%%"+newEstimateTO);
		
		//견적일자와 견적,견적상세의 json객체를 EstimateTO로 변환한 newEstimateTO를 map에 담음
		map.put("result", resultList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchEstimateDetailListProcess.do", method= RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");
		//batchList안의 객체들마다 estimateNo를 지정해주거나
		String estimateNo = request.getParameter("estimateNo");
		//request parameter로 estimateNo를 인자값으로 줘도 됨
		ArrayList<EstimateDetailTO> estimateDetailTOList = gson.fromJson(batchList,
				new TypeToken<ArrayList<EstimateDetailTO>>() {
				}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultList = salesSF.batchEstimateDetailListProcess(estimateDetailTOList,estimateDetailTOList.get(0).getEstimateNo());

		map.put("result", resultList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

}
