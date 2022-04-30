package kr.co.seoulit.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.sales.mapper.ContractDAO;
import kr.co.seoulit.logistics.sales.mapper.ContractDetailDAO;
import kr.co.seoulit.logistics.sales.mapper.DeliveryDAO;
import kr.co.seoulit.logistics.sales.repository.ContractDetailRepository;
import kr.co.seoulit.logistics.sales.repository.ContractRepository;
import kr.co.seoulit.logistics.sales.repository.DeliveryInfoRepository;
import kr.co.seoulit.logistics.sales.repository.EstimateRepository;
import kr.co.seoulit.logistics.sales.to.DeliveryInfoTO;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@Component
public class DeliveryApplicationServiceImpl implements DeliveryApplicationService {

	
	private final DeliveryDAO deliveryDAO;

	
	private final DeliveryInfoRepository deliveryInfoRepository;
	@Override
	public ArrayList<DeliveryInfoTO> getDeliveryInfoList(HashMap<String, String> map) {
		
		ArrayList<DeliveryInfoTO> deliveryInfoList=null;
		 HashMap<String,String> maplist=new HashMap<>();
		String searchCondition=map.get("searchCondition");
		
		if(searchCondition.equals("searchByDate")){
		 
        
         maplist.put("startDate",map.get("startDate"));
          maplist.put("endDate",map.get("endDate"));
                
     deliveryInfoList=deliveryDAO.selectDeliveryInfoListByDeliverydate(maplist);
	   
	   }else if(searchCondition.equals("searchByDate")){
		 
  deliveryInfoList=deliveryInfoRepository.findAllByCustomerCodeOrderByDeliveryDateDesc(map.get("customerCode"));
		   
	   }else {
		
		// DB 테이블의 컬럼명이 아니니 주의할 것. 엔티티 칼럼명 넣어야한다.
		deliveryInfoList=deliveryInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "deliveryDate"));
		
	   }
		
	   return deliveryInfoList;
	}

	@Override
	public HashMap<String, Object> batchDeliveryListProcess(List<DeliveryInfoTO> deliveryTOList) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (DeliveryInfoTO bean : deliveryTOList) {

			String status = bean.getStatus();

			switch (status.toUpperCase()) {

			case "INSERT":

				// 새로운 일련번호 생성
				String newDeliveryNo = "새로운";

				// Bean 에 새로운 일련번호 세팅
				bean.setDeliveryNo(newDeliveryNo);
				deliveryDAO.insertDeliveryResult(bean);
				insertList.add(newDeliveryNo);

				break;

			case "UPDATE":

				deliveryDAO.updateDeliveryResult(bean);

				updateList.add(bean.getDeliveryNo());

				break;

			case "DELETE":

				deliveryDAO.deleteDeliveryResult(bean);

				deleteList.add(bean.getDeliveryNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	@Override //납품 
	public HashMap<String,Object> deliver(String contractDetailNo) {
		
		//jpa 미구현 - procedure 호출
        HashMap<String, String> map = new HashMap<>();
		map.put("contractDetailNo", contractDetailNo);
		
		deliveryDAO.deliver(map);
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}
	
}
