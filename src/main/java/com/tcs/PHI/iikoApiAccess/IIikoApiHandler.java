package com.tcs.PHI.iikoApiAccess;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.PHI.APIhandlerService.ApiConfig;
import com.tcs.PHI.APIhandlerService.ServiceBean;
import com.tcs.PHI.req.ReqBean;
import com.tcs.PHI.res.ResBean;


public class IIikoApiHandler {

	
	private static final Logger log = LoggerFactory.getLogger(IIikoApiHandler.class);

	private ServiceBean iikoService;
	String storeCode=null;

	
	
	public IIikoApiHandler(String configFile,String storeCode){
		iikoService=new ServiceBean(configFile,storeCode);
		this.storeCode=storeCode;
	}
	
	/************************************************************finance handler**************************************************************************************/
	/**
	 * to access iiko api for financial data
	 * @param from
	 * @param to
	 * @return
	 */
	public List<ResBean> handleFinance(String from,String to){
		List<ReqBean> requestList=this.makeFinancialReqData(from,to);
		List<ResBean> responsetList=this.makeOlapResponse(requestList);
		if(responsetList!=null)
			return responsetList;
		return responsetList;	
		
	}
	
	/*
	 * ***********************************************inventory handler********************************************************
	 */
	/**
	 * olap responses list for inv trnx
	 * @param from
	 * @param to
	 * @param storeId
	 * @return
	 */
	public List<ResBean> handleInventoryTrnxOlapPart(String from,String to){
		List<ReqBean> requestList=this.makeInventoryTrnxOlapRequestData(from, to);
		List<ResBean> responsetList=this.makeOlapResponse(requestList);
		if(responsetList!=null)
			return responsetList;
		return responsetList;	
	}
	
	/************************************************hourly sales mix handler****************************************************/
	public List<ResBean> handleHSMix(String from,String to){
		List<ReqBean> requestList=this.makeHSMixReqData(from,to);
		List<ResBean> responsetList=this.makeOlapResponse(requestList);
		if(responsetList!=null)
			return responsetList;
		return responsetList;
	}
	
	/**************************************************hourly sales handler*******************************************************/
	 
	 public List<ResBean> handleHrlySales(String from,String to){
		List<ReqBean> requestList=this.makeHrlySalesReqData(from,to);
		List<ResBean> responsetList=this.makeOlapResponse(requestList);
		if(responsetList!=null)
			return responsetList;
		return responsetList;
	}
	 
	 
	 /***************************************************petty cash handler*********************************************************/
	 
	 public List<ResBean> handlePettyCash(String from,String to){
			List<ReqBean> requestList=this.makePettyCashReqData(from,to);
			List<ResBean> responsetList=this.makeOlapResponse(requestList);
			if(responsetList!=null)
				return responsetList;
			return responsetList;
		}
	 
	 

	
	
