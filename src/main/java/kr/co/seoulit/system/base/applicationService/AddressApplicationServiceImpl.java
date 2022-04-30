package kr.co.seoulit.system.base.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.system.base.mapper.AddressDAO;
import kr.co.seoulit.system.base.to.AddressTO;

@Component
public class AddressApplicationServiceImpl implements AddressApplicationService {
	// DAO 참조변수
	@Autowired
	private AddressDAO addressDAO;
	
	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue,
			String mainNumber) {
		ArrayList<AddressTO> addressList = null;
		AddressTO to= addressDAO.selectSidoCode(sidoName);
		String sidoCode = to.getSidoCode();

		HashMap<String, String> map = new HashMap<>();
		switch (searchAddressType) {

		case "roadNameAddress":

			String buildingMainNumber = mainNumber;
			map.put("sidoCode", sidoCode);
			map.put("searchValue", searchValue);
			map.put("buildingMainNumber", buildingMainNumber);
			addressList = addressDAO.selectRoadNameAddressList(map);

			break;

		case "jibunAddress":

			String jibunMainAddress = mainNumber;
			map.put("sidoCode", sidoCode);
			map.put("searchValue", searchValue);
			map.put("jibunMainAddress", jibunMainAddress);
			addressList = addressDAO.selectJibunAddressList(map);

			break;

		}
		return addressList;

	}

}
