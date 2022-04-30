package kr.co.seoulit.system.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import kr.co.seoulit.system.base.mapper.CodeDetailDAO;
import kr.co.seoulit.system.base.to.CodeDetailTO;
import kr.co.seoulit.system.basicInfo.mapper.CompanyDAO;
import kr.co.seoulit.system.basicInfo.repository.CompanyRepository;
import kr.co.seoulit.system.basicInfo.to.CompanyTO;

@Component
public class CompanyApplicationServiceImpl implements CompanyApplicationService {
	// 참조변수 선언
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private CompanyRepository companyRepository;
	
	public ArrayList<CompanyTO> getCompanyList() {
		/* jpa 구현
		ArrayList<CompanyTO> companyList = null;
		companyList = companyDAO.selectCompanyList();
		*/
		List<CompanyTO> companyList = companyRepository.findAll(Sort.by(Sort.Direction.ASC, "companyCode"));
		return new ArrayList<>(companyList);
		
	}

	public String getNewCompanyCode() {
		ArrayList<CompanyTO> companyList = null;
		String newCompanyCode = null;
		companyList = companyDAO.selectCompanyList();

		TreeSet<Integer> companyCodeNoSet = new TreeSet<>();

		for (CompanyTO bean : companyList) {

			if (bean.getCompanyCode().startsWith("COM-")) {

				try {

					Integer no = Integer.parseInt(bean.getCompanyCode().split("COM-")[1]);
					companyCodeNoSet.add(no);

				} catch (NumberFormatException e) {

					// "COM-" 다음 부분을 Integer 로 변환하지 못하는 경우 : 그냥 다음 반복문 실행

				}

			}

		}

		if (companyCodeNoSet.isEmpty()) {
			newCompanyCode = "COM-" + String.format("%02d", 1);
		} else {
			newCompanyCode = "COM-" + String.format("%02d", companyCodeNoSet.pollLast() + 1);
		}
		return newCompanyCode;
	}

	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeBean = new CodeDetailTO();

		
		// 2번의 반복문 실행 : 합쳐서 해도 상관없지만, 추가/수정/삭제가 겹치면 중간에 빠지는 번호가 생길 수 있음
		for (CompanyTO bean : companyList) {  // 1차 반복 : INSERT 만 실행 => 중간에 빠지는 번호가 없도록 하기 위해서..

			String status = bean.getStatus();

			switch (status) {   

			case "INSERT":

				// 새로운 회사번호 생성 후 bean 에 set
				String newCompanyCode = getNewCompanyCode();
				bean.setCompanyCode(newCompanyCode);

				// 회사 테이블에 insert
				companyDAO.insertCompany(bean);
				insertList.add(bean.getCompanyCode());

				// CODE_DETAIL 테이블에 Insert
				detailCodeBean.setDivisionCodeNo("CO-01");
				detailCodeBean.setDetailCode(bean.getCompanyCode());
				detailCodeBean.setDetailCodeName(bean.getCompanyName());

				codeDetailDAO.insertDetailCode(detailCodeBean);

				break;

			}

		}

		for (CompanyTO bean : companyList) {    // 2차 반복 : UPDATE , DELETE 만 실행

			String status = bean.getStatus();

			switch (status) {

			case "UPDATE":

				companyDAO.updateCompany(bean);
				updateList.add(bean.getCompanyCode());

				// CODE_DETAIL 테이블에 Update
				detailCodeBean.setDivisionCodeNo("CO-01");
				detailCodeBean.setDetailCode(bean.getCompanyCode());
				detailCodeBean.setDetailCodeName(bean.getCompanyName());

				codeDetailDAO.updateDetailCode(detailCodeBean);

				break;

			case "DELETE":

				companyDAO.deleteCompany(bean);
				deleteList.add(bean.getCompanyCode());

				// CODE_DETAIL 테이블에 Delete
				detailCodeBean.setDivisionCodeNo("CO-01");
				detailCodeBean.setDetailCode(bean.getCompanyCode());
				detailCodeBean.setDetailCodeName(bean.getCompanyName());

				codeDetailDAO.deleteDetailCode(detailCodeBean);

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;
	}

}
