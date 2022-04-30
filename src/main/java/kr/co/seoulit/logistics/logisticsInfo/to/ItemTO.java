package kr.co.seoulit.logistics.logisticsInfo.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="ITEM")
@Dataset(name="gds_item")
public class ItemTO extends BaseTO {
	 @Id
	 private String itemCode;
	 private String itemGroupCode;
	 private String leadTime;
	 private String unitOfStock;
	 private int standardUnitPrice;
	 private String description;
	 private String itemClassification;
	 private String lossRate;
	 private String itemName;
}