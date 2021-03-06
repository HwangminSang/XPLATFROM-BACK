package kr.co.seoulit.system.basicInfo.to;

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
@Table(name="COMPANY")
@Dataset(name="gds_company")
public class CompanyTO extends BaseTO {
   	 private String companyTelNumber;
	 private String companyDivision;
	 private String companyBasicAddress;
	 private String companyOpenDate;
	 private String companyBusinessItems;
	 private String businessLicenseNumber;
	 private String companyName;
	 private String companyDetailAddress;
	 private String companyFaxNumber;
	 private String companyCeoName;
	 private String companyEstablishmentDate;
	 @Id
	 private String companyCode;
	 private String homepage;
	 private String corporationLicenseNumber;
	 private String companyBusinessConditions;
	 private String companyZipCode;
}