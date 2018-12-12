/* Copyright (C) Tata Consultancy Services Pvt. Ltd. - All Rights Reserved
 * This file is part of TCS-PHI project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Written by Subhankar Maitra<maitra.subhankar@tcs.com>, November 2017.
 * 
 */


package com.tcs.PHI.SGPApiAccess;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcs.PHI.bean.FinDataReqBean;
import com.tcs.PHI.bean.HrlySalesReqBean;
import com.tcs.PHI.iikoApiAccess.MakeSGJsonRequest;



public class SGDataUploader {
	
	private static final Logger log = LoggerFactory.getLogger(SGDataUploader.class);
	
	private SGApiHandler apiHandler;
	
	/**
	 * @param apiHandler
	 */
	public SGDataUploader() {
		this.apiHandler = new SGApiHandler();
	} 

	public void uploadFinancialData(String storeId, String endPoint) {
		
		MakeSGJsonRequest jsonReqMaker = new MakeSGJsonRequest(storeId);
		List<FinDataReqBean> requestList = jsonReqMaker.makeFinancialReqData();
		
		for(FinDataReqBean req: requestList) {
			log.info("Request: "+req);
		}
		
		for(FinDataReqBean req: requestList) {
			String res = getApiHandler().postFinancial(getApiHandler().makeApiUrl(endPoint),req);
			log.info("Result -"+res);
		}
		
		log.info(requestList.size()+" records of Financial data uploaded to SDC database successfully.");
	}
	
	
	public void uploadHrlySalesData(String storeId, String endPoint) {
		
		MakeSGJsonRequest jsonReqMaker = new MakeSGJsonRequest(storeId);
		List<HrlySalesReqBean> requestList = new ArrayList<HrlySalesReqBean>();
		String startTime = null;
		
		for (int i=9;i<=23;i++) {
			
			if(i<10) startTime = "0".concat(String.valueOf(i));
			else startTime = String.valueOf(i);
			/*
			 * Generate Sales data for every hour
			 */
			log.debug("Start Time: "+startTime);
			requestList = jsonReqMaker.makeHrlySalesReqData(startTime);
			
			for(HrlySalesReqBean req: requestList) {
				String res = getApiHandler().postHrlySales(getApiHandler().makeApiUrl(endPoint),req);
				log.info("Result -"+res);
			}
			
		}
		
		log.info(requestList.size()+" records of Hourly Sales data uploaded to SDC database successfully.");
		
	}
	
	public SGApiHandler getApiHandler() {
		return apiHandler;
	}
	
	public void setApiHandler(SGApiHandler apiHandler) {
		this.apiHandler = apiHandler;
	}
}
