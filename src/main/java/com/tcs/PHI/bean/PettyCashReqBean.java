/* Copyright (C) Tata Consultancy Services Pvt. Ltd. - All Rights Reserved
 * This file is part of TCS-PHI project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Written by Abhik Chakraborty<chakraborty.abhik@tcs.com>, November 2017.
 * 
 */
package com.tcs.PHI.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "financialCode",
    "storeCode",
    "totalAmount",
    "totalTransaction",
    "tranDate"
})
public class PettyCashReqBean {

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

	/**
	 * @param financialCode
	 * @param storeCode
	 * @param totalAmount
	 * @param totalTransaction
	 * @param tranDate
	 */
	
	public PettyCashReqBean(int financialCode, String storeCode, BigDecimal totalAmount, int totalTransaction,
			String tranDate) {
		super();
		this.financialCode = financialCode;
		this.storeCode = storeCode;
		this.totalAmount = totalAmount;
		this.totalTransaction = totalTransaction;
		this.tranDate = tranDate;
	}

	public int getFinancialCode() {
		return financialCode;
	}

	public void setFinancialCode(int financialCode) {
		this.financialCode = financialCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(int totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	
	@Override
	public String toString() {
		return "PettyCashReqBean [financialCode=" + financialCode + ", storeCode=" + storeCode + ", totalAmount="
				+ totalAmount + ", totalTransaction=" + totalTransaction + ", tranDate=" + tranDate + "]";
	}
}
