package kr.co.seoulit.system.base.to;

import kr.co.seoulit.system.common.annotation.Dataset;
import lombok.Data;


@Dataset(name="gds_api")
@Data
public class ApiAirTo {

	
   private String airLine;
   private String airPort;
   private String firstDate;
   private String flightId;
   private String startTime;
   private String lastDate;
   private String monday;
   private String tuesday;
   private String wednesday;
   private String thursday;
   private String friday;
   private String saturday;
   private String sunday;
}  
