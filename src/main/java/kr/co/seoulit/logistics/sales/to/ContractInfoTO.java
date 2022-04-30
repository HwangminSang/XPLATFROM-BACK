package kr.co.seoulit.logistics.sales.to;

import java.util.ArrayList;

import javax.persistence.Transient;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import kr.co.seoulit.system.common.annotation.RemoveColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_contractInfo")
public class ContractInfoTO extends BaseTO {

	private String contractNo;
	private String estimateNo;
	private String contractType;
	private String contractTypeName;
	private String customerCode;
	private String customerName;
	private String estimateDate;
	private String contractDate;
	private String contractRequester;
	private String personCodeInCharge;
	private String empNameInCharge;
	private String description;
	@RemoveColumn
	private ArrayList<ContractDetailTO> contractDetailTOList;
	private String deliveryCompletionStatus;

}
