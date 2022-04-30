package kr.co.seoulit.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.sales.mapper.DeliveryDAO;
import kr.co.seoulit.logistics.sales.mapper.EstimateDAO;
import kr.co.seoulit.logistics.sales.mapper.EstimateDetailDAO;
import kr.co.seoulit.logistics.sales.repository.DeliveryInfoRepository;
import kr.co.seoulit.logistics.sales.repository.EstimateDetailRepository;
import kr.co.seoulit.logistics.sales.repository.EstimateRepository;
import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class EstimateApplicationServiceImpl implements EstimateApplicationService {

	// 참조변수 선언
	
	private final EstimateDAO estimateDAO;
	
	private final EstimateDetailDAO estimateDetailDAO;
	private final EstimateDetailRepository estimateDetailRepository;
	private final EstimateRepository estimateRepository;
	
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {
		ArrayList<EstimateTO> estimateTOList = null;
		HashMap<String, String> map = new HashMap<>();
		map.put("dateSearchCondition", dateSearchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		estimateTOList = estimateDAO.selectEstimateList(map);
		
		return estimateTOList;
	}

	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

		ArrayList<EstimateDetailTO> estimateDetailTOList = null;
		estimateDetailTOList = estimateDetailDAO.selectEstimateDetailList(estimateNo);
		return estimateDetailTOList;

	}

	public String getNewEstimateDetailNo(String estimateNo) {
		StringBuffer newEstimateDetailNo = null;
		int i = estimateDetailDAO.selectEstimateDetailSeq(); //1

		newEstimateDetailNo = new StringBuffer();
		newEstimateDetailNo.append("ES");
		newEstimateDetailNo.append(estimateNo.replace("-", ""));
		newEstimateDetailNo.append("-"); 
		newEstimateDetailNo.append(String.format("%02d", i));		   
		return newEstimateDetailNo.toString();
	}
	
	public String getNewEstimateNo(String estimateDate) {

		StringBuffer newEstimateNo = null;
		//int i = estimateDAO.selectEstimateCount(estimateDate); //1   -- JPA 적용 전
		//쿼리 결과 레코드 수를 요청하는 메서드 임을 알림
	
        
		int i = estimateRepository.countByEstimateDate(estimateDate)+1; 	//1 
				
		newEstimateNo = new StringBuffer();
		newEstimateNo.append("ES");
		newEstimateNo.append(estimateDate.replace("-", "")); //ES20200422
		newEstimateNo.append(String.format("%02d", i));	
		return newEstimateNo.toString();
	}

	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateBean) {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		int cnt = 1;

		// 새로운 견적일련번호 생성
		String newEstimateNo = getNewEstimateNo(estimateDate);		
		System.out.println("디버깅확인하기"+newEstimateNo);
		// 뷰단에서 보내온 견적 Bean 에 새로운 견적일련번호 set  view단에서  올때는 null 상태
		newEstimateBean.setEstimateNo(newEstimateNo);
		
		// 견적상세 Bean 을 Insert
		List<EstimateDetailTO> estimateDetailTOList = newEstimateBean.getEstimateDetailTOList();
		
		StringBuffer newEstimateDetailNoInsert = new StringBuffer();
		// 견적상세 List - 견적상세 bean 
		for (EstimateDetailTO bean : estimateDetailTOList) {
			
			// 견적상세일번호 생성
			StringBuffer newEstimateDetailNo = new StringBuffer();
			newEstimateDetailNo.append("ES");
			newEstimateDetailNo.append(newEstimateNo);
			newEstimateDetailNo.append("-"); 
			newEstimateDetailNo.append(String.format("%02d", cnt++));	
			bean.setEstimateDetailNo(newEstimateDetailNo.toString());
			System.out.println("디버깅확인하기"+newEstimateNo);
			// jpa에서 대신 넣어준다.
    		bean.setEstimateNo(newEstimateNo);
		
			// 새로 생성된 견적상세일련번호를 저장
			newEstimateDetailNoInsert.append(newEstimateDetailNo.toString()+",");
			
		
		}
		
	
		
		//jpa 이용 견적TO INSERT
		estimateRepository.save(newEstimateBean);
		
		// 결과 맵에 "INSERT" 키값으로 새로 생성된 견적상세일련번호 리스트 저장
		resultMap.put("INSERT", newEstimateDetailNoInsert);

		// 결과 맵에 "newEstimateNo" 키값으로 새로 생성된 견적일련번호 저장
		resultMap.put("newEstimateNo", newEstimateNo);
		
		return resultMap;
	}

	public HashMap<String, Object> batchEstimateDetailListProcess(List<EstimateDetailTO> estimateDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

		for (EstimateDetailTO bean : estimateDetailTOList) {

			String status = bean.getStatus();
			switch (status) {

			
			case "update":
				estimateDetailRepository.save(bean);
				break;
				//기존의 값을 수정했을 경우
			case "delete":
				//jpa 적용
				estimateDetailRepository.delete(bean);
			   
				break;
			}

		}
		
		return resultMap;

	}

}
