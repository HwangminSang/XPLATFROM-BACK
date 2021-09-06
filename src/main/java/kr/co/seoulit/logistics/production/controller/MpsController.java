package kr.co.seoulit.logistics.production.controller;

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

import kr.co.seoulit.logistics.production.serviceFacade.ProductionServiceFacade;
import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;

@RestController
@RequestMapping("/production/*")
public class MpsController{
	// serviceFacade 참조변수 선언
	@Autowired
	private ProductionServiceFacade productionSF;
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchMpsInfo.do", method=RequestMethod.GET)
	public ModelAndView searchMpsInfo(HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply"); 
								// 포함 = includeMrpApply, 미포함 = excludeMrpApply;
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<MpsTO> mpsTOList = productionSF.getMpsList(startDate, endDate, includeMrpApply);

		map.put("gridRowJson", mpsTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/searchContractDetailInMpsAvailable.do", method=RequestMethod.GET)
	public ModelAndView searchContractDetailListInMpsAvailable(HttpServletRequest request) {
		String searchCondition = request.getParameter("searchCondition"); //수주일자 = contractDate, 납기일 = dueDateOfContract
				System.out.println(searchCondition);
		String startDate = request.getParameter("startDate");
				System.out.println(startDate);
		String endDate = request.getParameter("endDate");
				System.out.println(endDate);
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionSF
				.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
												   //contractDate, 2019-07-01, 2019-07-31
		map.put("gridRowJson", contractDetailInMpsAvailableList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/searchSalesPlanListInMpsAvailable.do" , method=RequestMethod.GET)
	public ModelAndView searchSalesPlanListInMpsAvailable(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionSF
				.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

		map.put("gridRowJson", salesPlanInMpsAvailableList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/convertContractDetailToMps.do", method=RequestMethod.POST)
	public ModelAndView convertContractDetailToMps(HttpServletRequest request) {

		String batchList = request.getParameter("batchList"); // 수주상세 데이터 
				System.out.println("batchList = "+batchList);
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {
				}.getType());
				//제너릭클래스를 사용할경우 컴파일시 자동으로 ContractDetailInMpsAvailableTO 라고 한 이름이 Object 로 바뀌어서 역직렬화가 어려워진다 그래서 이렇게 한다고한다
				System.out.println("contractDetailInMpsAvailableList = "+contractDetailInMpsAvailableList);
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = productionSF
				.convertContractDetailToMps(contractDetailInMpsAvailableList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/convertSalesPlanToMps.do", method=RequestMethod.POST)
	public ModelAndView convertSalesPlanToMps(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");

		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
				}.getType());

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultMap = productionSF.convertSalesPlanToMps(salesPlanInMpsAvailableList);

		map.put("result", resultMap);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

}
