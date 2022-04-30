package kr.co.seoulit.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.logistics.logisticsInfo.mapper.ItemDAO;
import kr.co.seoulit.logistics.logisticsInfo.repository.ItemRepository;
import kr.co.seoulit.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemInfoTO;
import kr.co.seoulit.logistics.logisticsInfo.to.ItemTO;
import kr.co.seoulit.logistics.material.mapper.BomDAO;
import kr.co.seoulit.logistics.material.to.BomTO;
import kr.co.seoulit.system.base.mapper.CodeDetailDAO;
import kr.co.seoulit.system.base.to.CodeDetailTO;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class ItemApplicationServiceImpl implements ItemApplicationService {
	
	// DAO 참조변수 선언
	
	private final ItemDAO itemDAO;
	
	private final CodeDetailDAO codeDetailDAO;
	
	private final BomDAO bomDAO;
	
	private final ItemRepository itemRepository;
	
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {
		ArrayList<ItemInfoTO> itemInfoList = null;
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);
		if (paramArray != null) {
			for (int i = 0; i < paramArray.length; i++) {
				switch (i + "") {
					case "0":
						if (searchCondition.equals("ITEM_CLASSIFICATION")) {
							map.put("itemClassification", paramArray[0]);
						} else if (searchCondition.equals("ITEM_GROUP_CODE")) {
							map.put("itemGroupCode", paramArray[0]);
						} else if (searchCondition.equals("STANDARD_UNIT_PRICE")) {
							map.put("minPrice", paramArray[0]);
						}
						break;
					case "1":
						map.put("maxPrice", paramArray[1]);
						break;
				}
			}
		}
		switch (searchCondition) {

		case "ALL":

			itemInfoList = itemDAO.selectAllItemList();

			break;

		case "ITEM_CLASSIFICATION":

			itemInfoList = itemDAO.selectItemList(map);

			break;

		case "ITEM_GROUP_CODE":

			itemInfoList = itemDAO.selectItemList(map);

			break;

		case "STANDARD_UNIT_PRICE":

			itemInfoList = itemDAO.selectItemList(map);

			break;

		}
		return itemInfoList;
	}

	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		CodeDetailTO detailCodeTO = new CodeDetailTO();
		BomTO bomTO = new BomTO();
		
		for (ItemTO TO : itemTOList) {

			String status = TO.getStatus();

			switch (status) {

			case "INSERT":

				itemDAO.insertItem(TO);
				insertList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Insert
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.insertDetailCode(detailCodeTO);

				
				// 새로운 품목이 완제품 (ItemClassification : "IT-CI") , 반제품 (ItemClassification : "IT-SI") 일 경우 BOM 테이블에 Insert
				if( TO.getItemClassification().equals("IT-CI") || TO.getItemClassification().equals("IT-SI") ) {
					
					bomTO.setNo(1);
					bomTO.setParentItemCode("NULL");
					bomTO.setItemCode( TO.getItemCode() );
					bomTO.setNetAmount(1);
					
					bomDAO.insertBom(bomTO);
				}
				
				
				break;

			case "UPDATE":

				itemDAO.updateItem(TO);

				updateList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Update
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.updateDetailCode(detailCodeTO);

				break;

			case "DELETE":

				itemDAO.deleteItem(TO);

				deleteList.add(TO.getItemCode());

				// CODE_DETAIL 테이블에 Delete
				detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
				detailCodeTO.setDetailCode(TO.getItemCode());
				detailCodeTO.setDetailCodeName(TO.getItemName());
				detailCodeTO.setDescription(TO.getDescription());

				codeDetailDAO.deleteDetailCode(detailCodeTO);

				break;

			}

		}

		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);
		return resultMap;

	}

	@Override
	public ItemTO getStandardUnitPrice(String itemCode) {
		
		// jpa 구현
		/*
		int price = 0;
		price = itemDAO.getStandardUnitPrice(itemCode);
		return price;
		*/
		ItemTO item = itemRepository.findByItemCode(itemCode).orElse(new ItemTO());

		return item;
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {
		int price = 0;
		price = itemDAO.getStandardUnitPriceBox(itemCode);
		return price;
	}
}
