package kr.co.seoulit.logistics.production.to;

import javax.persistence.Transient;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_workOrderableMrpList")
public class WorkOrderableMrpListTO {
	
	private String mrpNo;
	@Transient
	private String mpsNo;	  // ?? 테이블에는 존재 X 
	private String mrpGatheringNo;	
	private String itemClassification;	
	private String itemCode;
	private String itemName;	
	private String unitOfMrp;	
	private int requiredAmount;	
	private String orderDate;
	private String requiredDate;
	@Transient
	private String checked;

}
