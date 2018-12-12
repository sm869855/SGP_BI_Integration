package com.tcs.PHI.SGPApiAccess;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class SGPApiConfig {
	
	private Properties props;
	public static String _ID;
	public static String _PWD;
	public static String _HOST;
	public static String _COUNTRY;
	public static String URL_FINANCIAL ;
	public static String URL_HSMIX;
	public static String URL_HrlySales;
	public static String URL_PettyCash;
	

	public SGPApiConfig(String configFilePath) {

		try {
			props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(configFilePath));
		} catch (IOException e) {
			
		}
		_ID=props.getProperty("id");
		_PWD=props.getProperty("pwd");
		_HOST = props.getProperty("host");
		_COUNTRY = props.getProperty("country");
		URL_FINANCIAL=_HOST+"/sdc/financial/data";
		URL_HSMIX = _HOST + "/sdc/sales/mix/hourly/data";
		URL_HrlySales=_HOST +"/sdc/sales/hourly/data";
		URL_PettyCash=_HOST +"/sdc/petty-cash/data";
		
	}
}
