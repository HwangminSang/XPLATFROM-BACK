package kr.co.seoulit.hr.affair.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.hr.affair.mapper.EmpSearchingDAO;
import kr.co.seoulit.hr.affair.mapper.EmployeeBasicDAO;
import kr.co.seoulit.hr.affair.mapper.EmployeeDetailDAO;
import kr.co.seoulit.hr.affair.mapper.EmployeeSecretDAO;
import kr.co.seoulit.hr.affair.repository.EmployeeRepository;
import kr.co.seoulit.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.hr.affair.to.EmployeeBasicTO;
import kr.co.seoulit.hr.affair.to.EmployeeDetailTO;
import kr.co.seoulit.hr.affair.to.EmployeeSecretTO;
import kr.co.seoulit.hr.affair.to.EmployeeTO;
import kr.co.seoulit.system.base.mapper.CodeDetailDAO;
import kr.co.seoulit.system.base.to.CodeDetailTO;

@Component
public class EmpApplicationServiceImpl implements EmpApplicationService {

	@Autowired	
	private EmployeeBasicDAO empBasicDAO;
	@Autowired
	private EmployeeDetailDAO empDetailDAO;
	@Autowired
	private EmployeeSecretDAO empSecretDAO;
	@Autowired
	private EmpSearchingDAO empSearchDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private EmployeeRepository employeeRepository;
		
	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) {

		ArrayList<EmpInfoTO> empList = null;
		HashMap<String,String> map=new HashMap<>();

		map.put("searchCondition", searchCondition);

		for (int i = 0; i < paramArray.length; i++) {
			switch (i + "") {
			case "0":
				map.put("companyCode", paramArray[0]);
				break;
			case "1":
				map.put("workplaceCode", paramArray[1]);
				break;
			case "2":
				map.put("deptCode", paramArray[2]);
				break;
			}
		}
			empList = empSearchDAO.selectAllEmpList(map);

			for (EmpInfoTO bean : empList) {
				
				map.clear();
				map.put("companyCode", bean.getCompanyCode());
				map.put("empCode", bean.getEmpCode());
				
				bean.setEmpDetailTOList(
						empDetailDAO.selectEmployeeDetailList(map));

				bean.setEmpSecretTOList(
						empSecretDAO.selectEmployeeSecretList(map));

			}

		return empList;

	}

	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {

		EmpInfoTO bean = new EmpInfoTO();
		
		HashMap<String,String> map=new HashMap<>();

		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		
			ArrayList<EmployeeDetailTO> empDetailTOList = empDetailDAO.selectEmployeeDetailList(map);

			ArrayList<EmployeeSecretTO> empSecretTOList = empSecretDAO.selectEmployeeSecretList(map);

			bean.setEmpDetailTOList(empDetailTOList);
			bean.setEmpSecretTOList(empSecretTOList);

			EmployeeBasicTO basicBean = empBasicDAO.selectEmployeeBasicTO(map);

			if (basicBean != null) {

				bean.setCompanyCode(companyCode);
				bean.setEmpCode(empCode);
				bean.setEmpName(basicBean.getEmpName());
				bean.setEmpEngName(basicBean.getEmpEngName());
				bean.setSocialSecurityNumber(basicBean.getSocialSecurityNumber());
				bean.setHireDate(basicBean.getHireDate());
				bean.setRetirementDate(basicBean.getRetirementDate());
				bean.setUserOrNot(basicBean.getUserOrNot());
				bean.setBirthDate(basicBean.getBirthDate());
				bean.setGender(basicBean.getGender());

			}

		return bean;
	}

	public String getNewEmpCode(String companyCode) {

		ArrayList<EmployeeBasicTO> empBasicList = null;
		String newEmpCode = null;
			empBasicList = empBasicDAO.selectEmployeeBasicList(companyCode);

			TreeSet<Integer> empCodeNoSet = new TreeSet<>();

			for (EmployeeBasicTO TO : empBasicList) {

				if (TO.getEmpCode().startsWith("EMP-")) {

					try {
						Integer no = Integer.parseInt(TO.getEmpCode().split("EMP-")[1]);
						empCodeNoSet.add(no);
					} catch (NumberFormatException e) {
						// "EMP-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾
					}
				}
			}
			if (empCodeNoSet.isEmpty()) {
				newEmpCode = "EMP-" + String.format("%03d", 1);
			} else {
				newEmpCode = "EMP-" + String.format("%03d", empCodeNoSet.pollLast() + 1);
			}
		return newEmpCode;
	}

	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		
			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeTO = new CodeDetailTO();

			for (EmployeeBasicTO TO : empBasicList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					empBasicDAO.insertEmployeeBasic(TO);

					insertList.add(TO.getEmpCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeTO.setDivisionCodeNo("HR-02");
					detailCodeTO.setDetailCode(TO.getEmpCode());
					detailCodeTO.setDetailCodeName(TO.getEmpEngName());

					codeDetailDAO.insertDetailCode(detailCodeTO);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);

		
		return resultMap;

	}

	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {
		HashMap<String, Object> resultMap = new HashMap<>();
			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			for (EmployeeDetailTO bean : empDetailList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					empDetailDAO.insertEmployeeDetail(bean);
					insertList.add(bean.getEmpCode());

					// �궗�썝 怨꾩젙 �젙吏� => EMPLOYEE_BASIC �뀒�씠釉붿쓽 USER_OR_NOT 而щ읆�쓣 "N" �쑝濡� 蹂�寃�
					// �깉濡쒖슫 userPassWord 瑜� null 濡� �엯�젰
					if (bean.getUpdateHistory().equals("怨꾩젙 �젙吏�")) {

						changeEmpAccountUserStatus(bean.getCompanyCode(), bean.getEmpCode(), "N");

						// �궗�썝 怨꾩젙 �젙吏� => EMPLOYEE_SECRET �뀒�씠釉붿뿉 userPassWord 媛� null �씤 �깉濡쒖슫 EmployeeSecretTO
						// �깮�꽦, Insert
						int newSeq = empSecretDAO.selectUserPassWordCount(bean.getCompanyCode(), bean.getEmpCode());

						EmployeeSecretTO newSecretBean = new EmployeeSecretTO();

						newSecretBean.setCompanyCode(bean.getCompanyCode());
						newSecretBean.setEmpCode(bean.getEmpCode());
						newSecretBean.setSeq(newSeq);

						empSecretDAO.insertEmployeeSecret(newSecretBean);

					}

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);

		
		return resultMap;

	}

	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {
		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			// ArrayList<String> updateList = new ArrayList<>();
			// ArrayList<String> deleteList = new ArrayList<>();

			for (EmployeeSecretTO TO : empSecretList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					empSecretDAO.insertEmployeeSecret(TO);

					insertList.add(TO.getEmpCode());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			// resultMap.put("UPDATE", updateList);
			// resultMap.put("DELETE", deleteList);
		return resultMap;
	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {

		ArrayList<EmployeeDetailTO> empDetailList = null;
		Boolean duplicated = false;

			empDetailList = empDetailDAO.selectUserIdList(companyCode);

			for (EmployeeDetailTO TO : empDetailList) {

				if (TO.getUserId().equals(newUserId)) {

					duplicated = true;

				}

			}

		return duplicated; // 以묐났�맂 肄붾뱶�씠硫� true 諛섑솚
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {

		ArrayList<EmployeeBasicTO> empBasicList = null;
		Boolean duplicated = false;
	
			empBasicList = empBasicDAO.selectEmployeeBasicList(companyCode);

			for (EmployeeBasicTO TO : empBasicList) {

				if (TO.getEmpCode().equals(newEmpCode)) {

					duplicated = true;
				}
			}

		return duplicated; // 以묐났�맂 肄붾뱶�씠硫� true 諛섑솚
	}

	@Override
	public void changeEmpAccountUserStatus(String companyCode, String empCode, String userStatus) {
		HashMap<String,String> map=new HashMap<>();
		map.put("companyCode",companyCode);
		map.put("empCode", empCode);
		map.put("userStatus", userStatus);

			empBasicDAO.changeUserAccountStatus(map);
	}

	@Override
	public void updateEmpImg(EmployeeDetailTO employeeDetailTO) {
		
		empDetailDAO.updateEmployeeImg(employeeDetailTO);
		
	}
	
	@Override
	public ArrayList<EmployeeTO> getEmployeeList() {
		
		List<EmployeeTO> employee = employeeRepository.findAll();			

		return new ArrayList<>(employee);
	}
	
	@Override
	public void batchEmpInfo(EmployeeTO employee) {
		
		employeeRepository.save(employee);
		
	} 

}
