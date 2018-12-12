package com.tcs.PHI.Application;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.tcs.PHI.SGPApiAccess.SGApiHandler;
import com.tcs.PHI.bean.FinDataReqBean;
import com.tcs.PHI.bean.HSMixDataReqBean;
import com.tcs.PHI.bean.HrlySalesReqBean;
import com.tcs.PHI.bean.PettyCashReqBean;
import com.tcs.PHI.iikoApiAccess.IIikoApiHandler;
import com.tcs.PHI.service.Service;

public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	private static Service service=new Service("application.properties","16735");
	private static SGApiHandler handler= new SGApiHandler("SGP.properties");
	static DateTimeFormatter df=DateTimeFormatter.ISO_DATE_TIME;
	
	/**
	 * to upload financial data and see if successful
	 * @param from
	 * @param to
	 */
	public static void financialDataUpload(String from ,String to/*List<FinDataReqBean> requestList*/){
		
		List<FinDataReqBean> requestList=service.makeFinancialRequests(from, to);
		if(handler.updateFinancialTable(requestList))
			{
			log.debug(requestList.size()+" number of records updated in Financial table");
			}
		
	}
	/**
	 * to upload inventory transaction data and see if successful
	 * @param from
	 * @param to
	 */
	public static void invTrnxDataUpload(String from ,String to/*List<FinDataReqBean> requestList*/){
		
		List<FinDataReqBean> requestList=service.makeFinancialRequests(from, to);
		if(handler.updateFinancialTable(requestList))
			{
			log.debug(requestList.size()+" number of records updated in Financial table");
			}
		
	}
	
	//Hourly Sales Mix
	public static void HSMixDataUpload(String from, String to) {
		LocalDateTime fromTime = LocalDateTime.parse(from, df);
		LocalDateTime toTime = LocalDateTime.parse(to, df);

		while (service.count != 24) {
			LocalDateTime end = fromTime.plusHours(1);
			service.setHourly(fromTime, end);
			fromTime = end;
			log.debug("hi:" + service.count + "--" + service.startTime + "--" + service.endTime);
			List<HSMixDataReqBean> requestList = service.makeHSMixRequests(service.startTime, service.endTime);
			if(requestList!=null||!requestList.isEmpty()){
			if (handler.updateHSMixTable(requestList, service.startTime, service.endTime)) {
				log.debug(requestList.size() + " number of records updated in HSMix table");
			}}
		}
	}
	
	//Hourly Sales

	public static void HrlySalesDataUpload(String from, String to) {
		LocalDateTime fromTime = LocalDateTime.parse(from, df);
		LocalDateTime toTime = LocalDateTime.parse(to, df);

		while (service.count != 24) {
			LocalDateTime end = fromTime.plusHours(1);
			service.setHourly(fromTime, end);
			fromTime = end;
			log.debug("hi:" + service.count + "--" + service.startTime + "--" + service.endTime);
			List<HrlySalesReqBean> requestList = service.makeHrlySalesRequests(service.startTime, service.endTime);
			if(requestList!=null){
			if (handler.updateHrlySalesTable(requestList, service.startTime, service.endTime)) {
				log.debug(requestList.size() + " number of records updated in HSMix table");
			}
			}
		}
	}
	
	//Petty Cash
	
	public static void PettyCashDataUpload(String from, String to) {

		List<PettyCashReqBean> requestList = service.makePettyCashRequests(from, to);
		if (handler.updatePettyCashTable(requestList)) {
			log.debug(requestList.size() + " number of records updated in Financial table");
		}

	}
	
	public static void main(String[] args){
		//FinDataReqBean fb=new FinDataReqBean();
		//fb.addData(5, "345", "234", 33, "2017-11-05T00:00:00.000");
		//List<FinDataReqBean> lst= new ArrayList<FinDataReqBean>();
		//lst.add(fb);
		//Application.financialDataUpload("2017-01-01T00:00:00.000","2017-11-01T00:00:00.000");
		//Application.HSMixDataUpload("2017-11-01T00:00:00.000","2017-11-01T23:59:59.000");
		//HSMixDataUpload("2017-01-01T00:00:00.000", "2017-11-01T00:00:00.000");
		HrlySalesDataUpload("2017-11-06T00:00:00.000", "2017-11-22T00:00:00.000");
		//PettyCashDataUpload("2017-01-01T00:00:00.000", "2017-11-01T00:00:00.000");
	}

}
