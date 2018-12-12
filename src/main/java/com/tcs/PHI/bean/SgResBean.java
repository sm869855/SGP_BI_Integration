package com.tcs.PHI.bean;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SgResBean {
	
	private static final Logger log = LoggerFactory.getLogger(SgResBean.class);
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonPropertyOrder({
	    "status",
	    "data",
	    "errors"
	})
	

	    @JsonProperty("status")
	    private HashMap<String,String> status = new HashMap<String,String>();
	    @JsonProperty("data")
	    private HashMap<String,String> data = new HashMap<String,String>();
	    @JsonProperty("errors")
	    private ArrayList<HashMap<String,String>> errors = new ArrayList<HashMap<String,String>>();
	  
	    
	    
	    public SgResBean() {
	    }


		/**
		 * 
		 * @param status
		 * @param data
		 * @param errors
		 */
		public SgResBean(HashMap<String, String> status, HashMap<String, String> data, ArrayList<HashMap<String, String>> errors) {
			super();
			this.status = status;
			this.data = data;
			this.errors = errors;
		}

		

		public ArrayList<HashMap<String, String>> getErrors() {
			return errors;
		}


		public void setErrors(ArrayList<HashMap<String, String>> errors) {
			this.errors = errors;
		}


		public HashMap<String, String> getStatus() {
			return status;
		}


		public void setStatus(HashMap<String, String> status) {
			this.status = status;
		}


		public HashMap<String, String> getData() {
			return data;
		}


		public void setData(HashMap<String, String> data) {
			this.data = data;
		}
	    
	    @Override
	    public String toString() {
	    		String response=null;
	    		try {
	    			if(!this.data.isEmpty()){
	    				response = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(getData());
	    			}else if(this.errors==null && this.data!=null){
	    				response = "Something wrong in request field values eg. timestamp doesn't have Z in the end (something i faced)";    			
	    			}else if(!this.errors.isEmpty()){
	    				log.debug("boom");
	    				for(HashMap<String,String> error:getErrors())
	    				response = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(error);
	    			}
				} catch (JsonProcessingException e) {
					log.error(e.getMessage()); 
				}
	    		return response;
	    }

}
