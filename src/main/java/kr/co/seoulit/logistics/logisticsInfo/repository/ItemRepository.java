package kr.co.seoulit.logistics.logisticsInfo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.co.seoulit.logistics.logisticsInfo.to.ItemTO;

public interface ItemRepository extends CrudRepository<ItemTO, String> {
	
	 Optional<ItemTO> findByItemCode(String itemCode);
	 
}
