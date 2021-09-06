package kr.co.seoulit.system.basicInfo.controller;

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

import kr.co.seoulit.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;

@RestController
@RequestMapping("/basicInfo/*")
public class WorkplaceController{
	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade orgSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchWorkplace.do",method=RequestMethod.GET)
	public ModelAndView searchWorkplaceList(HttpServletRequest request) {

		String companyCode = request.getParameter("companyCode");

		ArrayList<WorkplaceTO> workplaceList = null;

		HashMap<String, Object> map = new HashMap<>();
		workplaceList = orgSF.getWorkplaceList(companyCode);

		map.put("gridRowJson", workplaceList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchWorkplaceListProcess.do", method = RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = orgSF.batchWorkplaceListProcess(workplaceList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}
}
