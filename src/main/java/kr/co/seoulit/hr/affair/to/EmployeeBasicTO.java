package kr.co.seoulit.hr.affair.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class EmployeeBasicTO extends BaseTO {
	 private String companyCode;
	 private String empCode;
	 private String empName;
	 private String empEngName;
	 private String socialSecurityNumber;
	 private String hireDate;
	 private String retirementDate;
	 private String userOrNot;
	 private String birthDate;
	 private String gender;
}