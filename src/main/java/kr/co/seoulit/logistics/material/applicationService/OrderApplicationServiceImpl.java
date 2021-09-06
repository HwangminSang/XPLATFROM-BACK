package kr.co.seoulit.logistics.material.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.material.dao.OrderDAO;
import kr.co.seoulit.logistics.material.to.OrderInfoTO;
import kr.co.seoulit.logistics.material.to.OrderTempTO;
@Component
public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private OrderDAO orderDAO;

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		orderDAO.getOrderList(map);
		@SuppressWarnings("unchecked")
		ArrayList<OrderTempTO> list = (ArrayList<OrderTempTO>) map.get("RESULT");
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson",list);
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG")); 
		return resultMap;
	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(String mrpNoArr) {
		HashMap<String, String> map = new HashMap<>();
		String mrpNoList = mrpNoArr.replace("[", "").replace("]", "").replace("\"", "");
		map.put("mrpNoList", mrpNoList);

		orderDAO.getOrderDialogInfo(map);
		
		HashMap<String,Object> resultMap = new HashMap<>();			
		resultMap.put("gridRowJson", map.get("RESULT"));
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG"));
        
		return resultMap;
	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {
		HashMap<String, String> map = new HashMap<>();
		String mpsNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");
		map.put("mpsNoList", mpsNoList);
		
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

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		ArrayList<OrderInfoTO> orderInfoListOnDelivery = null;
		orderInfoListOnDelivery = orderDAO.getOrderInfoListOnDelivery();
		return orderInfoListOnDelivery;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return orderDAO.getOrderInfoList(map);
	}

	@Override
	public HashMap<String,Object> checkOrderInfo(ArrayList<String> orderNoArr) {
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
