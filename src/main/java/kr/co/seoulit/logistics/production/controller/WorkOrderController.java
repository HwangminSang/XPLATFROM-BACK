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
import kr.co.seoulit.logistics.production.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.production.to.WorkOrderInfoTO;

@RestController
@RequestMapping("/production/*")
public class WorkOrderController{
	// serviceFacade 참조변수 선언
	@Autowired
	private ProductionServiceFacade productionSF;

	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // serializeNulls() 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/getWorkOrderableMrpList.do" , method=RequestMethod.GET)
	public ModelAndView getWorkOrderableMrpList() {
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap = productionSF.getWorkOrderableMrpList();
		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/showWorkOrderDialog.do", method=RequestMethod.GET)
	public ModelAndView showWorkOrderDialog(HttpServletRequest request) {
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
		
		HashMap<String,Object> resultMap = new HashMap<>();
		String mrpNo = request.getParameter("mrpNo");
			
		resultMap = productionSF.getWorkOrderSimulationList(mrpGatheringNo,mrpNo);

		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/workOrder.do", method=RequestMethod.POST)
	public ModelAndView workOrder(HttpServletRequest request) {
		String mrpGatheringNo = request.getParameter("mrpGatheringNo"); // mrpGatheringNo
		String workPlaceCode = request.getParameter("workPlaceCode"); //사업장코드
		String productionProcess = request.getParameter("productionProcessCode"); //생산공정코드:PP002
		System.out.println("사업장코드" + workPlaceCode + " : " + "생산공정코드 : " + productionProcess);
		String mrpNo = request.getParameter("mrpNo");
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap = productionSF.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);

		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/showWorkOrderInfoList.do", method=RequestMethod.GET)
	public ModelAndView showWorkOrderInfoList() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;
		workOrderInfoList = productionSF.getWorkOrderInfoList();

		map.put("gridRowJson", workOrderInfoList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/workOrderCompletion.do", method=RequestMethod.POST)
	public ModelAndView workOrderCompletion(HttpServletRequest request) {

		String workOrderNo=request.getParameter("workOrderNo");
		String actualCompletionAmount=request.getParameter("actualCompletionAmount");
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap = productionSF.workOrderCompletion(workOrderNo,actualCompletionAmount);

		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/getProductionPerformanceInfoList.do", method=RequestMethod.GET)
	public ModelAndView getProductionPerformanceInfoList() {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;
		productionPerformanceInfoList = productionSF.getProductionPerformanceInfoList();

		map.put("gridRowJson", productionPerformanceInfoList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value="/showWorkSiteSituation.do", method=RequestMethod.POST)
	public ModelAndView showWorkSiteSituation(HttpServletRequest request) {


		HashMap<String, Object> resultMap = new HashMap<>();
		String workSiteCourse = request.getParameter("workSiteCourse");//원재료검사:RawMaterials,제품제작:Production,판매제품검사:SiteExamine
		String workOrderNo = request.getParameter("workOrderNo");//작업지시일련번호	
		String itemClassIfication = request.getParameter("itemClassIfication");//품목분류:완제품,반제품,재공품	

		resultMap = productionSF.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);

		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/workCompletion.do", method=RequestMethod.POST)
	public ModelAndView workCompletion(HttpServletRequest request) {

		HashMap<String, Object> resultMap = new HashMap<>();
		String workOrderNo = request.getParameter("workOrderNo"); //작업지시번호
		String itemCode = request.getParameter("itemCode"); //제작품목코드 DK-01 , DK-AP01
		String itemCodeList = request.getParameter("itemCodeList"); //작업품목코드 
		ArrayList<String> itemCodeListArr = gson.fromJson(itemCodeList,
				new TypeToken<ArrayList<String>>() {}.getType());
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		productionSF.workCompletion(workOrderNo,itemCode,itemCodeListArr);

		return new ModelAndView("jsonView",resultMap);
	}

	@RequestMapping(value="/workSiteLog.do", method=RequestMethod.POST)
	public ModelAndView workSiteLogList(HttpServletRequest request) {

		String workSiteLogDate = request.getParameter("workSiteLogDate");
		
		HashMap<String, Object> resultMap = new HashMap<>();

		resultMap=productionSF.workSiteLogList(workSiteLogDate);
		return new ModelAndView("jsonView",resultMap);
	}
	
}
