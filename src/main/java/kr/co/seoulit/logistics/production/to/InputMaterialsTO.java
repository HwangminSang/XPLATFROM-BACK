package kr.co.seoulit.logistics.production.to;

import kr.co.seoulit.system.base.to.BaseTO;
import lombok.Data;

@Data
public class InputMaterialsTO extends BaseTO {
	 private String inputItemNo;
	 private String productionResultNo;
	 private String description;
	 private String itemCode;
	 private String unitOfInputMaterials;
	 private String warehouseCode;
	 private String itemName;
	 private int inputAmount;
	 private String inputDate;

}