	/*
	 * ***************************************************Request Makers********************************************
	 */
	/**
	 * maker of iiko requests for financial data
	 * @param from
	 * @param to
	 * @return
	 */
	public List<ReqBean> makeFinancialReqData(String from ,String to) {
		
		
		String requestJson = null;
		List<ReqBean> requestList = new ArrayList<ReqBean>();
		
		//Logic to create SDC request data from iiko API response data
	
		ReqBean iikoRequest = new ReqBean("SALES",Arrays.asList("OpenDate.Typed","Delivery.IsDelivery","Delivery.ServiceType",
				"OrderType","NonCashPaymentType","Banquet"),Arrays.asList("DishDiscountSumInt.withoutVAT","UniqOrderId"));
		try {
			iikoRequest.addDefaultFilterByRange(from,to);
			log.debug(iikoRequest.toString());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		requestList.add(iikoRequest);
		return requestList;
		
	}
/*********************************************************Hourly Sales Mix********************************************************/
	
	public List<ReqBean> makeHSMixReqData(String from, String to) {
		List<ReqBean> requestList = null;
		requestList = new ArrayList<ReqBean>();

		ReqBean iikoRequest = new ReqBean("SALES",
				Arrays.asList("OpenDate.Typed", "Delivery.IsDelivery", "Delivery.ServiceType", "NonCashPaymentType",
						"Banquet", "DishCode", "OriginName", "PayTypes", "OrderDiscount.Type"),
				Arrays.asList("UniqOrderId", "DishDiscountSumInt", "DiscountSum"));
		try {
			iikoRequest.addDefaultFilterByRange(from, to);
			log.debug(iikoRequest.toString());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		requestList.add(iikoRequest);

		return requestList;
	}

	
	/*****************************************************Hourly Sales********************************************************/
	
	public List<ReqBean> makeHrlySalesReqData(String from, String to) {
		List<ReqBean> requestList = null;
		requestList = new ArrayList<ReqBean>();

		ReqBean iikoRequest = new ReqBean(
				"SALES", Arrays.asList("OpenDate.Typed", "Delivery.IsDelivery", "Delivery.ServiceType",
						"NonCashPaymentType", "Banquet", "OriginName"),
				Arrays.asList("UniqOrderId", "DishDiscountSumInt"));
		try {
			iikoRequest.addFilterByRange("OpenDate", "DateRange", "CUSTOM", from, to);;
			log.debug(iikoRequest.toString());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		requestList.add(iikoRequest);
		String request=null;
		try {
			request = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(iikoRequest);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		log.debug(request);
		return requestList;
	}
	
	/************************************************petty cash*************************************************/
	
	
	public List<ReqBean> makePettyCashReqData(String from, String to) {
		List<ReqBean> requestList = null;
		requestList = new ArrayList<ReqBean>();

		ReqBean iikoRequest = new ReqBean("TRANSACTIONS",
				Arrays.asList("Account.Name", "TransactionType", "DateTime.Typed", "Sum.ResignedSum"),
				Arrays.asList("Amount"));
		try {
			iikoRequest.addDefaultFilterByRange(from, to);
			log.debug(iikoRequest.toString());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		requestList.add(iikoRequest);

		return requestList;
	}
	

	/**
	 * maker of item transxn olap requests : making required olap req for item_trnx
	 * @param from
	 * @param to
	 * @return list of olap requests
	 */
	public List<ReqBean> makeInventoryTrnxOlapRequestData(String from,String to){
		List<ReqBean> requestList=null;
		//sales part
		ReqBean olapSalesReq= new ReqBean();
		olapSalesReq.setReportType("SALES");  //report type
		olapSalesReq.addGroupByRowField("OpenDate.Typed",
				"Delivery.IsDelivery",
				"Delivery.ServiceType",
				"OrderType",
				"NonCashPaymentType",
				"Banquet");  //groupbyrow fields
		olapSalesReq.addAggregateField(
				"DishDiscountSumInt.withoutVAT",
				"UniqOrderId","DiscountSum" );  //aggregatefields
		try {
			olapSalesReq.addFilterByRange("DateTime.Typed",  "DateRange", "CUSTOM", from, to);// adding range filter
			olapSalesReq.addFilterByValues("Storned", "ExcludeValues", "TRUE");// adding filter by value
			olapSalesReq.addFilterByValues("OrderDeleted", "IncludeValues", "NOT_DELETED");// adding filter by value
			olapSalesReq.addFilterByValues("DeletedWithWriteoff", "IncludeValues", "NOT_DELETED");// adding filter by value
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		requestList.add(olapSalesReq);
		
		//tranx part
		ReqBean olapTrnxReq= new ReqBean();
		olapTrnxReq.setReportType("TRANSACTIONS");  //report type
		olapTrnxReq.addGroupByRowField("DateTime.DateTyped",
			    "Product.Type",
			    "Product.Id",
			    "Product.Num",
			    "Document",
			    "Counteragent.Id",
			    "TransactionType",
			    "Account.CounteragentType");  //groupbyrow fields
		olapTrnxReq.addAggregateField(
				"Sum.ResignedSum",
			    "Amount" );  //aggregatefields
		try {
			olapTrnxReq.addFilterByRange("DateTime.Typed",  "DateRange", "CUSTOM", from, to);// adding range filter
			olapTrnxReq.addFilterByValues("Product.Type", "IncludeValues", "GOODS");// adding filter by value
			olapTrnxReq.addFilterByValues("Account.Type", "IncludeValues", "INVENTORY_ASSETS");// adding filter by value
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		 requestList.add(olapTrnxReq);
		
		 return requestList;
	}
	
	
	
	
	
	
	/*
	 * **************************************Response Makers********************************************
	 */
	
/**
 * make olap responses
 * @param requestList
 * @param responseList
 * @return
 */
	public List<ResBean> makeOlapResponse(List<ReqBean> requestList){
		
		List<ResBean> responseList= new ArrayList<ResBean>();
		
		String url = iikoService.getApiConfig().URL_OLAP;
		boolean isadded=false;
		//calling iiko api
		for(ReqBean iikoRequest:requestList){
			isadded=responseList.add(iikoService.fetchJsonFromPostResponse(url, iikoRequest));
		}
		return responseList;
	
	}
/**
 * for inventory api 
 * @param datetime
 * @param storeCode
 * @return
 */
	public HashMap<String,Object> makeInventoryBalanceResponse(String datetime,String storeCode){
		HashMap<String,Object> invBalance= new HashMap<String,Object>();
		String deptGUID = null;
		ArrayList<HashMap<String,Object>> departments=(ArrayList<HashMap<String,Object>>)iikoService.fetchFilteredXmlFromGetResponse(ApiConfig.URL_DEPARTMENTS, "corporateItemDto");
		System.out.println("departments--->"+departments.size());
		for(HashMap<String,Object> department : departments) {
			if(String.valueOf(department.get("code")).equals(storeCode)) {
				deptGUID=department.get("id").toString();
			}
		}
		if(deptGUID!=null){
			invBalance=iikoService.fetchJsonFromGetResponse(ApiConfig.getURL_INVENTORY_BALANCE(datetime, deptGUID));
			log.debug(invBalance.toString());
		}else
			log.debug("dept guid is null");
		/*
		//picking from files
		HashMap<String,Object> suppliers= new HashMap<String,Object>();
		try {
			suppliers = (HashMap<String,Object>)this.xmlMapper.readValue(new File("D:\\workspace\\test\\src\\test\\resources\\Suppliers.xml"),
					Object.class);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		*/
		return invBalance;
		
	}
	/**
	 * products api
	 * @param from
	 * @param to
	 * @return
	 */
	public HashMap<String,Object> makeProductsResponse(String from,String to){
		HashMap<String,Object> products= new HashMap<String,Object>();
		String url=ApiConfig.URL_PRODUCTS;
		products=iikoService.fetchXmlFromGetResponse(url);
		/*
		//picking from files
		HashMap<String,Object> suppliers= new HashMap<String,Object>();
		try {
			suppliers = (HashMap<String,Object>)this.xmlMapper.readValue(new File("D:\\workspace\\test\\src\\test\\resources\\Suppliers.xml"),
					Object.class);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		*/
		return products;
		
	}
/**
 * supplier api
 * @param from
 * @param to
 * @param supplierName
 * @return
 */
	public HashMap<String,Object> makeSuppliersResponse(String supplierId){
		HashMap<String,Object> supplier= new HashMap<String,Object>();
		String url=ApiConfig.URL_SUPPLIERS_SEARCH+"id="+supplierId+"&key=";
		supplier=iikoService.fetchXmlFromGetResponse(url);
		/*
		//picking from files
		HashMap<String,Object> suppliers= new HashMap<String,Object>();
		try {
			suppliers = (HashMap<String,Object>)this.xmlMapper.readValue(new File("D:\\workspace\\test\\src\\test\\resources\\Suppliers.xml"),
					Object.class);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		*/
		return supplier;
	}
/**
 * suppliers price list api
 * @param from
 * @param to
 * @param supplierCode
 * @return
 */
	public HashMap<String,Object> makeSuppliersPricelistResponse(String from,String to,String supplierCode){
		
		HashMap<String,Object> supplierPriceList= new HashMap<String,Object>();
		String url=ApiConfig.getURL_SUPPLIER_PRICELIST(supplierCode);
		supplierPriceList=iikoService.fetchXmlFromGetResponse(url);
		/*
		//picking from files
		HashMap<String,Object> supplierPriceLists= new HashMap<String,Object>();

		try {
			supplierPriceLists = (HashMap<String,Object>)iikoService.xmlMapper.readValue(new File("D:\\workspace\\IDN_StoreToWarehouse\\src\\main\\resources\\suppliersPricelist.xml"),
					Object.class);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		*/
	 return supplierPriceList;
	}
	}
	
