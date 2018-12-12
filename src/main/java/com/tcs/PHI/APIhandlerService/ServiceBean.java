package com.tcs.PHI.APIhandlerService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tcs.PHI.bean.SgResBean;
import com.tcs.PHI.req.Document;
import com.tcs.PHI.req.ReqBean;
import com.tcs.PHI.res.FixedUntypedXMLDeserializer;
import com.tcs.PHI.res.ResBean;

@Service
/**
 * @author Sunandan Adhikary
 * service class that supports all api calls
 * one service bean object is basically used for one whole session for one api
 */
public class ServiceBean {
	
	private static final Logger log= LoggerFactory.getLogger(Service.class);
	
	
	@Autowired
	private  RestTemplate template ;
	@Autowired
	private ApiConfig config ;
	@Autowired
	protected XmlMapper xmlMapper ;
	
	private String token;
	

//private static ConfigurableApplicationContext context= new AnnotationConfigApplicationContext(ConfigBean.class);
//private static ConfigBean config =context.getBean(ConfigBean.class);

	public ServiceBean(){
		this.template=new RestTemplate();
		this.template.setErrorHandler(new MyResponseErrorHandler());
		this.xmlMapper=new XmlMapper();
		this.xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
	}
	
	/**
	 * @Constructor formation using configuration file
	 * @param filename String file name to load api configurations
	 */
	public ServiceBean(String filename,String storeCode){
		this.template=new RestTemplate();
		this.template.setErrorHandler(new MyResponseErrorHandler());
		this.config=new ApiConfig(filename,storeCode);
		this.xmlMapper=new XmlMapper();
		this.xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		this.setToken();
		}
	
	/**
	 * setting config file to get properties
	 * @param file String
	 */
	public void setApiConfig(String file,String storeCode) {
		this.config=new ApiConfig(file,storeCode);
		this.setToken();
	}
	/**
	 * getting properties from config file
	 * @param file String
	 */
	public ApiConfig getApiConfig(){
		return this.config;
	}
	
	/*
	 * ***************************************get restTemplate***********************************
	 */
	@Bean
	protected RestTemplate createTemplate() {
	
		return this.template;
	
	}
	/**
	 * setter for token if needed
	 * @param token
	 */
	public void setToken(String token){
		this.token=token;
	}
	/**
	 * token generation from certain URL passed
	 * @param url
	 * @return token
	 */
	public String getToken(String url) {
		this.token= this.createTemplate().getForObject(url, String.class);
		log.debug("logged in with token: "+this.token);
		return this.token;
	}
	/**
	 * Token generation taking values from {@link ApiConfig} properties
	 * and setting the value in token element
	 * @return token
	 */
	 
	private void setToken() {
		String url=ApiConfig.URL_AUTH;
		this.token= this.createTemplate().getForObject(url, String.class);
		log.debug("logged in with token: "+this.token);
}
	/**
	 * get token for this Service bean
	 * @return
	 */
	private String getToken(){
		return this.token;
	}
	
	/**
	 * log out taking values from developer
	 * @param url String
	 */
	 
	public void logOut(String url){
		String uri= url+this.token;
		String q= this.createTemplate().getForObject(uri, String.class);
		log.debug("logged in with token: "+q);
		
	}
	/**
	 * log out method
	 */
	 
	public void logOut(){
		String uri= this.config.URL_LOGOUT+this.token;
		String q= this.createTemplate().getForObject(uri, String.class);
		log.debug(q);
		
	}
	
	/**
	 *  to change Dates and Times to desired format
	 * @param currentParttern
	 * @param desiredPattern
	 * @param dateTime
	 * @return dateTimeinDesiredFormat
	 */
	
	public String changeDateTimeFormat(String currentParttern,String desiredPattern,String dateTime){
		DateTimeFormatter currentFormat= DateTimeFormatter.ofPattern(currentParttern);
		DateTimeFormatter desiredFormat= DateTimeFormatter.ofPattern(desiredPattern);
		LocalDateTime datetime= LocalDateTime.parse(dateTime, currentFormat);
		String dateTimeinDesiredFormat= datetime.format(desiredFormat);
		return dateTimeinDesiredFormat;
	}
	
	/**
	 * to change Dates to desired format
	 * @param currentParttern
	 * @param desiredPattern
	 * @param dateInput
	 * @return dateinDesiredFormat
	 */
	
	public String changeDateFormat(String currentParttern,String desiredPattern,String dateInput){
		DateTimeFormatter currentFormat= DateTimeFormatter.ofPattern(currentParttern);
		DateTimeFormatter desiredFormat= DateTimeFormatter.ofPattern(desiredPattern);
		LocalDate date= LocalDate.parse(dateInput, currentFormat);
		String dateinDesiredFormat= date.format(desiredFormat);
		return dateinDesiredFormat;
	}
	
	/**
	 * to change Times to desired format
	 * @param currentParttern
	 * @param desiredPattern
	 * @param timeInput
	 * @return timeinDesiredFormat
	 */
	
