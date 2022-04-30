package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_productionPerformance")
public class ProductionPerformanceInfoTO {

	private String workOrderCompletionDate;
	private String workOrderNo;
	private String mpsNo;
	private String contractDetailNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unit;
	private String workOrderAmount;
	private String actualCompletionAmount;
	private String workSuccessRate;

}
