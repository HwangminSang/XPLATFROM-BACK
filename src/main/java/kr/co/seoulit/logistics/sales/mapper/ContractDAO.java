package kr.co.seoulit.logistics.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.sales.to.ContractInfoTO;
import kr.co.seoulit.logistics.sales.to.ContractTO;
import kr.co.seoulit.logistics.sales.to.EstimateTO;

@Mapper
public interface ContractDAO {

	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(HashMap<String, String> map);

	public ArrayList<ContractInfoTO> selectContractListByDate(HashMap<String, String> map);
	
	public ArrayList<ContractInfoTO> getContractListByCondition(HashMap<String, String> map);

	public ArrayList<ContractInfoTO> selectContractListByCustomer(String customerCode);

	public ArrayList<ContractInfoTO> selectDeliverableContractListByCondition(HashMap<String,String> map);
	
	public int selectContractCount();

	public void insertContract(ContractTO TO);

	public void updateContract(ContractTO TO);

	public void deleteContract(ContractTO TO);

}
