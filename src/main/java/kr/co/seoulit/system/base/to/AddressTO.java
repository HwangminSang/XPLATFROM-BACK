package kr.co.seoulit.system.base.to;

import lombok.Data;

@Data
public class AddressTO extends BaseTO {

	private String addressNo;
	private int cnt;
	private String zipCode;
	private String addressType;
	private String address;
	private String sidoCode;


}
