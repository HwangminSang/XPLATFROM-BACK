package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_workSiteLog")
public class WorkSiteLog {
	private String workOrderNo;
	private String itemCode;
	private String itemName;
	private String reaeson;
	private String workSiteName;
	private String workDate;
	private String productionProcessCode;
	private String productionProcessName;
	
}
