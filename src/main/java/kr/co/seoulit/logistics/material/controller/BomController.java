package kr.co.seoulit.logistics.material.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import kr.co.seoulit.logistics.material.mapper.StockDAO;
import kr.co.seoulit.logistics.material.repository.StockRepository;
import kr.co.seoulit.logistics.material.serviceFacade.MaterialServiceFacade;
import kr.co.seoulit.logistics.material.to.BomDeployTO;
import kr.co.seoulit.logistics.material.to.BomInfoTO;
import kr.co.seoulit.logistics.material.to.BomTO;
import kr.co.seoulit.system.common.mapper.DatasetBeanMapper;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/material/*")
public class BomController{  
	// serviceFacade 참조변수 선언

	private final MaterialServiceFacade materialServiceFacade;
    private final DatasetBeanMapper datasetBeanMapper;
	

	@RequestMapping(value="/searchBomDeploy")
	public void searchBomDeploy(@RequestAttribute("reqData")PlatformData reqData,@RequestAttribute("resData")PlatformData resData)throws Exception {

	
		// System.out.println(deployCondition);
		// forward 정전개 || reverse 역전개
	
		// System.out.println(itemCode);
		// CodeController를 사용하여 검색한 후 선택하여 텍스트박스에 들어있던 값을 파라미터로 받아옴
		// ex ] DK-01
		
		
		String deployCondition =reqData.getVariableList().getString("deployCondition");
		String itemCode =reqData.getVariableList().getString("itemCode");
		String itemClassificationCondition =reqData.getVariableList().getString("itemClassificationCondition");
		
		
		 System.out.println(deployCondition);
		 System.out.println(itemCode);
		 System.out.println(itemClassificationCondition);
	
		ArrayList<BomDeployTO> bomDeployList = materialServiceFacade.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		datasetBeanMapper.beansToDataset(resData, bomDeployList, BomDeployTO.class);
		
	}
	
	
	
	
//
//	@RequestMapping(value= "/searchBomInfo", method=RequestMethod.GET)
//	public ModelAndView searchBomInfo(HttpServletRequest request) {
//		String parentItemCode = request.getParameter("parentItemCode");
//
//		HashMap<String, Object> map = new HashMap<>();
//		ArrayList<BomInfoTO> bomInfoList = materialServiceFacade.getBomInfoList(parentItemCode);
//
//		map.put("gridRowJson", bomInfoList);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//		return new ModelAndView("jsonView",map);
//
//	}
//
//	@RequestMapping(value ="/searchAllItemWithBomRegisterAvailable" ,method=RequestMethod.GET)
//	public ModelAndView searchAllItemWithBomRegisterAvailable() {
//		HashMap<String, Object> map = new HashMap<>();
//		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = materialServiceFacade.getAllItemWithBomRegisterAvailable();
//
//		map.put("gridRowJson", allItemWithBomRegisterAvailable);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//		return new ModelAndView("jsonView",map);
//
//	}
//
//	@RequestMapping(value= "/batchBomListProcess", method=RequestMethod.POST)
//	public ModelAndView batchBomListProcess(HttpServletRequest request) {
//
//		String batchList = request.getParameter("batchList");
//		// System.out.println(batchList);
//		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
//		}.getType());
//		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
//		HashMap<String, Object> map = new HashMap<>();
//		HashMap<String, Object> resultList = materialServiceFacade.batchBomListProcess(batchBomList);
//
//		map.put("result", resultList);
//		map.put("errorCode", 1);
//		map.put("errorMsg", "성공");
//		return new ModelAndView("jsonView",map);
//
//	}

}
