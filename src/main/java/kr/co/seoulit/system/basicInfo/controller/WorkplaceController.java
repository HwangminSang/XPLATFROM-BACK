package kr.co.seoulit.system.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

import kr.co.seoulit.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;
import kr.co.seoulit.system.basicInfo.to.WorkplaceTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/basicInfo/*")
public class WorkplaceController{
	// serviceFacade 참조변수 선언
	@Autowired
	private BasicInfoServiceFacade basicInfoServiceFacade;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping(value="/searchWorkplace")
	public void searchWorkplaceList(HttpServletRequest request) throws Exception {

		PlatformData reqData = (PlatformData) request.getAttribute("reqData");
		PlatformData resData = (PlatformData) request.getAttribute("resData");
		
		CompanyTO companyCode = datasetBeanMapper.datasetToBean(reqData, CompanyTO.class);
		System.out.println("디버그용companyCode @@@@@@@@ workplaceList"+companyCode.getCompanyCode());
		ArrayList<WorkplaceTO> workplaceList = basicInfoServiceFacade.getWorkplaceList(companyCode.getCompanyCode());
		
		System.out.println("@@@@@@@@ workplaceList: " + workplaceList);
		datasetBeanMapper.beansToDataset(resData, workplaceList, WorkplaceTO.class);
		
	}

	@RequestMapping(value="/batchWorkplaceListProcess", method = RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = basicInfoServiceFacade.batchWorkplaceListProcess(workplaceList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");

		return new ModelAndView("jsonView",map);
	}
}
