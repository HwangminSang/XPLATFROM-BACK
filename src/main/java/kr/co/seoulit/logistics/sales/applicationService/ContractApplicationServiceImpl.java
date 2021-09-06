package kr.co.seoulit.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.sales.dao.ContractDAO;
import kr.co.seoulit.logistics.sales.dao.ContractDetailDAO;
import kr.co.seoulit.logistics.sales.dao.EstimateDAO;
import kr.co.seoulit.logistics.sales.dao.EstimateDetailDAO;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;

@Component
public class ContractApplicationServiceImpl implements ContractApplicationService {

	// 참조변수 선언
	@Autowired
	private ContractDAO contractDAO;
	@Autowired
	private ContractDetailDAO contractDetailDAO;
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired
	private EstimateDetailDAO estimateDetailDAO;
	
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

	public ArrayList<ContractInfoTO> getDeliverableContractList(String searchCondition, String[] paramArray) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
		switch (searchCondition) {

		case "searchByDate": //기간검색

			String startDate = paramArray[0];
			String endDate = paramArray[1];
			HashMap<String, String> map = new HashMap<>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			contractInfoTOList = contractDAO.selectDeliverableContractListByDate(map);

			break;

		case "searchByCustomer": //거래처검색

			String customerCode = paramArray[0];

			contractInfoTOList = contractDAO.selectDeliverableContractListByCustomer(customerCode);

			break;

		}

		for (ContractInfoTO bean : contractInfoTOList) { // 해당 수주의 수주상세 리스트 세팅 

			bean.setContractDetailTOList(contractDetailDAO.selectDeliverableContractDetailList(bean.getContractNo()));

		}
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
		
		estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(map);
		//estimateListInContractAvailable = EstimateListInContractAvailable

		for (EstimateTO bean : estimateListInContractAvailable) {

			bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));

		}
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {
		StringBuffer newContractNo = null;
		int i = contractDAO.selectContractCount(contractDate);
		newContractNo = new StringBuffer();
		newContractNo.append("CO"); //CO
		newContractNo.append(contractDate.replace("-", "")); //CO2020-04-27 -> CO20200427 -> CO2020042701
		newContractNo.append(String.format("%02d", i));	//CO + contractDate + 01 
		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
		ContractTO workingContractBean) {

		HashMap<String, Object> resultMap = new HashMap<>();
		// 새로운 수주일련번호 생성
		String newContractNo = getNewContractNo(contractDate);//CO + contractDate + 01 <= 01은 첫번째라는 뜻 2번째이며 02 로 부여가 됨
		workingContractBean.setContractNo(newContractNo); // 새로운 수주일련번호 세팅
		workingContractBean.setContractDate(contractDate); // 뷰에서 전달한 수주일자 세팅
		workingContractBean.setPersonCodeInCharge(personCodeInCharge); // 뷰에서 전달한 담당자코드 세팅

		contractDAO.insertContract(workingContractBean);
		// 견적 테이블에 수주여부 "Y" 로 수정
		changeContractStatusInEstimate(workingContractBean.getEstimateNo(), "Y");
		HashMap<String, Object> map = new HashMap<>();
		map.put("estimateNo", workingContractBean.getEstimateNo());
		map.put("contractNo", newContractNo);
		map.put("contractType", workingContractBean.getContractType());
		contractDetailDAO.insertContractDetail(map);
		@SuppressWarnings("unchecked")
		ArrayList<ContractDetailTO> list = (ArrayList<ContractDetailTO>) map.get("RESULT");
		ArrayList<String> contractDetailNoArr=new ArrayList<String>();
		for(ContractDetailTO CDT : list) {
			System.out.println(CDT.getContractDetailNo()+"@@@@@@@@@@@@@@@@@");
			contractDetailNoArr.add(CDT.getContractDetailNo());
		}
		resultMap.put("gridRowJson", contractDetailNoArr);
		System.out.println("@@@@@@@@@@@@@@@"+resultMap.get("gridRowJson"));
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
		HashMap<String, String> map = new HashMap<>();
		map.put("estimateNo", estimateNo);
		map.put("contractStatus", contractStatus);
	
		estimateDAO.changeContractStatusOfEstimate(map);
	}

}
