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
@JsonPropertyOrder({ "brandCode", "storeCode", "salesTypeCode", "tranDate", "tranStartTime", "tranEndTime",
		"paidTranCount", "netSales" })
public class HrlySalesReqBean {

	@JsonProperty("brandCode")
	private String brandCode = new String(new char[10]);;
	@JsonProperty("storeCode")
	private String storeCode = new String(new char[7]);;
	@JsonProperty("salesTypeCode")
	private int salesTypeCode;
	@JsonProperty("tranDate")
	private String tranDate = new String(new char[24]);;
	@JsonProperty("tranStartTime")
	private String tranStartTime = new String(new char[8]);;
	@JsonProperty("tranEndTime")
	private String tranEndTime = new String(new char[8]);;
	@JsonProperty("paidTranCount")
	private int paidTranCount;
	@JsonProperty("netSales")
	private BigDecimal netSales;

	/**
	 * @param storeCode
	 * @param salesTypeCode
	 * @param tranDate
	 * @param tranStartTime
	 * @param tranEndTime
	 * @param paidTranCount
	 * @param netSales
	 */
	public HrlySalesReqBean(String brandCode, String storeCode, int salesTypeCode, String tranDate,
			String tranStartTime, String tranEndTime, int paidTranCount, BigDecimal netSales) {
		super();
		this.brandCode = brandCode;
		this.storeCode = storeCode;
		this.salesTypeCode = salesTypeCode;
		this.tranDate = tranDate;
		this.tranStartTime = tranStartTime;
		this.tranEndTime = tranEndTime;
		this.paidTranCount = paidTranCount;
		this.netSales = netSales;
	}

	public HrlySalesReqBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "HrlySalesReqBean [brandCode=" + brandCode + ", storeCode=" + storeCode + ", salesTypeCode="
				+ salesTypeCode + ", tranDate=" + tranDate + ", tranStartTime=" + tranStartTime + ", tranEndTime="
				+ tranEndTime + ", paidTranCount=" + paidTranCount + ", netSales=" + netSales + "]";
	}

	@JsonProperty("brandCode")
	public String getBrandCode(String brandCode) {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public int getSalesTypeCode() {
		return salesTypeCode;
	}

	public void setSalesTypeCode(int salesTypeCode) {
		this.salesTypeCode = salesTypeCode;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranStartTime() {
		return tranStartTime;
	}

	public void setTranStartTime(String tranStartTime) {
		this.tranStartTime = tranStartTime;
	}

	public String getTranEndTime() {
		return tranEndTime;
	}

	public void setTranEndTime(String tranEndTime) {
		this.tranEndTime = tranEndTime;
	}

	public int getPaidTranCount() {
		return paidTranCount;
	}

	public void setPaidTranCount(int paidTranCount) {
		this.paidTranCount = paidTranCount;
	}

	public BigDecimal getNetSales() {
		return netSales;
	}

	public void setNetSales(BigDecimal netSales) {
		this.netSales = netSales;
	}

}
