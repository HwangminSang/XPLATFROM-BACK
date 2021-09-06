package kr.co.seoulit.logistics.logisticsInfo.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class ItemTO extends BaseTO {
	 private String itemGroupCode;
	 private String leadTime;
	 private String unitOfStock;
	 private int standardUnitPrice;
	 private String description;
	 private String itemCode;
	 private String itemClassification;
	 private String lossRate;
	 private String itemName;
}