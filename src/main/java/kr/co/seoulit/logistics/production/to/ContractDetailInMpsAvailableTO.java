package kr.co.seoulit.logistics.production.to;

import javax.persistence.Id;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_contractDetailInMpsAvailable")
public class ContractDetailInMpsAvailableTO extends BaseTO {
	@Id
	private String contractDetailNo;
	private String contractNo;
	private String contractType;
	private String contractDate;
	private String customerCode;
	private String itemCode;
	private String itemName;
	private String unitOfContract;
	private String estimateAmount;
	private String stockAmountUse;
	private String productionRequirement;
	private String dueDateOfContract;
	private String description;
	private String planClassification;
	private String mpsPlanDate;
	private String scheduledEndDate;
	private String checked;
	
}