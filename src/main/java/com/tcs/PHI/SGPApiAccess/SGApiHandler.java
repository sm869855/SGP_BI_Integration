/* Copyright (C) Tata Consultancy Services Pvt. Ltd. - All Rights Reserved
 * This file is part of TCS-PHI project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Written by Subhankar Maitra<maitra.subhankar@tcs.com>, November 2017.
 * 
 */


package com.tcs.PHI.SGPApiAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.PHI.APIhandlerService.ServiceBean;
import com.tcs.PHI.bean.FinDataReqBean;
import com.tcs.PHI.bean.HSMixDataReqBean;
import com.tcs.PHI.bean.HrlySalesReqBean;
import com.tcs.PHI.bean.PettyCashReqBean;
import com.tcs.PHI.bean.SgResBean;
import com.tcs.PHI.service.Service;


public class SGApiHandler extends ServiceBean{
	
	private static final Logger log = LoggerFactory.getLogger(SGApiHandler.class);
	@Autowired
	private static SGPApiConfig config;
	
	//private String UAT_HOST_IDC = "http://129.126.152.23:11001/dc-api/idc/";
	
	public SGApiHandler(String configFileForSGP){
		super();
		config= new SGPApiConfig(configFileForSGP);
	}

	/*
	 * ********************************************to fetch Get methods response from iiko APIs with headers*****************************************
	 */
	
	
	/**
	 * to fetch Get methods json response from any APIs
	 * @param url to hit with token if required
	 * @param header authentication/any other header needed
	 * @return response Object 
	 */
	@Bean
	@Override
	public SgResBean fetchJsonFromGetResponse(String url,HttpHeaders header){
		
		SgResBean response=new SgResBean();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 ResponseEntity<SgResBean> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,SgResBean.class);
		 response=resEntity.getBody();
		 return response;

		}

	/**
	 * to fetch Get methods xml response from any APIs
	 * @param url to hit with token if required
	 * @param header authentication/any other header needed
	 * @return response Object
	 */
	@Bean
	@Override
	public SgResBean fetchXmlFromGetResponse(String url,HttpHeaders header){
		
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 //XmlMapper xmlMapper= new XmlMapper();
		 //xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 ResponseEntity<String> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,String.class);
		 SgResBean response=new SgResBean();
			try {
				response = xmlMapper.readValue(resEntity.getBody(),SgResBean.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		}

	/*
	 * ******************************************************to get json responses from apis via get/post method with required headers*****************************************
	 */	
	
	/**
	 * can add desired header
	 * @param <T>
	 * @param <T>
	 * @param url with token if needed
	 * @param req
	 * @param header
	 * @return response Object type
	 */
	@SuppressWarnings("unchecked")
	@Bean
	@Override
	public SgResBean fetchJsonFromPostResponse(String url,Object req,HttpHeaders header){
			
		SgResBean response=new SgResBean();	
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(req,header);
		 ResponseEntity<SgResBean> resEntity = this.createTemplate().postForEntity(url,reqEntity,SgResBean.class);
		response= resEntity.getBody();
		return response;
		
	}
	
	
	
	/**
	 * can add header
	 * @param url With token if needed
	 * @param req
	 * @param header
	 * @return response Object type
	 */
	
	@Bean
	@Override
	public SgResBean fetchXmlFromPostResponse(String url,Object req,HttpHeaders header){
		
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(req,header);
		 ResponseEntity<String> resEntity = this.createTemplate().postForEntity(url,reqEntity,String.class);
		// XmlMapper xmlMapper=new XmlMapper();
		// xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 SgResBean response=new SgResBean();
			try {
				response =  xmlMapper.readValue(resEntity.getBody(),SgResBean.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		
	}
	
	

	public HttpHeaders createHttpGetHeaders()
	{
	    String notEncoded = config._ID + ":" + config._PWD;
		//String notEncoded = "Basic SUlLTzpOMkthNSxAOiQ5UGh4K0Iz";
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Basic "+encodedAuth);
	    headers.add("Accept", "application/xml");
	    headers.add("X-API-Version","1.0");
	    //headers.add("Accept-Language","en");
	    headers.setContentType(MediaType.APPLICATION_XML);
	    //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    //headers.setContentType(MediaType.APPLICATION_JSON);
	    log.info(encodedAuth);
	    return headers;
	}
	
	public HttpHeaders createHttpPostHeaders() {
	    String notEncoded = config._ID + ":" + config._PWD;
		//String notEncoded = "Basic SUlLTzpOMkthNSxAOiQ5UGh4K0Iz";
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Basic "+encodedAuth);
	    headers.add("X-API-Version","1.0");
	    headers.add("Accept", "application/json");
	    headers.add("Content-Type","application/json");
	  //headers.add("Accept-Language","en");
	   
	    log.info(encodedAuth);
	    return headers;
	}
	    
	
	
	public boolean updateFinancialTable(List<FinDataReqBean> requestList){
		
		for(FinDataReqBean req: requestList) {
			String request=null;
			try {
				request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(req);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
			log.debug(requestList.size()+" number of rows to update .Here's the requestList :"+request);
			SgResBean response=this.fetchJsonFromPostResponse(config.URL_FINANCIAL, req, this.createHttpPostHeaders());
			//log.debug(o.toString());
			//HashMap<String,Object> response=  (HashMap<String,Object>) o;
			log.debug(response.toString());
			log.debug("Response message :"+response.getStatus().get("message"));
			if(response.getStatus().get("code").equals("200")){
				return true;
			}else{
				log.debug("here's the error: "+response.toString());
			}
				
			/*if(((HashMap<String,String>)response.get("status")).get("code").equals("200")){
				log.debug(((HashMap<String,String>)response.get("status")).get("message"));
				return true;
			}*/
		}
		return false;
		
	}

		
	
	public boolean updateInventoryTranxTable(List<FinDataReqBean> requestList){
		
		for(FinDataReqBean req: requestList) {
			String request=null;
			try {
				request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(req);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
			log.debug(requestList.size()+" number of rows to update .Here's the requestList :"+request);
			SgResBean response=this.fetchJsonFromPostResponse(config.URL_FINANCIAL, req, this.createHttpPostHeaders());
			//log.debug(o.toString());
			//HashMap<String,Object> response=  (HashMap<String,Object>) o;
			log.debug(response.toString());
			log.debug("Response message :"+response.getStatus().get("message"));
			if(response.getStatus().get("code").equals("200")){
				return true;
			}else{
				log.debug("here's the error: "+response.toString());
			}
				
			/*if(((HashMap<String,String>)response.get("status")).get("code").equals("200")){
				log.debug(((HashMap<String,String>)response.get("status")).get("message"));
				return true;
			}*/
		}
		return false;
		
	}

	public boolean updateHSMixTable(List<HSMixDataReqBean> requestList,String from,String to) {

		for (HSMixDataReqBean req : requestList) {
			String request = null;
			try {
				request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(req);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
			log.debug(requestList.size() + " number of rows to update .Here's the requestList :" + request);
			SgResBean response = this.fetchJsonFromPostResponse(config.URL_HSMIX, req, this.createHttpPostHeaders());
			log.debug(response.toString());
			log.debug("Response message :" + response.getStatus().get("message"));
			if (response.getStatus().get("code").equals("200")) {
				return true;
			} else {
				log.debug("here's the error: " + response.toString());
			}

		}
		return false;

	}
	
	public boolean updateHrlySalesTable(List<HrlySalesReqBean> requestList,String from,String to) {
		
		for (HrlySalesReqBean req : requestList) {
			String request = null;
			try {
				request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(req);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
			log.debug(requestList.size() + " number of rows to update .Here's the requestList :" + request);
			/*SgResBean response = this.fetchJsonFromPostResponse(config.URL_HrlySales, req, this.createHttpPostHeaders());
			log.debug(response.toString());
			log.debug("Response message :" + response.getStatus().get("message"));
			if (response.getStatus().get("code").equals("200")) {
				return true;
			} else {
				log.debug("here's the error: " + response.toString());
			}*/

		}
		return false;

	}
	
	public boolean updatePettyCashTable(List<PettyCashReqBean> requestList) {

		for (PettyCashReqBean req : requestList) {
			String request = null;
			try {
				request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(req);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
			log.debug(requestList.size() + " number of rows to update .Here's the requestList :" + request);
			SgResBean response = this.fetchJsonFromPostResponse(config.URL_PettyCash, req, this.createHttpPostHeaders());
			log.debug(response.toString());
			log.debug("Response message :" + response.getStatus().get("message"));
			if (response.getStatus().get("code").equals("200")) {
				return true;
			} else {
				log.debug("here's the error: " + response.toString());
			}

		}
		return false;

	}


	

}
