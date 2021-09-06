package kr.co.seoulit.system.base.applicationService;

import java.util.ArrayList;

import kr.co.seoulit.system.base.to.AddressTO;

public interface AddressApplicationService {
		
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
}
