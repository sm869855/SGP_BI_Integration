package com.tcs.PHI.APIhandlerService;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class ApiConfig {
	//from config file
	private static String URL_AUTH_ID;
	private static String URL_AUTH_PWD;
	public static String URL_HOST_CHAIN;
	public static String URL_HOST;
	public static String URL_COUNTRY;
	public static String URL_AUTH;
	public static String URL_LOGOUT;
	public static String URL_OLAP;
	public static String URL_EMPS;
	public static String URL_EMP_SEARCH;
	public static String URL_STORES;
	public static String URL_STORE_SEARCH;
	public static String URL_DEPARTMENTS;
	public static String URL_PRODUCTS;
	public static String URL_PRODUCT_SEARCH;
	public static String URL_SUPPLIERS;
	public static String URL_SUPPLIERS_SEARCH;
	public static String URL_SUPPLIER_PRICELIST;
	public static String URL_EDI_ORDERS;
	public static String URL_EVENTS;
	public static String URL_EVENT_SEARCH;
	public static String URL_INVENTORY_BALANCE;
	private Properties props;

	public ApiConfig(String configFilePath,String storeCode) {

	try {
		props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(configFilePath));
	} catch (IOException e) {
		
	}
	
	URL_HOST= "https://"+storeCode+props.getProperty("iiko_host");
	URL_AUTH_ID=props.getProperty("iiko_id");
	URL_AUTH_PWD=props.getProperty("iiko_pwd");
	URL_COUNTRY = props.getProperty("iiko_country");
	URL_AUTH=URL_HOST+"/resto/api/auth?login="+URL_AUTH_ID+"&pass="+URL_AUTH_PWD;
	URL_LOGOUT=URL_HOST+"/resto/api/logout?key=";
	URL_OLAP=URL_HOST+"/resto/api/v2/reports/olap?key=";
	URL_EMPS=URL_HOST+"/resto/api/employees?key=";
	URL_EMP_SEARCH=URL_HOST+"/resto/api/employees/search?";
	URL_STORES=URL_HOST+"/resto/api/corporation/stores?key=";
	URL_STORE_SEARCH=URL_HOST+"/resto/api/corporation/stores/search?";
	URL_DEPARTMENTS=URL_HOST+"/resto/api/corporation/departments?key=";
	URL_PRODUCTS=URL_HOST+"/resto/api/products?key=";
	URL_PRODUCT_SEARCH=URL_HOST+"/resto/api/corporation/products/search?";
	URL_SUPPLIERS=URL_HOST+"/resto/api/suppliers?key=";
	URL_SUPPLIERS_SEARCH=URL_HOST+"/resto/api/suppliers/search?";
	URL_EVENTS=URL_HOST+"/resto/api/events?key=";
	URL_EVENT_SEARCH=URL_HOST+"/resto/api/events/search?";
	URL_EDI_ORDERS=URL_HOST+"/resto/api/edi/709f5a71-47b6-f2fc-b5a8-d10176a851d7/orders/list?";
	
}
	public static String makeURL_HOST(String storeCode){
		if(storeCode!=null)
		URL_HOST= "https://"+storeCode+URL_HOST;
		return URL_HOST;
	}

	
	public static String getURL_INVENTORY_BALANCE(String forDateTime,String deptGUID) {
		
		URL_INVENTORY_BALANCE=URL_HOST+"/resto/api/v2/reports/balance/stores?timestamp="+forDateTime+"&department="+deptGUID+"&key=";
		return URL_INVENTORY_BALANCE;
	}
	public static String makeURL_EMP_SEARCH(String filterField,String filterValue) {
		URL_EMP_SEARCH=URL_HOST+"/resto/api/employees/search?"+filterField+"="+filterValue+"&key=";
		return URL_EMP_SEARCH;
	}

	public static String makeURL_STORE_SEARCH(String filterField,String filterValue) {
		URL_STORE_SEARCH=URL_HOST+"/resto/api/corporation/stores/search?"+filterField+"="+filterValue+"&key=";
		return URL_STORE_SEARCH;
	}

	public static String makeURL_PRODUCT_SEARCH(String filterField,String filterValue) {
		URL_PRODUCT_SEARCH=URL_HOST+"/resto/api/corporation/products/search?"+filterField+"="+filterValue+"&key=";
		return URL_PRODUCT_SEARCH;
	}

	/**
	 * get pricelist for items sold by thsi supplier
	 * @param forSupplierCode
	 * @return URL_SUPPLIER_PRICELIST
	 */
	public static String getURL_SUPPLIER_PRICELIST(String forSupplierCode) {
	
		URL_SUPPLIER_PRICELIST=URL_HOST+"/resto/api/suppliers/"+forSupplierCode+"/pricelist?key=";
		
		return URL_SUPPLIER_PRICELIST;
	}
	
	public static String makeURL_SUPPLIER_PRICELIST(String forSupplierCode,String filterField,String filterValue) {
		if(URL_SUPPLIER_PRICELIST==null)
			URL_SUPPLIER_PRICELIST=URL_HOST+"/resto/api/suppliers/"+forSupplierCode+"/pricelist?key=";
		else
			URL_SUPPLIER_PRICELIST=URL_SUPPLIER_PRICELIST+"&"+filterField+"="+filterValue;
		return URL_SUPPLIER_PRICELIST;
	}
	
	public static String makeURL_SUPPLIERS_SEARCH(String filterField,String filterValue) {
		if(URL_SUPPLIERS_SEARCH==null)
			URL_SUPPLIERS_SEARCH=URL_HOST+"/resto/api/suppliers/search?"+filterField+"="+filterValue+"&key=";
		else
			URL_SUPPLIERS_SEARCH=URL_SUPPLIERS_SEARCH+"&"+filterField+"="+filterValue;
		return URL_SUPPLIERS_SEARCH;
	}
	
	public static String makeURL_EVENT_SEARCH(String filterField,String filterValue) {
		if(URL_EVENT_SEARCH==null)
			URL_EVENT_SEARCH=URL_HOST+"/resto/api/events/search?"+filterField+"="+filterValue+"&key=";
		else
			URL_EVENT_SEARCH=URL_EVENT_SEARCH+"&"+filterField+"="+filterValue;
		return URL_EVENT_SEARCH;
	}
	
	public static String makeURL_EDI_ORDERS(String filterField,String filterValue) {
		if(URL_EDI_ORDERS==null)
			URL_EDI_ORDERS=URL_HOST+"/resto/api/edi/709f5a71-47b6-f2fc-b5a8-d10176a851d7/orders/list?"+filterField+"="+filterValue+"&key=";
		else
			URL_EDI_ORDERS=URL_EDI_ORDERS+"&"+filterField+"="+filterValue;
		return URL_EDI_ORDERS;
	}
}

