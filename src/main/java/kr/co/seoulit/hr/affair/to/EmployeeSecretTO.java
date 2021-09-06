package kr.co.seoulit.hr.affair.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class EmployeeSecretTO extends BaseTO {
	
	 private String companyCode;
	 private String empCode;
	 private int seq;
	 private String userPassword;
}