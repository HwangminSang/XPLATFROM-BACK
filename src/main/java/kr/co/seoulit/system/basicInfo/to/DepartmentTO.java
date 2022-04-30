package kr.co.seoulit.system.basicInfo.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentTO extends BaseTO  {
	 private String workplaceName;
	 private String deptName;
	 private String deptCode;
	 private String workplaceCode;
	 private String companyCode;
	 private String deptEndDate;
	 private String deptStartDate;

}