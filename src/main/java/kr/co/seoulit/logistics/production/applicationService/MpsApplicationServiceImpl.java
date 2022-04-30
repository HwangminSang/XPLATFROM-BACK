package kr.co.seoulit.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.outsourcing.applicationService.OutSourcingApplicationService;
import kr.co.seoulit.logistics.production.mapper.MpsDAO;
import kr.co.seoulit.logistics.production.repository.MpsRepository;
import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.SalesPlanInMpsAvailableTO;
import kr.co.seoulit.logistics.sales.mapper.ContractDetailDAO;
import kr.co.seoulit.logistics.sales.mapper.SalesPlanDAO;
import kr.co.seoulit.logistics.sales.repository.ContractDetailRepository;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class MpsApplicationServiceImpl implements MpsApplicationService {
	// DAO 참조변수 선언

	
	private final MpsDAO mpsDAO;

	private final ContractDetailDAO contractDetailDAO;

	private final SalesPlanDAO salesPlanDAO;
	
	private final MpsRepository mpsRepository;
	
	private final ContractDetailRepository contractDetailRepository;
	
	
	
	//mps 조회
	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {
		
		
		
		ArrayList<MpsTO> selectMpsList = null;
		
		
		if(includeMrpApply.equals("excludeMrpApply")) {
			//mrp적용상태가 null인놈 찾기
			selectMpsList = mpsRepository.findByMpsPlanDateBetweenAndMrpApplyStatusIsNull(startDate, endDate);
		}else { 
			//날짜만으로 검색
			selectMpsList = mpsRepository.findByMpsPlanDateBetween(startDate, endDate);
		}
		
		return selectMpsList;
	}

	//mps 주생산계획에서  수주 상세검색시
	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {
		
		// jpa 미구현 - join구문
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return contractDetailDAO.selectContractDetailListInMpsAvailable(map);
	}

	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition, String startDate,
			String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return salesPlanDAO.selectSalesPlanListInMpsAvailable(map);
	}

	public String getNewMpsNo(String mpsPlanDate) {

		StringBuffer newEstimateNo = null;
		
		List<MpsTO> mpsTOlist = mpsRepository.findByMpsPlanDate(mpsPlanDate);
		
		TreeSet<Integer> intSet = new TreeSet<>();
		for (MpsTO bean : mpsTOlist) {
			
			String mpsNo = bean.getMpsNo();
			// MPS 일련번호에서 마지막 2자리만 가져오기
			int no = Integer.parseInt(mpsNo.substring(mpsNo.length() - 2, mpsNo.length()));
			intSet.add(no);
		}
		
		int i=1;
		if (!intSet.isEmpty()) {
			i=intSet.pollLast() + 1;
		}
		
	   newEstimateNo = new StringBuffer();
		newEstimateNo.append("PS");
		newEstimateNo.append(mpsPlanDate.replace("-", ""));
		newEstimateNo.append(String.format("%02d", i)); //PS2020042401
		
		return newEstimateNo.toString();
	}

	
	//mps주생산계획등록시!
	public List<MpsTO> convertContractDetailToMps(
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {

		
		
		List<MpsTO> insertList=new ArrayList<MpsTO>();
		
		
         //getNewMpsNo()메서드를 쓰지 않는 이유는 이렇게하면 커리문이 한번만 나가는데 이전에는 for문안으로 들어가여 여러번 동일한 커리문을 호출하였음!
		ContractDetailInMpsAvailableTO contractDetail=contractDetailInMpsAvailableList.get(0);
		MpsTO mps=mpsDAO.getNewMpsNo(contractDetail.getMpsPlanDate());
		int no=1;
		if(mps!=null){
			String mpsNo=mps.getMpsNo();
			no = Integer.parseInt(mpsNo.substring(mpsNo.length() - 2, mpsNo.length()))+1;
		}
		
		StringBuffer newEstimateNo = new StringBuffer();
		// MPS 에 등록할 수주상세 Bean 의 정보를 새로운 MPS Bean 에 세팅, status : "INSERT"
		for (ContractDetailInMpsAvailableTO bean : contractDetailInMpsAvailableList) {

			MpsTO newMpsBean = new MpsTO();
			//수주상세번호 생성 mps
		
			
			newEstimateNo.append("PS");
			newEstimateNo.append(bean.getMpsPlanDate().replace("-", ""));
			newEstimateNo.append(String.format("%02d", no++)); 
			
		

			newMpsBean.setMpsPlanClassification(bean.getPlanClassification());
			newMpsBean.setContractDetailNo(bean.getContractDetailNo());
			newMpsBean.setItemCode(bean.getItemCode());
			newMpsBean.setItemName(bean.getItemName());
			newMpsBean.setUnitOfMps(bean.getUnitOfContract());
			newMpsBean.setMpsPlanDate(bean.getMpsPlanDate());
			newMpsBean.setMpsPlanAmount(bean.getProductionRequirement());
			newMpsBean.setDueDateOfMps(bean.getDueDateOfContract());
			newMpsBean.setScheduledEndDate(bean.getScheduledEndDate());
			newMpsBean.setDescription(bean.getDescription());
			newMpsBean.setMpsNo(newEstimateNo.toString());//주생산계획번호 저장  pk

		
			insertList.add(newMpsBean);
			
			//jpa 사용
			
			mpsRepository.save(newMpsBean);
			
			//초기화 <이렇게하면 속도가 좋다고함> 
			newEstimateNo.delete(0, newEstimateNo.length());
			
			// MPS TO 의 수주상세번호가 존재하면, 수주상세 테이블에서 해당 번호의 MPS처리상태 (PROCESSING_STATUS)를 'Y' 로 변경
			if (bean.getContractDetailNo() != null) {
					
				//jpa사용
			    Optional<ContractDetailTO> contractDetailNo = 
			    		contractDetailRepository.findByContractDetailNo(bean.getContractDetailNo());
			    
			    	contractDetailNo.ifPresent(contractDetailUpdate ->{
			    		contractDetailUpdate.setProcessingStatus("Y"); //Mps처리상태를 기존 null에서  Y로
			    		contractDetailRepository.save(contractDetailUpdate);       
			        });
			} 
			
			
		}
		
		
		return insertList;

	}

	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList) {
		HashMap<String, Object> resultMap = null;
		ArrayList<MpsTO> mpsTOList = new ArrayList<>();

		MpsTO newMpsBean = null;

		// MPS 에 등록할 판매계획 TO 의 정보를 새로운 MPS TO 에 세팅, status : "INSERT"
		for (SalesPlanInMpsAvailableTO bean : salesPlanInMpsAvailableList) {

			newMpsBean = new MpsTO();

			newMpsBean.setStatus("INSERT");

			newMpsBean.setMpsPlanClassification(bean.getPlanClassification());
			newMpsBean.setSalesPlanNo(bean.getSalesPlanNo());
			newMpsBean.setItemCode(bean.getItemCode());
			newMpsBean.setItemName(bean.getItemName());
			newMpsBean.setUnitOfMps(bean.getUnitOfSales());
			newMpsBean.setMpsPlanDate(bean.getMpsPlanDate());
			newMpsBean.setMpsPlanAmount(bean.getSalesAmount());
			newMpsBean.setDueDateOfMps(bean.getDueDateOfSales());
			newMpsBean.setScheduledEndDate(bean.getScheduledEndDate());
			newMpsBean.setDescription(bean.getDescription());

			mpsTOList.add(newMpsBean);

		}

		resultMap = batchMpsListProcess(mpsTOList);
		return resultMap;
	}

	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {

		HashMap<String, Object> resultMap = null;
		resultMap = new HashMap<>();

		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MpsTO bean : mpsTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// 새로운 MPS번호 생성
				String newMpsNo = getNewMpsNo(bean.getMpsPlanDate());

				// MPS TO 에 새로운 MPS번호 세팅
				bean.setMpsNo(newMpsNo);

				// MPS TO Insert
				mpsDAO.insertMps(bean);

				// 생성된 새로운 MPS 번호를 ArrayList 에 추가
				insertList.add(newMpsNo);

				// MPS TO 의 수주상세번호가 존재하면, 수주상세 테이블에서 해당 번호의 MPS 적용상태를 'Y' 로 변경
				if (bean.getContractDetailNo() != null) {
					changeMpsStatusInContractDetail(bean.getContractDetailNo(), "Y");

					// MPS TO 의 판매계획번호가 존재하면, 판매계획 테이블에서 해당 번호의 MPS 적용상태를 'Y' 로 변경
				} else if (bean.getSalesPlanNo() != null) {
					changeMpsStatusInSalesPlan(bean.getSalesPlanNo(), "Y");
				}

				break;

			case "UPDATE":

				mpsDAO.updateMps(bean);

				updateList.add(bean.getMpsNo());

				break;

			case "DELETE":

				mpsDAO.deleteMps(bean);

				deleteList.add(bean.getMpsNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	public void changeMpsStatusInContractDetail(String contractDetailNo, String mpsStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("contractDetailNo", contractDetailNo);
		map.put("mpsStatus", mpsStatus);
		
		contractDetailDAO.changeMpsStatusOfContractDetail(map);
	}

	public void changeMpsStatusInSalesPlan(String salesPlanNo, String mpsStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("salesPlanNo", salesPlanNo);
		map.put("mpsStatus", mpsStatus);
		
		salesPlanDAO.changeMpsStatusOfSalesPlan(map);
	}

}

