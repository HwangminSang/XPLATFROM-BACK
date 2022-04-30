package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.material.mapper.BomDAO;
import kr.co.seoulit.logistics.material.mapper.OrderDAO;
import kr.co.seoulit.logistics.material.repository.OrderRepository;
import kr.co.seoulit.logistics.material.to.OrderInfoTO;
import kr.co.seoulit.logistics.material.to.OrderTempTO;
import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언

	private final OrderDAO orderDAO;

	private final OrderRepository orderRepository;

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		
		//jpa 미구현 - procedure
		HashMap<String, Object> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		orderDAO.getOrderList(map);
		
		@SuppressWarnings("unchecked")
		ArrayList<OrderTempTO> list = (ArrayList<OrderTempTO>) map.get("RESULT");
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson",list);
       
        
		return resultMap;
	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpGatheringNoListStr) {
		//:name=mrpGatheringNoList, type=string, value="[MG20220104-032]"
		//jpa 미구현 - procedure
		HashMap<String, String> map = new HashMap<>();
		String mrpGatheringNoList = mrpGatheringNoListStr.replace("[", "").replace("]", "").replace("\"", "");
		map.put("mrpGatheringNoList", mrpGatheringNoList);

		orderDAO.getOrderDialogInfo(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();			
		resultMap.put("orderDialogInfoList", map.get("RESULT"));
       
        
		return resultMap;
	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {
		
		//jpa 미구현 - procedure		
		HashMap<String, String> map = new HashMap<>();
		String mrpGaNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");
		map.put("mrpGaNoList", mrpGaNoList);
		
		orderDAO.order(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();
    	resultMap.put("errorCode",map.get("ERROR_CODE"));
    	resultMap.put("errorMsg", map.get("ERROR_MSG")); 

    	return resultMap;
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {
		HashMap<String, String> map = new HashMap<>();
		map.put("itemCode", itemCode);
		map.put("itemAmount", itemAmount);	
		orderDAO.optionOrder(itemCode, itemAmount);
		
		HashMap<String,Object> resultMap = new HashMap<>();
	    resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
    	return resultMap;
	}

	@Override //배송중인 발주품목가져오기 
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
	
		List<String> orderInfoStatusList = new ArrayList<>();
		orderInfoStatusList.add("운송중");
		orderInfoStatusList.add("검사완료");
		//… where OrderInfoStatus in ( ?1 , ?2)  //운송중  or 검사완료   //처음에는  '운송중' ,null
	     //발주상태 : 운송중 --> 입고완료
		List<OrderInfoTO> orderInfoTOList = orderRepository.findByOrderInfoStatusIn(orderInfoStatusList);
		
		return new ArrayList<>(orderInfoTOList);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		
		 
		 //jpa구현 발주현황조회
		List<OrderInfoTO> orderInfoList = orderRepository.findByOrderDateBetween(startDate, endDate);
		System.out.println("@@@@@@@@@@@@2 orderInfoList " + orderInfoList);
		return new ArrayList<>(orderInfoList);		
	}

	@Override//발주품목 제품검사
	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr) {
		
		// jpa 미구현 - procedure 호출
		HashMap<String,String> params = new HashMap<>();
		String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");
		params.put("orderNoList", orderNoList);
		orderDAO.updateOrderInfo(params);
		
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("errorCode",params.get("ERROR_CODE"));
    	resultMap.put("errorMsg", params.get("ERROR_MSG"));
    	
		return resultMap;
	}
}
