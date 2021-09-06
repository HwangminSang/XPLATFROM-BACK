package kr.co.seoulit.logistics.sales.to;

import java.util.ArrayList;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class ContractTO extends BaseTO {
	private String contractType;
	private String estimateNo;
	private String contractDate;
	private String description;
	private String contractRequester;
	private String customerCode;
	private String personCodeInCharge;
	private String contractNo;
	private ArrayList<ContractDetailTO> contractDetailTOList;
	 
}