package kr.co.seoulit.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.sales.to.EstimateDetailTO;

@Mapper
public interface EstimateDetailDAO {

	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo);

	public ArrayList<EstimateDetailTO> selectEstimateDetailCount(String estimateNo);

	public void insertEstimateDetail(EstimateDetailTO TO);

	public void updateEstimateDetail(EstimateDetailTO TO);

	public void deleteEstimateDetail(EstimateDetailTO TO);

	public int selectEstimateDetailSeq();
	
	public void initDetailSeq(String est);
	
	public void reArrangeEstimateDetail(HashMap<String,String> params);
}