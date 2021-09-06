package kr.co.seoulit.logistics.logisticsInfo.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class ItemInfoTO extends BaseTO {

	private String itemCode; 
	private String itemName;
	private String itemGroupCode;
	private String itemClassification;
	private String unitOfStock;
	private String lossRate;
	private String leadTime;
	private int standardUnitPrice;
	private String codeUseCheck;
	private String description;
}
