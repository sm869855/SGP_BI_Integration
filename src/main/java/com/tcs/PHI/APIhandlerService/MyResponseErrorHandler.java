package com.tcs.PHI.APIhandlerService;

import java.io.IOException;
import java.util.Scanner;

import javax.naming.AuthenticationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class MyResponseErrorHandler  extends DefaultResponseErrorHandler implements ResponseErrorHandler{

		private static final Logger logger = LoggerFactory.getLogger(MyResponseErrorHandler.class);
		@Override
		public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {

		    if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
		        logger.debug(HttpStatus.FORBIDDEN + " response. Exception thrown for wrong semantics/other issues.");
		        try {
					throw new AuthenticationException();
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
				}
		    }
		    else if (clienthttpresponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
		        logger.debug(HttpStatus.BAD_REQUEST + " response. Exception thrown for wrong semantics/illegalArgument issues.");
		        
		        try {
					throw new AuthenticationException();
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
				}
		    }
		    else if (clienthttpresponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
		        logger.debug(HttpStatus.UNAUTHORIZED + " response. Exception thrown for unauthhorised access.");
		        try {
					throw new AuthenticationException();
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
				}
		    }
		    else if (clienthttpresponse.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
		        logger.debug(HttpStatus.REQUEST_TIMEOUT + " response. Exception thrown for timeout.");
		        try {
					throw new AuthenticationException();
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
				}
		    }
		    else if (clienthttpresponse.getStatusCode() == HttpStatus.NOT_FOUND) {
		        logger.debug(HttpStatus.NOT_FOUND + " response. Exception thrown for wrong/not found url/request.");
		        try {
					throw new AuthenticationException();
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
				}
		    }
		}

		@Override
		public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

		    if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
		        logger.debug("Status code: " + clienthttpresponse.getStatusCode());
		        logger.debug("Response" + clienthttpresponse.getStatusText());
		        //logger.debug(getResponseBody(clienthttpresponse));
		        if (clienthttpresponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
		            logger.debug("Call returned a error 400 forbidden resposne ");
		            return true;
		        }
		        else if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
		            logger.debug("Call returned a error 403 forbidden resposne ");
		            return true;
		        }
		        else if (clienthttpresponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
		            logger.debug("Call returned a error 401 Unauthorized resposne ");
		            return true;
		        }
		        else if (clienthttpresponse.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
		            logger.debug("Call returned a error 404 Not Found ");
		            return true;
		        }
		        else if (clienthttpresponse.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
		            logger.debug("Call returned a error 408 Request Timeout resposne ");
		            return true;
		        }
		    }
		    return false;
		}
}
