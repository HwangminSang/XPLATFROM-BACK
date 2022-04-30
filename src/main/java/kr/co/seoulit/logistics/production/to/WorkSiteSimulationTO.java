package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_workSiteSimulation")
public class WorkSiteSimulationTO {
	
	private String workOrderNo;
	private String mrpNo;
	private String mpsNo;
	private String workSiteName;
	private String wdItem;
	private String parentItemCode;
	private String parentItemName;
	private String itemClassIfication;
	private String itemCode;
	private String itemName;
	private String requiredAmount;

}