package snippet;

public class Snippet {
	
	@SuppressWarnings({ "unchecked", "unused" })
	private void doGET(String url)
	{
	    //String theUrl = "http://129.126.152.23:11001/dc-api/sdc/lookup/brands";
	    RestTemplate restTemplate = new RestTemplate();
	    XmlMapper xmlMapper=new XmlMapper();
		xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));
		
	    try {
	        HttpHeaders headers = createHttpGetHeaders("IIKO","N2Ka5,@:$9Phx+B3");
	        log.info(headers.toString());
	        HttpEntity<String> entity = new HttpEntity<String>(headers);
	        log.debug("Before firing GET");
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        responseXml = (HashMap<String, Object>) xmlMapper.readValue(response.getBody().toString(),Object.class);
	        log.debug("After getting response");
	        
	        log.info("Result - status ("+ response.getStatusCode() + ") has body: " + 
	        			new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(responseXml));
	    }
	    
	    catch (Exception eek) {
	        log.error("** Exception: "+ eek.getMessage());
	    }
	}
	
	
	public String postFinancial(String url,FinDataReqBean requestBean) {
		
			
			RestTemplate restTemplate = new RestTemplate();
			
			/*XmlMapper xmlMapper=new XmlMapper();
			xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class,new FixedUntypedXMLDeserializer()));*/
			
	        HttpHeaders headers = createHttpPostHeaders("IIKO","N2Ka5,@:$9Phx+B3");
	        HttpEntity<FinDataReqBean> reqEntity = new HttpEntity<FinDataReqBean>(requestBean,headers);
	        ResponseEntity<SgResBean> resEntity;
	        
	        log.debug("Before firing POST");
	        
				//resEntity = restTemplate.postForEntity(url, reqEntity, FinDataResBean.class);
	        		//answer1= restTemplate.postForObject(url, reqEntity1, String.class);
	        		resEntity= restTemplate.postForEntity(url, reqEntity, SgResBean.class);
	        		
	        
	        
			return resEntity.getStatusCode().toString();
	}
	
	
	public String postHrlySales(String url,HrlySalesReqBean requestBean) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = createHttpPostHeaders("IIKO","N2Ka5,@:$9Phx+B3");
	    HttpEntity<HrlySalesReqBean> reqEntity = new HttpEntity<HrlySalesReqBean>(requestBean,headers);
	    ResponseEntity<Object> resEntity;
	    
				
	        		resEntity= restTemplate.postForEntity(url, reqEntity, Object.class);
	        		
	        
	    return resEntity.getStatusCode().toString();
		
	}
	
	
	/*public String firePOST(String url) {
		
		String res=null;
		RestTemplate restTemplate = new RestTemplate();
		
		 HttpHeaders headers = createHttpPostHeaders("IIKO","N2Ka5,@:$9Phx+B3");
		 
		 int financialCode = 14;
	     String storeCode = "345"; 
	     BigDecimal totalAmount = new BigDecimal("102.50");
	     int totalTransaction = 12; 
	     String tranDate = "2017-11-08T00:00:00.000Z";
	     
	     String requestJson = "{\"financialCode\":"+financialCode+",\"storeCode\":"+"\""+"345"+"\""+",\"totalAmount\":"+totalAmount+","
	        		+ "\"totalTransaction\":"+totalTransaction+",\"tranDate\":"+"\""+tranDate+"\""+"}";
	        		
	      FinDataResBean resBean = new FinDataResBean();
		 
		 FinDataReqBean req = new FinDataReqBean(8,"345",new BigDecimal("102.50"),12,"2017-11-08T00:00:00.000Z");
		 
		 
		 HttpEntity<FinDataReqBean> reqEntity = new HttpEntity<FinDataReqBean>(req,headers);
		 ResponseEntity<FinDataResBean> resEntity;
		 String answer=null;
		 try {
			 	resEntity = restTemplate.postForEntity(url, reqEntity, FinDataResBean.class);
	        		//answer= restTemplate.postForObject(url, reqEntity, String.class);
	        		//resBean= restTemplate.postForObject(url, reqEntity, resBean.getClass());
	        		
			} catch (HttpClientErrorException hce) {
				throw new MyRestCallException("Api call exeception occured with following details:\n",
						hce.getStatusCode(), hce.getStatusText(), hce.getResponseBodyAsByteArray(), Charset.forName("UTF-8"));
			}
		 
		 //return resBean.getStatus().get("code");
		return resEntity.getStatusCode().toString();
	}*/
	
	/*public static void main(String args[]) {
		SDCApiHandler handler = new SDCApiHandler();
		handler.firePOST(handler.makeApiUrl("sdc/financial/data"));
	}*/
	
}

