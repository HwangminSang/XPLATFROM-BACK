package kr.co.seoulit.system.base.controller;

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

import kr.co.seoulit.system.base.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.system.base.to.CodeDetailTO;
import kr.co.seoulit.system.base.to.CodeTO;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/base/*")
public class CodeController{

	// serviceFacade 참조변수 선언
	@Autowired
	private BaseServiceFacade baseSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchCodeList", method=RequestMethod.GET)
	public ModelAndView findCodeList() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<CodeTO> codeList = baseSF.getCodeList();

		map.put("codeList", codeList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value = "/codeList")
	public void findDetailCodeList(HttpServletRequest request) throws Exception {

		PlatformData reqData = (PlatformData) request.getAttribute("reqData");
		PlatformData resData = (PlatformData) request.getAttribute("resData");
		
		//VariableList:name=divisionCode, type=string, value="CL-01"
		String divisionCode = reqData.getVariable("divisionCode").getString();	 //value값을 얻어옴

		ArrayList<CodeDetailTO> detailCodeList = baseSF.getDetailCodeList(divisionCode); 
		datasetBeanMapper.beansToDataset(resData, detailCodeList, CodeDetailTO.class);
		
	}

	@RequestMapping(value="/checkCodeDeuplication", method = RequestMethod.GET)
	public ModelAndView checkCodeDuplication(HttpServletRequest request) {

		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");

		HashMap<String, Object> map = new HashMap<>();

		Boolean flag = baseSF.checkCodeDuplication(divisionCode, newDetailCode);

		map.put("result", flag);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/batchListProcess", method = RequestMethod.POST)
	public ModelAndView batchListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<CodeTO> codeList = null;
		ArrayList<CodeDetailTO> detailCodeList = null;
		HashMap<String, Object> resultMap = null;

		if (tableName.equals("CODE")) {

			codeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeTO>>() {
			}.getType());
			//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
			resultMap = baseSF.batchCodeListProcess(codeList);

		} else if (tableName.equals("CODE_DETAIL")) {

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());
			//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
			resultMap = baseSF.batchDetailCodeListProcess(detailCodeList);

		}

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}


	@RequestMapping(value="/changeCodeUseCheckProcess", method = RequestMethod.POST)
	public ModelAndView changeCodeUseCheckProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<CodeDetailTO> detailCodeList = null;
		HashMap<String, Object> resultMap = null;

		detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
		}.getType());
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		resultMap = baseSF.changeCodeUseCheckProcess(detailCodeList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}
	
}
