package kr.co.seoulit.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Optional;




import org.springframework.stereotype.Component;


import kr.co.seoulit.logistics.sales.mapper.ContractDAO;
import kr.co.seoulit.logistics.sales.mapper.ContractDetailDAO;

import kr.co.seoulit.logistics.sales.repository.ContractDetailRepository;
import kr.co.seoulit.logistics.sales.repository.ContractRepository;
import kr.co.seoulit.logistics.sales.repository.EstimateRepository;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class ContractApplicationServiceImpl implements ContractApplicationService {

	// 참조변수 선언

	private final ContractDAO contractDAO;

	private final ContractDetailDAO contractDetailDAO;
	
	
	private final EstimateRepository estimateRepository; 
	
	private final ContractRepository contractRepository;
	

	private final ContractDetailRepository contractDetailRepository;
	

	
	
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
		switch (searchCondition) {

		case "searchByDate":
			String startDate = paramArray[0];
			String endDate = paramArray[1];
			HashMap<String, String> map = new HashMap<>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			contractInfoTOList = contractDAO.selectContractListByDate(map);
			break;

		case "searchByCustomer":
			String customerCode = paramArray[0];
			contractInfoTOList = contractDAO.selectContractListByCustomer(customerCode);
			break;
		}

		for (ContractInfoTO bean : contractInfoTOList) {
			bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));
		}
		return contractInfoTOList;

	}
	
	public ArrayList<ContractInfoTO> getContractListByCondition(HashMap<String, String> map){
		ArrayList<ContractInfoTO> contractInfoTOList =
				contractDAO.getContractListByCondition(map);
	
		
		return contractInfoTOList;
	}
	

	public ArrayList<ContractInfoTO> getDeliverableContractList(HashMap<String, String> map) {

		// jpa 미구현 - join구문 //수정 <민상> 마이바티스 when절  문법오류 where절 안됨.
		//.DELIVERY_COMPLETION_STATUS IS NULL
		ArrayList<ContractInfoTO> contractInfoTOList = contractDAO.selectDeliverableContractListByCondition(map);
		
		/*
		 * for (ContractInfoTO bean : contractInfoTOList) { // 해당 수주의 수주상세 리스트 세팅 String
		 * constractNo = bean.getContractNo();
		 * bean.setContractDetailTOList(contractDetailDAO.
		 * selectDeliverableContractDetailList(constractNo)); }
		 */
		
		return contractInfoTOList;
	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {
		ArrayList<ContractDetailTO> contractDetailTOList = null;
		contractDetailTOList = contractDetailDAO.selectContractDetailList(contractNo);
		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		ArrayList<EstimateTO> estimateListInContractAvailable = null;
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		// 마이바티스 사용 join 문
		estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(map);
   
		

		return estimateListInContractAvailable;
	}

	@Override//수주번호작성
	public String getNewContractNo(String contractDate) {
		StringBuffer newContractNo  = new StringBuffer();
		
		int i = contractDAO.selectContractCount();

		newContractNo.append("CO"); 
		newContractNo.append(contractDate.replace("-", ""));
		newContractNo.append(String.format("%02d", i));	
		
		return newContractNo.toString();
	}

	@Override//수주등록
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
		ContractTO workingContractBean) {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		// 새로운 수주일련번호 생성
		String newContractNo = getNewContractNo(contractDate); //CO + contractDate + 01 <= 01은 첫번째라는 뜻 2번째이며 02 로 부여가 됨
		workingContractBean.setContractNo(newContractNo); // 새로운 수주일련번호 세팅
		workingContractBean.setContractDate(contractDate); // 뷰에서 전달한 수주일자 세팅
		
		
		// jpa - ContractTO만 적용 (contractDetailTO - procedure 호출로 저장)
		
		contractRepository.save(workingContractBean);

		
		// 견적 테이블에 수주여부 "Y" 로 수정
		Optional<EstimateTO> estimateTo = estimateRepository.findByEstimateNo(workingContractBean.getEstimateNo());	
			estimateTo.ifPresent(estimateToUpdate ->{
				estimateToUpdate.setContractStatus("Y");
				estimateRepository.save(estimateToUpdate);
			
				
			});
		
			
		// ContractDetail 저장
		// jpa 미구현 - procedure 호출
		HashMap<String, Object> map = new HashMap<>();
		map.put("estimateNo", workingContractBean.getEstimateNo());//--견적상세테이블 조회시 사용
		map.put("contractNo", newContractNo); // 수주상세번호 만들때 사용 
		map.put("contractType", workingContractBean.getContractType()); //STOCK_AMOUNT 구하기위해 
				
		contractDetailDAO.insertContractDetail(map);
		

		
		
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (ContractDetailTO bean : contractDetailTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				/*contractDetailDAO.insertContractDetail(bean);*/
				insertList.add(bean.getContractDetailNo());

				break;

			case "UPDATE":

				/*contractDetailDAO.updateContractDetail(bean);*/
				updateList.add(bean.getContractDetailNo());

				break;

			case "DELETE":

				contractDetailDAO.deleteContractDetail(bean);
				deleteList.add(bean.getContractDetailNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {

				
		// 견적 테이블에 수주여부 "N" 로 수정
		Optional<EstimateTO> estimateTo = estimateRepository.findByEstimateNo(estimateNo);
			estimateTo.ifPresent(estimateToUpdate ->{
				estimateToUpdate.setContractStatus(contractStatus);
				estimateRepository.save(estimateToUpdate);
			});
	}

}
