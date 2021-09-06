package kr.co.seoulit.logistics.material.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.material.to.OrderInfoTO;

@Mapper
public interface OrderDAO {
	
	 public HashMap<String,Object> getOrderList(HashMap<String, Object> map);
	 
	 public HashMap<String,Object> getOrderDialogInfo(HashMap<String, String> map);
	 
	 public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	 
	 public ArrayList<OrderInfoTO> getOrderInfoList(HashMap<String, String> map);

	 public HashMap<String,Object> order(HashMap<String, String> map);	 
	 
	 public HashMap<String,Object> optionOrder(String itemCode, String itemAmount);

	public HashMap<String,Object> updateOrderInfo(HashMap<String, String> params);
}
