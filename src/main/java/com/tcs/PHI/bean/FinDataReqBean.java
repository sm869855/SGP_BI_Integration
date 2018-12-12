/* Copyright (C) Tata Consultancy Services Pvt. Ltd. - All Rights Reserved
 * This file is part of TCS-PHI project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Written by Subhankar Maitra<maitra.subhankar@tcs.com>, November 2017.
 * 
 */


package com.tcs.PHI.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "financialCode",
    "storeCode",
    "totalAmount",
    "totalTransaction",
    "tranDate"
})
public class FinDataReqBean {
	
	@JsonProperty("financialCode")
	private int financialCode;
	@JsonProperty("storeCode")
	private String storeCode;
	@JsonProperty("totalAmount")
	private BigDecimal totalAmount;
	@JsonProperty("totalTransaction")
	private int totalTransaction;
	@JsonProperty("tranDate")
	private String tranDate;

	public FinDataReqBean(){}
	/**
	 * @param financialCode
	 * @param storeCode
	 * @param totalAmount
	 * @param totalTransaction
	 * @param tranDate
	 */
	public FinDataReqBean(int financialCode, String storeCode, BigDecimal totalAmount, int totalTransaction,
			String tranDate) {
		super();
		this.financialCode = financialCode;
		this.storeCode = storeCode;
		//DecimalFormat df = new DecimalFormat("##############.00");
		this.totalAmount = totalAmount;
		this.totalTransaction = totalTransaction;
		this.tranDate = tranDate;
	}
	public void addData(int financialCode, String storeCode, String totalAmount, int totalTransaction,String tranDate){
		this.financialCode = financialCode;
		this.storeCode = storeCode;
		//DecimalFormat df = new DecimalFormat("##############.00");
		this.totalAmount = new BigDecimal(totalAmount);
		this.totalTransaction = totalTransaction;
		this.tranDate = tranDate;
	}
	@JsonProperty("financialCode")
	public int getFinancialCode() {
		return financialCode;
	}
	
	@JsonProperty("financialCode")
	public void setFinancialCode(int financialCode) {
		this.financialCode = financialCode;
	}
	
	@JsonProperty("storeCode")
	public String getStoreCode() {
		return storeCode;
	}
	
	@JsonProperty("storeCode")
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	@JsonProperty("totalAmount")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	@JsonProperty("totalAmount")
	public void setTotalAmount(BigDecimal totalamount) {
		this.totalAmount = totalamount;
	}
	/*public void setTotalAmount(String totalAmount) {
		String pattern = "##############.0000";
		BigDecimal decimal = new BigDecimal(totalAmount);
		this.totalAmount = new DecimalFormat(pattern).format(decimal);
	}*/
	
	@JsonProperty("totalTransaction")
	public int getTotalTransaction() {
		return totalTransaction;
	}
	
	@JsonProperty("totalTransaction")
	public void setTotalTransaction(int totalTransaction) {
		this.totalTransaction = totalTransaction;
	}
	
	@JsonProperty("tranDate")
	public String getTranDate() {
		return tranDate;
	}
	
	@JsonProperty("tranDate")
	public void setTranDate(LocalDateTime tranDate) {
		this.tranDate = tranDate.toString();
	}

	@Override
	public String toString() {
		return "{financialCode:" + financialCode + ", storeCode:" + storeCode + ", totalAmount:"
				+ totalAmount + ", totalTransaction:" + totalTransaction + ", tranDate:" + tranDate+"}";
		//return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString();
	}
	
	/*
	 * String pattern = "##############.0000";
		BigDecimal decimal = new BigDecimal(finAmount);
		this.finAmount = new DecimalFormat(pattern).format(decimal);
	 */
	

}
