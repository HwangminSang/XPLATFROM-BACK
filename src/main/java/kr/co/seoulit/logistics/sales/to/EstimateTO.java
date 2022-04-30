package kr.co.seoulit.logistics.sales.to;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import kr.co.seoulit.system.base.to.BaseTO;
import kr.co.seoulit.system.common.annotation.Dataset;
import kr.co.seoulit.system.common.annotation.RemoveColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="ESTIMATE")
@Dataset(name = "gds_estimate")
public class EstimateTO extends BaseTO implements Persistable<String> {
	
	@Id
	private String estimateNo;
	private String estimateRequester;
	private String description;
	private String contractStatus;
	private String customerCode;
	private String personCodeInCharge;
	private String estimateDate;
	private String effectiveDate;
	
	@Transient
	private String status;
	
	@Transient
	private String personNameCharge;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="estimateNo")
	@RemoveColumn
	private List<EstimateDetailTO> estimateDetailTOList;

	
	 @Transient
	 @RemoveColumn
	 @CreatedDate
	  private LocalDateTime createdDate;
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return createdDate==null;
	}

}