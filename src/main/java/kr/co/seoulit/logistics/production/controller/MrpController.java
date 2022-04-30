package kr.co.seoulit.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;

import kr.co.seoulit.logistics.production.serviceFacade.ProductionServiceFacade;
import kr.co.seoulit.logistics.production.to.MrpGatheringTO;
import kr.co.seoulit.logistics.production.to.MrpInsertInfoTO;
import kr.co.seoulit.logistics.production.to.MrpTO;
import kr.co.seoulit.logistics.production.to.OpenMrpTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/production/*")
public class MrpController{
	// serviceFacade 참조변수 선언

	private final ProductionServiceFacade poductionServiceFacade;

	private final DatasetBeanMapper datasetBeanMapper;
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	//소요량전개취합 화면 바뀌자 바로 받아옴
	@RequestMapping(value="/getMrpList")
	public void getMrpList(@RequestAttribute("reqData")PlatformData reqData,
			               @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		
		String mrpGatheringStatusCondition = reqData.getVariable("mrpGatheringStatusCondition").getString();  //처음에는 null
		String dateSearchCondition = reqData.getVariable("dateSearchCondition").getString(); // ''
		String mrpStartDate = reqData.getVariable("mrpStartDate").getString();//''
		String mrpEndDate = reqData.getVariable("mrpEndDate").getString();//''
		String mrpGatheringNo = reqData.getVariable("mrpGatheringNo").getString(); //'' 소요량 취합번호
		
		ArrayList<MrpTO> mrpList = null;
		
		if(mrpGatheringStatusCondition.equals("null")) {
			//여기 null이라는 스트링값이 담겨저왔으니 null은 아님. 객체가있는상태.		
			mrpList = poductionServiceFacade.searchMrpList(mrpGatheringStatusCondition);
			
		} else if (!dateSearchCondition.equals("null")) {		
			mrpList = poductionServiceFacade.searchMrpList(dateSearchCondition, mrpStartDate, mrpEndDate);
			
		} else if (!mrpGatheringNo.equals("null")) {	
			mrpList = poductionServiceFacade.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
	
		}
		
		
		datasetBeanMapper.beansToDataset(resData, mrpList, MrpTO.class);
		
	}
	

	// MPS 모의전개
	@RequestMapping(value="/openMrp")
	public void openMrp(@RequestAttribute("reqData")PlatformData reqData,
			            @RequestAttribute("resData")PlatformData resData) throws Exception {
		
	
		
		String mpsNoList = reqData.getVariableList().getString("mpsNoList");  //변수의 이름을 적용 하면 VALUE값을 얻어온다
		
		//MPS번호가 여러개 있을경우 배열에 넣는다.
		ArrayList<String> mpsNoArr = new ArrayList<>();
		mpsNoArr.add(mpsNoList);
	
		
		HashMap<String, Object> resultMap = poductionServiceFacade.openMrp(mpsNoArr);
		
		
		
		@SuppressWarnings("unchecked")
		
		ArrayList<OpenMrpTO> openMrpList = (ArrayList<OpenMrpTO>)resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(resData, openMrpList, OpenMrpTO.class);
		
	}

   //소요량전개 등록
	@RequestMapping(value="/registerMrp")
	public void registerMrp(@RequestAttribute("reqData")PlatformData reqData,
                         @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		// JPA 일부 구현 - Procedure 호출
		
		
		String mrpRegisterDate = reqData.getVariable("mrpRegisterDate").getString();
		//주생산계획번호
		String mpsNo = reqData.getVariable("mpsNoCollection").getString();
		
	
					
		HashMap<String, Object> resultMap = poductionServiceFacade.registerMrp(mrpRegisterDate, mpsNo);	
		
		
		//결과 출력해서 보내주기 . mrp번호
		 MrpInsertInfoTO mi=(MrpInsertInfoTO)resultMap.get("MrpInsertInfoTO");
		datasetBeanMapper.beanToDataset(resData,mi,MrpInsertInfoTO.class);
	
	}
	
	
	//소요량 취합 
	@RequestMapping(value="/getMrpGatheringList")  
	public void getMrpGatheringList(@RequestAttribute("reqData")PlatformData reqData,
			                        @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		// JPA 미구현 - 서브쿼리 호출
	

		String mrpNoList = reqData.getVariableList().getString("mrpNoList"); //VariableList:name=mrpNoList, 
		System.out.println("mrp번호:"+mrpNoList);
		ArrayList<String> mrpNoArr = new ArrayList<>();
		mrpNoArr.add(mrpNoList);
		
		ArrayList<MrpGatheringTO> mrpGatheringList = poductionServiceFacade.getMrpGathering(mrpNoArr);
		datasetBeanMapper.beansToDataset(resData, mrpGatheringList, MrpGatheringTO.class);
	}
	
	//소요량 취합 등록
	@RequestMapping(value="/registerMrpGathering")
	public void registerMrpGathering(@RequestAttribute("reqData")PlatformData reqData,
			                           @RequestAttribute("resData")PlatformData resData) throws Exception {
		
		
		String mrpGatheringRegisterDate = reqData.getVariable("mrpGatheringRegisterDate").getString();
		String mrpNoList = reqData.getVariableList().getString("mrpNoList");
		String mrpNoAndItemCodeList = reqData.getVariableList().getString("mrpNoAndItemCodeList");
		
		ArrayList<String> mrpNoArr = new ArrayList<>();
		mrpNoArr.add(mrpNoList);
		
		// Xplatform의 mrpNoAndItemCodeList를 hashMap으로 저장 방법 요구
		HashMap<String, String> mrpNoAndItemCodeMap =  gson.fromJson(mrpNoAndItemCodeList, //mprNO : ItemCode 
              new TypeToken<HashMap<String, String>>() { }.getType());
		
		//새로 생성된 소요량취합번호 보내기
		HashMap<String, Object> map=poductionServiceFacade.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr, mrpNoAndItemCodeMap);	 
		for(String key: map.keySet()) {
		resData.getVariableList().add(key,map.get(key));
		}
		
	}
	

	@RequestMapping(value="/searchMrpGathering")
	public void searchMrpGathering(@RequestAttribute("reqData")PlatformData reqData,
                                          @RequestAttribute("resData")PlatformData resData)throws Exception{

		String searchDateCondition = reqData.getVariableList().getString("searchDateCondition");
		 
		 
		String startDate = reqData.getVariableList().getString("mrpGatheringStartDate");
		String endDate = reqData.getVariableList().getString("mrpGatheringEndDate");
		
		
		ArrayList<MrpGatheringTO> mrpGatheringList = poductionServiceFacade.searchMrpGatheringList(searchDateCondition, startDate, endDate);
		
		datasetBeanMapper.beansToDataset(resData, mrpGatheringList, MrpGatheringTO.class);
	


	}
	
	
}