	public String changeTimeFormat(String currentParttern,String desiredPattern,String timeInput){
		DateTimeFormatter currentFormat= DateTimeFormatter.ofPattern(currentParttern);
		DateTimeFormatter desiredFormat= DateTimeFormatter.ofPattern(desiredPattern);
		LocalDate time= LocalDate.parse(timeInput, currentFormat);
		String timeinDesiredFormat= time.format(desiredFormat);
		return timeinDesiredFormat;
	}

	
	/*
	 * ********************************************to fetch Get methods response from iiko APIs*****************************************
	 */
	
	
	/**
	 * to fetch Get methods json response from iiko APIs
	 * @param url to hit
	 * @return response HashMap of String and Object
	 */
	@Bean
	public HashMap<String,Object> fetchJsonFromGetResponse(String urlWithouttoken){
		
		String url= urlWithouttoken+this.token;
		HashMap<String,Object> response=new HashMap<String,Object>();
		HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 ResponseEntity<HashMap> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,HashMap.class);
		 response=(HashMap<String,Object>)resEntity.getBody();
		 return response;

		}

	/**
	 * to get xml responses from apis via get method
	 * @param url to hit
	 * @return response HashMap of String and Object
	 */
	@Bean
	public HashMap<String,Object> fetchXmlFromGetResponse(String urlWithouttoken){
		
		String url= urlWithouttoken+this.token;
		 HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 //XmlMapper xmlMapper= new XmlMapper();
		 //xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 ResponseEntity<String> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,String.class);
		 HashMap<String, Object> response=new HashMap<String,Object>();
			try {
				response = (HashMap<String, Object>) xmlMapper.readValue(resEntity.getBody(),Object.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		}

	/*
	 * ******************************************************to get json responses from apis via post method*****************************************
	 */	
	
	/**
	 * to get json responses from apis via post method
	 * @param url
	 * @param req
	 * @return response HashMap of String and Object
	 */
	@SuppressWarnings("unchecked")
	@Bean
	public HashMap<String,Object> fetchJsonFromPostResponse(String urlWithouttoken,HashMap<String,Object> req){
			
		String url= urlWithouttoken+this.token;
		HashMap<String,Object> response=new HashMap<String,Object>();	
		 HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<HashMap<String,Object>> reqEntity= new HttpEntity<HashMap<String,Object>>(req,header);
		 ResponseEntity<HashMap> resEntity = this.createTemplate().postForEntity(url,reqEntity,HashMap.class);
		response=(HashMap<String,Object>) resEntity.getBody();
		return response;
		
	}
	
	/**
	 * for Olap Apis
	 * @param url
	 * @param req
	 * @return response json response caught in {@link ResBean}}
	 */
	
	@Bean
	public ResBean fetchJsonFromPostResponse(String urlWithouttoken,ReqBean req){
		
		String url= urlWithouttoken+this.token;
		ResBean response=new ResBean();	
		 HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<ReqBean> reqEntity= new HttpEntity<ReqBean>(req,header);
		 ResponseEntity<ResBean> resEntity = this.createTemplate().postForEntity(url,reqEntity,ResBean.class);
		response= resEntity.getBody();
		return response;
		
	}
	
	
	
	/**
	 * to fetch Post methods response from iiko APIs for XML api s
	 * @param url
	 * @param req
	 * @return response HashMap of String and Object
	 */
	
	@Bean
	public HashMap<String,Object> fetchXmlFromPostResponse(String urlWithouttoken,Object req){
		
		String url= urlWithouttoken+this.token;
		 HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(req,header);
		 ResponseEntity<String> resEntity = this.createTemplate().postForEntity(url,reqEntity,String.class);
		// XmlMapper xmlMapper=new XmlMapper();
		// xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 HashMap<String, Object> response=new HashMap<String,Object>();
			try {
				response = (HashMap<String, Object>) xmlMapper.readValue(resEntity.getBody(),Object.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		
	}
	
	/**
	 * *post Purchase Invoice request
	 * @param url
	 * @param doc
	 * @return response HashMap of String and Object
	 */

	@Bean
	public HashMap<String,Object> purchaseInvoice(String urlWithouttoken,Document doc){
		String url= urlWithouttoken+this.token;
		 HttpHeaders header= new HttpHeaders();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(doc,header);
		 ResponseEntity<String> resEntity = this.createTemplate().postForEntity(url,reqEntity,String.class);
		 //XmlMapper xmlMapper=new XmlMapper();
		 //xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 HashMap<String, Object> response=new HashMap<String,Object>();
			try {
				response = (HashMap<String, Object>) xmlMapper.readValue(resEntity.getBody(),Object.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		
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
	public Object fetchJsonFromGetResponse(String url,HttpHeaders header){
		
		Object response=new Object();
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 ResponseEntity<Object> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,Object.class);
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
	public Object fetchXmlFromGetResponse(String url,HttpHeaders header){
		
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(header);
		 //XmlMapper xmlMapper= new XmlMapper();
		 //xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 ResponseEntity<String> resEntity = this.createTemplate().exchange(url,HttpMethod.GET,reqEntity,String.class);
		 Object response=new Object();
			try {
				response = xmlMapper.readValue(resEntity.getBody(),Object.class);
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
	public Object fetchJsonFromPostResponse(String url,Object req,HttpHeaders header){
			
		Object response=new Object();	
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(req,header);
		 ResponseEntity<Object> resEntity = this.createTemplate().postForEntity(url,reqEntity,Object.class);
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
	public Object fetchXmlFromPostResponse(String url,Object req,HttpHeaders header){
		
		 header.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
		 header.add("Accept-Language", "en");
		 HttpEntity<Object> reqEntity= new HttpEntity<Object>(req,header);
		 ResponseEntity<String> resEntity = this.createTemplate().postForEntity(url,reqEntity,String.class);
		// XmlMapper xmlMapper=new XmlMapper();
		// xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		 Object response=new Object();
			try {
				response =  xmlMapper.readValue(resEntity.getBody(),Object.class);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return response;
		
	}
	
	public Object fetchFilteredXmlFromGetResponse(String url, String filter){
		Object o=this.fetchXmlFromGetResponse(url).get(filter) instanceof ArrayList;
		if(o instanceof ArrayList){
		ArrayList<HashMap<String,Object>> data=(ArrayList<HashMap<String, Object>>) o;
		return data;
		}else if(o instanceof HashMap){
			HashMap<String,Object> data=(HashMap<String, Object>) o;
			return data;
		}
		return o;
		
		}
	
		
}
