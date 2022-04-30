package kr.co.seoulit.hr.affair.to;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_employee")
public class EmpInfoTO extends BaseTO {
	private String socialSecurityNumber;
	private String empEngName;
	private String deptName;
	private String updateHistory;
	private String updateDate;
	private String zipCode;
	private String positionCode;
	private String userId;
	private String levelOfEducation;
	private String userOrNot;
	private String email;
	private String empName;
	private String image;
	private String hireDate;
	private String retirementDate;
	private String workplaceCode;
	private String companyCode;
	private String birthDate;
	private String gender;
	private String deptCode;
	private String empCode;
	private String positionName;
	private String detailAddress;
	private String workplaceName;
	private String basicAddress;
	private String phoneNumber;
	private int seq;
	private ArrayList<EmployeeDetailTO> empDetailTOList;
	private ArrayList<EmployeeSecretTO> empSecretTOList;
	private String[] authorityGroupList;
	private String[] authorityGroupMenuList;


}