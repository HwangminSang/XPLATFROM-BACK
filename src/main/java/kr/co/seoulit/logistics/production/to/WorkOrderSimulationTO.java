package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_workOrderSimulation")
public class WorkOrderSimulationTO {

	private String mrpNo; 
	private String mpsNo;
	private String mrpGatheringNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private String inputAmount;
	private String requiredAmount;
	private String stockAfterWork;
	private String orderDate;
	private String requiredDate;

}
