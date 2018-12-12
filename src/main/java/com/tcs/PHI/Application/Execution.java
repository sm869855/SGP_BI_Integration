package com.tcs.PHI.Application;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.PHI.APIhandlerService.ServiceBean;
import com.tcs.PHI.SGPApiAccess.SGApiHandler;
import com.tcs.PHI.bean.SgResBean;
import com.tcs.PHI.service.Service;

public class Execution {
	
	/*public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper= new ObjectMapper();
		SgResBean s= mapper.readValue(new File(
				"D:\\s_workspace\\SGP_BiGeneration\\src\\main\\resources\\s.json")
					,new TypeReference<SgResBean>() {
			});
		System.out.println(s.toString());
	}*/
}
