package kr.co.seoulit.hr.affair.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class EmployeeDetailTO extends BaseTO {
	 private String companyCode;
	 private String empCode;
	 private int seq;
	 private String updateHistory;
	 private String updateDate;
	 private String workplaceCode;
	 private String deptCode;
	 private String positionCode;
	 private String userId;
	 private String phoneNumber;
	 private String email;
	 private String zipCode;
	 private String basicAddress;
	 private String detailAddress;
	 private String levelOfEducation;
	 private String image;
}