package kr.co.seoulit.logistics.material.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;

@Data
@Dataset(name="gds_orderTemp")
public class OrderTempTO {
	
	
	private String mrpGatheringNo;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private int requiredAmount;
	private int stockAmount;
	private String orderDate;
	private String requiredDate;
	private String checked;
	
}