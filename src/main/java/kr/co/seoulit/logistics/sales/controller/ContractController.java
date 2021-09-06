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

import kr.co.seoulit.logistics.sales.serviceFacade.SalesServiceFacade;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;

@RestController
@RequestMapping("/sales/*")
public class ContractController{
	// serviceFacade 참조변수 선언
	@Autowired
	private SalesServiceFacade salesSF;

	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 蹂��솚

	@RequestMapping(value= "/searchContract.do", method=RequestMethod.GET)
	public ModelAndView searchContract(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");

		HashMap<String, Object> map = new HashMap<>();

		ArrayList<ContractInfoTO> contractInfoTOList = null;

		if (searchCondition.equals("searchByDate")) {

			String[] paramArray = { startDate, endDate };
			contractInfoTOList = salesSF.getContractList("searchByDate", paramArray);

		} else if (searchCondition.equals("searchByCustomer")) {

			String[] paramArray = { customerCode };
			contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

		}

		map.put("gridRowJson", contractInfoTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

//	�옉�뾽以�
	@RequestMapping(value="/searchContractNO.do", method=RequestMethod.GET)
	public ModelAndView searchContractNO(HttpServletRequest request) {

		String searchCondition = request.getParameter("searchCondition");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<ContractInfoTO> contractInfoTOList = null;
		if (searchCondition.equals("searchByDate")) {
			String customerCode = "";
			String[] paramArray = { customerCode };
			contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

		}

		map.put("gridRowJson", contractInfoTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "�꽦怨�");
		return new ModelAndView("jsonView",map);
	}

//	�옉�뾽�걹
	@RequestMapping(value="/searchContractDetail.do", method=RequestMethod.GET)
	public ModelAndView searchContractDetail(HttpServletRequest request) {

		String contractNo = request.getParameter("contractNo");

		HashMap<String, Object> map = new HashMap<>();

		ArrayList<ContractDetailTO> contractDetailTOList = salesSF.getContractDetailList(contractNo);

		map.put("gridRowJson", contractDetailTOList);
		map.put("errorCode", 1);
		map.put("errorMsg", "�꽦怨�");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value= "/searchEstimateInContractAvailable.do", method=RequestMethod.GET)
	public ModelAndView searchEstimateInContractAvailable(HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<EstimateTO> estimateListInContractAvailable = salesSF
				.getEstimateListInContractAvailable(startDate, endDate);

		map.put("gridRowJson", estimateListInContractAvailable);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/addNewContract.do", method=RequestMethod.POST)
	public ModelAndView addNewContract(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");  // 수주등록할 견적데이터 json 문자열 
		String contractDate = request.getParameter("contractDate");//오늘날짜
		String personCodeInCharge = request.getParameter("personCodeInCharge");//EMP-01
		System.out.println("@batchList="+batchList+"@contractDate"+contractDate+"@personCodeInCharge"+personCodeInCharge);
		
		HashMap<String, Object> resultMap = new HashMap<>();
		ContractTO workingContractTO = gson.fromJson(batchList, ContractTO.class);
		resultMap = salesSF.addNewContract(contractDate, personCodeInCharge,workingContractTO);

		return new ModelAndView("jsonView",resultMap);
		
	}

	@RequestMapping(value= "/cancleEstimate.do" , method=RequestMethod.POST)
	public ModelAndView cancleEstimate(HttpServletRequest request) {

		String estimateNo = request.getParameter("estimateNo");

		HashMap<String, Object> map = new HashMap<>();
		salesSF.changeContractStatusInEstimate(estimateNo, "N");

		map.put("errorCode", 1);
		map.put("errorMsg", "�꽦怨�");
		map.put("cancledEstimateNo", estimateNo);
		return new ModelAndView("jsonView",map);
	}

}
