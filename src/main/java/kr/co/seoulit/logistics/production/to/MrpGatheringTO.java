package kr.co.seoulit.logistics.production.to;

import java.util.ArrayList;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class MrpGatheringTO extends BaseTO {

	private String mrpGatheringNo;
	private String orderOrProductionStatus;
	private String itemCode;
	private String itemName;
	private String unitOfMrpGathering;
	private String claimDate;
	private String dueDate;
	private int necessaryAmount;
	private ArrayList<MrpTO> mrpTOList;
	private int mrpGatheringSeq;
}