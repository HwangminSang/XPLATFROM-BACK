package kr.co.seoulit.logistics.material.controller;

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

import kr.co.seoulit.logistics.material.serviceFacade.MaterialServiceFacade;
import kr.co.seoulit.logistics.material.to.BomDeployTO;
import kr.co.seoulit.logistics.material.to.BomInfoTO;
import kr.co.seoulit.logistics.material.to.BomTO;

@RestController
@RequestMapping("/material/*")
public class BomController{
	// serviceFacade 참조변수 선언
	@Autowired
	private MaterialServiceFacade purchaseSF;

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping(value="/searchBomDeploy.do", method=RequestMethod.GET)
	public ModelAndView searchBomDeploy(HttpServletRequest request) {

		String deployCondition = request.getParameter("deployCondition");
		// System.out.println(deployCondition);
		// forward 정전개 || reverse 역전개
		String itemCode = request.getParameter("itemCode");
		// System.out.println(itemCode);
		// CodeController를 사용하여 검색한 후 선택하여 텍스트박스에 들어있던 값을 파라미터로 받아옴
		// ex ] DK-01
		String itemClassificationCondition = request.getParameter("itemClassificationCondition");
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		map.put("gridRowJson", bomDeployList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);
	}

	@RequestMapping(value= "/searchBomInfo.do", method=RequestMethod.GET)
	public ModelAndView searchBomInfo(HttpServletRequest request) {
		String parentItemCode = request.getParameter("parentItemCode");

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);

		map.put("gridRowJson", bomInfoList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);

	}

	@RequestMapping(value ="/searchAllItemWithBomRegisterAvailable.do" ,method=RequestMethod.GET)
	public ModelAndView searchAllItemWithBomRegisterAvailable() {
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

		map.put("gridRowJson", allItemWithBomRegisterAvailable);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);

	}

	@RequestMapping(value= "/batchBomListProcess.do", method=RequestMethod.POST)
	public ModelAndView batchBomListProcess(HttpServletRequest request) {

		String batchList = request.getParameter("batchList");
		// System.out.println(batchList);
		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> resultList = purchaseSF.batchBomListProcess(batchBomList);

		map.put("result", resultList);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공");
		return new ModelAndView("jsonView",map);

	}

}
