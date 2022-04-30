package kr.co.seoulit.logistics.sales.repository;

import org.springframework.data.repository.CrudRepository;

import kr.co.seoulit.logistics.sales.to.ContractTO;

public interface ContractRepository extends CrudRepository<ContractTO, String> {

}
