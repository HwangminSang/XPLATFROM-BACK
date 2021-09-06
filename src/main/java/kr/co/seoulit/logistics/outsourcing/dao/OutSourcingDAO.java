package kr.co.seoulit.logistics.outsourcing.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.logistics.outsourcing.to.OutSourcingTO;

@Mapper
public interface OutSourcingDAO {

	ArrayList<OutSourcingTO> selectOutSourcingList(HashMap<String,String> params);

}
