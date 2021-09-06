package kr.co.seoulit.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.production.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.sales.to.ContractDetailTO;

@Mapper
public interface ContractDetailDAO {

	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo);

	public ArrayList<ContractDetailTO> selectDeliverableContractDetailList(String contractNo);

	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(HashMap<String, String> map);
/*
	public void insertContractDetail(ContractDetailTO TO);

	public void updateContractDetail(ContractDetailTO TO);*/

	public void changeMpsStatusOfContractDetail(HashMap<String, String> map);

	public void deleteContractDetail(ContractDetailTO TO);
	
	public ArrayList<ContractDetailTO> insertContractDetail(HashMap<String, Object> map);
	
}
