package kr.co.seoulit.logistics.material.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import kr.co.seoulit.logistics.material.to.StockTO;

public interface StockRepository extends CrudRepository<StockTO, String> {

	List<StockTO> findAll(Sort sort);

}
