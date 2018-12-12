package com.tcs.PHI.res;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.tcs.PHI.req.ReqBean;

@SuppressWarnings("deprecation")
public class FixedUntypedXMLDeserializer extends UntypedObjectDeserializer {
	
	private static final Logger log= LoggerFactory.getLogger(FixedUntypedXMLDeserializer.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object mapObject(JsonParser parser,DeserializationContext ctx) throws IOException{
	String firstKey;
	String nextKey;
	LinkedHashMap<String, Object> resultMap= new LinkedHashMap<String, Object>();
	JsonToken token= parser.currentToken();
	
	/*
	 * ****************take the field name in @param -firstKey if object starting or field name tag found,null otherwise**************************
	 */
	if(token==JsonToken.START_OBJECT)
		firstKey= parser.nextFieldName();
	else if(token==JsonToken.FIELD_NAME)
		firstKey=parser.getCurrentName();
	else {
		if (token!=JsonToken.END_OBJECT)
			throw ctx.mappingException(handledType(), parser.currentToken());
		firstKey=null;
	}
	
	/*
	 * ***********************returning the resulting Map with data****************************
	 */
	if(firstKey==null)
		return resultMap;
	
	/*
	 * ***********************moving on to next token and adding to map that contains data*************************
	 */
	parser.nextToken();
	resultMap.put(firstKey, deserialize(parser, ctx));
	
	
	Set<String> listKeys= new LinkedHashSet<String>();	
	/*
	 * ***********************if next Field name (@param-nextKey) is not null go to next token***************************
	 */
	while((nextKey=parser.nextFieldName()) != null){
		parser.nextToken();
	/*
	 * ***********************if the map containing data(resultMap) has the field name already/one field has multiple values, add it in list of objects***********************
	 * ***********************then put the next field name and the values into the resulting map********************************
	 */
		if(resultMap.containsKey(nextKey)){
			Object listOfObject= resultMap.get(nextKey);
			if(!(listOfObject instanceof List)){
				listOfObject= new ArrayList<>();
				((List) listOfObject).add(resultMap.get(nextKey));
				resultMap.put(nextKey, listOfObject);
			}
			((List) listOfObject).add(deserialize(parser, ctx));
			/*
			 * *************************adding next key with multiple values to list of keys*****************************
			 */
			listKeys.add(nextKey);
			
		}else{
			/*
			 * *************************if the next key doesn't have multiple values, deserialize normally****************************
			 */
			resultMap.put(nextKey, deserialize(parser, ctx));
		}
		
		
	}
		
	
	
	return resultMap;
	

}
}