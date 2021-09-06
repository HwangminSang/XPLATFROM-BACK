package kr.co.seoulit.logistics.production.to;

import lombok.Data;

@Data
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
