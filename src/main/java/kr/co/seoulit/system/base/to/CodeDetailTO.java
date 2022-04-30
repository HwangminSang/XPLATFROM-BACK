package kr.co.seoulit.system.base.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="CODE_DETAIL")
@Dataset(name="gds_codeDetail")
public class CodeDetailTO extends BaseTO {
	
	@Id
	private String detailCode;
	private String divisionCodeNo;
	private String detailCodeName;
	private String codeUseCheck;
	private String description;
	
}