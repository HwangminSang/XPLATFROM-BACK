package kr.co.seoulit.logistics.production.applicationService;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.Optional;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;


import kr.co.seoulit.logistics.production.mapper.MrpDAO;
import kr.co.seoulit.logistics.production.mapper.MrpGatheringDAO;
import kr.co.seoulit.logistics.production.repository.MpsRepository;
import kr.co.seoulit.logistics.production.repository.MrpGatheringRepository;
import kr.co.seoulit.logistics.production.repository.MrpRepository;
import kr.co.seoulit.logistics.production.to.MpsTO;
import kr.co.seoulit.logistics.production.to.MrpGatheringTO;
import kr.co.seoulit.logistics.production.to.MrpInsertInfoTO;
import kr.co.seoulit.logistics.production.to.MrpTO;
import kr.co.seoulit.logistics.production.to.OpenMrpTO;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class MrpApplicationServiceImpl implements MrpApplicationService {
	// DAO 참조변수 선언

	    
	private final MrpDAO mrpDAO;

	private final  MrpGatheringDAO mrpGatheringDAO;

	private final  MrpRepository mrpRepository;

	private final  MpsRepository mpsRepository;

	private final  MrpGatheringRepository mrpGatheringRepository;


	
	//소요량전개등록후 만들어지는 테이블을 가져옴. 왜? 소요량 취합을 하기위해서  <MRP>테이블 . 
	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

	
		ArrayList<MrpTO> mrpList = null;  
		if(mrpGatheringStatusCondition.equals("null")) //초기값은 NULL
			mrpList = mrpRepository.findByMrpGatheringStatusIsNullOrderByMrpNo();//소요량 취합전 null
		else
			mrpList = mrpRepository.findByMrpGatheringStatusIsNotNullOrderByMrpNo(); //소요량 취합후
		
	
		
		return mrpList; 
	}

	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {
		HashMap<String, String> params = new HashMap<>();
		params.put("dateSearchCondtion", dateSearchCondtion);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return mrpDAO.selectMrpList(params);

	}

	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {
		
		ArrayList<MrpTO> mrpList = null;
		mrpList = mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);
		return mrpList;
	}

	
	
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("searchDateCondition", dateSearchCondtion);
	
		map.put("startDate", startDate.substring(2));
		map.put("endDate", endDate.substring(2));
		
		
	
	
		 ArrayList<MrpGatheringTO> mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(map);

		
		return mrpGatheringList;

	}

	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {
		
		// jpa 미구현 - procedure 호출
		String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");
		HashMap<String,Object> params=new HashMap<>();
		params.put("mpsNoList", mpsNoList); 
		mrpDAO.openMrp(params);
		
			 
		@SuppressWarnings("unchecked")
		ArrayList<OpenMrpTO> openMrpList=(ArrayList<OpenMrpTO>) params.get("RESULT");
		
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson", openMrpList);
		resultMap.put("errorCode",params.get("ERROR_CODE"));
	    resultMap.put("errorMsg", params.get("ERROR_MSG"));
	    
		return resultMap;
	}

	
	//쇼요량전개된것 등록
	public HashMap<String, Object> registerMrp(String mrpRegisterDate, String mpsNoList) {

		// jpa 미구현 - procedure 호출
		HashMap<String,Object> resultMap = new HashMap<>();
		HashMap<String,Object> params=new HashMap<>();
			params.put("mrpRegisterDate",mrpRegisterDate);
			
	    mrpDAO.insertMrpList(params);
	    
	    // params.get("RESULT") = MrpInsertInfoTO(firstMrpNo=RP20211007-019, lastMrpNo=RP20211007-027, length=10)
	    @SuppressWarnings("unchecked")
	    MrpInsertInfoTO mi= ((ArrayList<MrpInsertInfoTO>) params.get("RESULT")).get(0);
	    
	    
		resultMap.put("MrpInsertInfoTO",mi);
	 
	    resultMap.put("errorCode",params.get("ERROR_CODE"));
	    resultMap.put("errorMsg", params.get("ERROR_MSG"));
  	    
	    //jpa 구현
	    // MPS 테이블에서 해당 mpsNo 의 MRP 적용상태를 "Y" 로 변경
	    // 1개만 날라오면 "," 얘가 없더라도 배열로 나눠짐.
	    String[] SplitMpsNo=mpsNoList.split(",");
	    for(String mpsNo : SplitMpsNo) {
         Optional<MpsTO> mpsTO = mpsRepository.findByMpsNo(mpsNo);
			mpsTO.ifPresent(mpsTOUpdate -> {
				mpsTOUpdate.setMrpApplyStatus("Y");
				mpsRepository.save(mpsTOUpdate);
			});
	    }
		return resultMap;
	}
	
	
	

	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MrpTO bean : mrpTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// dao 파트 시작
				mrpDAO.insertMrp(bean);
				// dao 파트 끝

				insertList.add(bean.getMrpNo());

				break;

			case "UPDATE":

				// dao 파트 시작
				mrpDAO.updateMrp(bean);
				// dao 파트 끝

				updateList.add(bean.getMrpNo());

				break;

			case "DELETE":

				// dao 파트 시작
				mrpDAO.deleteMrp(bean);
				// dao 파트 끝

				deleteList.add(bean.getMrpNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	
	
	//소요량 취합 조회
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {
		// jpa 미구현 - 서브쿼리  <원재료는 구매!!! 여기서 처리!> , 소요량 취합등록을 하지 않은 상태 , mrp 번호는 pk
		// 커리문 수정 
		ArrayList<MrpGatheringTO> mrpGatheringList = null;
		String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
		mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);
		return mrpGatheringList;

	}

	
	
	//소요량 취합 등록<제일 어려운부분>
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate, ArrayList<String> mrpNoArr,
			HashMap<String, String> mrpNoAndItemCodeMap){

		// 선택한날짜
		HashMap<String, Object> resultMap = new HashMap<>();
		
		HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();
		
		int seq = 0; 
		int i=1;
		
	
		// 가장 늦은 mrp 번호를 구해옴(받아온 날짜를 통해)
		
		MrpGatheringTO mgt= mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate); 
		// 이거떄문에 해당날짜의 첫번째가 없으면 nullpoint가 생겼음 그래서 npe체크추가.
		if(mgt!=null) {
		   String mrpNumber=mgt.getMrpGatheringNo();
			i=Integer.parseInt(mrpNumber.substring(mrpNumber.length() - 2, mrpNumber.length()))+1;  // 숫자안 - 제거하기위해
		}
			//시퀀스번호 생성
			seq = mrpGatheringDAO.getMGSeqNo(); 
			
	

    	// 새로운 mrpGathering번호를 bean에 입력,mrp_gathering_no IS NULL 인 mrp  
		ArrayList<MrpGatheringTO> mrpGatheringList = getMrpGathering(mrpNoArr);
		// 생성된 mrp 일련번호를 저장할 TreeSet
		TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

		StringBuffer newMrpGatheringNo = new StringBuffer();
		newMrpGatheringNo.append("MG");
		newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
		newMrpGatheringNo.append("-");

		StringBuffer sb = new StringBuffer();
		
		for (MrpGatheringTO bean : mrpGatheringList) { 
			//jpa 적용 ( 새 소요량취합번호 and 시퀀스 번호 저장)
			bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++)); //소요량 취합번호
		  
			bean.setMrpGatheringSeq(seq); 
			
			//MrpGatheringTO(소요량취합TO)에 소요량취합번호<pk> , 시퀀스번호(다같음) 추가해서 등록완료!!!
			mrpGatheringRepository.save(bean);

			
			
			//treeset
			mrpGatheringNoSet.add(bean.getMrpGatheringNo());
			
		
			
			// mrpGathering 빈의 itemCode 와 mrpGatheringNo 를 키값:밸류값으로 map 에 저장
			itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
		
			sb.append(bean.getMrpGatheringNo());
			sb.append(",");
			
			
		
		}
		
	
	    //view단에서 받아옴 map	 mrpNo:itemCode
		for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {   
			String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
			String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode); //새로추가된 mrpGatheringNo 구함 
	
			Optional<MrpTO> mrpTO = mrpRepository.findByMrpNo(mrpNo);
			
			// 여기서 먼저 mrp테이블에 소요량취합번호를 입력해줘야 아래 수주상세 프로시저에서 mrp 테이블을 이용하여 수주상세에 취합번호등록가능!!
			
			mrpTO.ifPresent(mrpTOUpdate -> {
			mrpTOUpdate.setMrpGatheringNo(mrpGatheringNo);
				mrpTOUpdate.setMrpGatheringStatus("Y");
			
				mrpRepository.save(mrpTOUpdate);
				
				
			
		
			
			});	
		}
		
		
		//프로시저 !(jpa미적용) 수주상세(contract_detail)에   새로운 소요량취합 (MrpGatheringNo) 번호를 추가
			
				sb.delete(sb.toString().length() - 1, sb.toString().length());  // 마지막 , 없앰
				HashMap<String, String> map = new HashMap<>();
				map.put("mrpGatheringNo",sb.toString());
				
				mrpGatheringDAO.updateMrpGatheringContract(map);   
				
				
				resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 최초 mrpGathering 일련번호를 결과 Map 에 저장
		        resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 마지막 mrpGathering 일련번호를 결과 Map 에 저장
	
		
		return resultMap;
		
		
		
	}

	public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" 의 맵
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MrpGatheringTO bean : mrpGatheringTOList) {// 소요량 취합결과 그리드에 뿌려진 데이터값

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				mrpGatheringDAO.insertMrpGathering(bean);

				insertList.add(bean.getMrpGatheringNo());
				// 소요량취합번호 추가
				insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
				// map에 key(ItemCode) : value(getMrpGatheringNo)
				break;

			case "UPDATE":

				mrpGatheringDAO.updateMrpGathering(bean);

				updateList.add(bean.getMrpGatheringNo());

				break;

			case "DELETE":

				mrpGatheringDAO.deleteMrpGathering(bean);

				deleteList.add(bean.getMrpGatheringNo());

				break;

			}

		}

		resultMap.put("INSERT_MAP", insertListMap); // key(ItemCode) : value(getMrpGatheringNo)
		resultMap.put("INSERT", insertList); // 소요량취합번호
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

}