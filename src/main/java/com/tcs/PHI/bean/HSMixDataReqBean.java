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
@JsonPropertyOrder({ "brandCode", "storeCode", "salesTypeCode", "tranDate", "tranStartTime", "tranEndTime", "itemType",
		"itemCode", "totalQuantity", "totalAmount" })
public class HSMixDataReqBean {

	@JsonProperty("brandCode")
	private String brandCode = new String(new char[10]);;
	@JsonProperty("storeCode")
	private String storeCode = new String(new char[7]);
	@JsonProperty("salesTypeCode")
	private int salesTypeCode;
	@JsonProperty("tranDate")
	private String tranDate = new String(new char[24]);
	@JsonProperty("tranStartTime")
	private String tranStartTime = new String(new char[8]);
	@JsonProperty("tranEndTime")
	private String tranEndTime = new String(new char[8]);
	@JsonProperty("itemType")
	private String itemType = new String(new char[10]);
	@JsonProperty("itemCode")
	private String itemCode = new String(new char[500]);
	@JsonProperty("totalQuantity")
	private int totalQuantity;
	@JsonProperty("totalAmount")
	private BigDecimal totalAmount;

	/**
	 * @param brandCode
	 * @param storeCode
	 * @param salesTypeCode
	 * @param tranDate
	 * @param tranStartTime
	 * @param tranEndTime
	 * @param itemType
	 * @param itemCode
	 * @param totalQuantity
	 * @param totalAmount
	 */
	public HSMixDataReqBean(String brandCode, String storeCode, int salesTypeCode, String tranDate,
			String tranStartTime, String tranEndTime, String itemType, String itemCode, int totalQuantity,
			BigDecimal totalAmount) {
		super();
		this.brandCode = brandCode;
		this.storeCode = storeCode;
		this.salesTypeCode = salesTypeCode;
		this.tranDate = tranDate;
		this.tranStartTime = tranStartTime;
		this.tranEndTime = tranEndTime;
		this.itemType = itemType;
		this.itemCode = itemCode;
		this.totalQuantity = totalQuantity;
		this.totalAmount = totalAmount;
	}

	@JsonProperty("brandCode")
	public String getBrandCode() {
		return brandCode;
	}

	@JsonProperty("brandCode")
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@JsonProperty("storeCode")
	public String getStoreCode() {
		return storeCode;
	}

	@JsonProperty("storeCode")
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@JsonProperty("salesTypeCode")
	public int getSalesTypeCode() {
		return salesTypeCode;
	}

	@JsonProperty("salesTypeCode")
	public void setSalesTypeCode(int salesTypeCode) {
		this.salesTypeCode = salesTypeCode;
	}

	@JsonProperty("tranDate")
	public String getTranDate() {
		return tranDate;
	}

	@JsonProperty("tranDate")
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	@JsonProperty("tranStartTime")
	public String getTranStartTime() {
		return tranStartTime;
	}

	@JsonProperty("tranStartTime")
	public void setTranStartTime(String str) {
		this.tranStartTime = str;
	}

	@JsonProperty("tranEndTime")
	public String getTranEndTime() {
		return tranEndTime;
	}

	@JsonProperty("tranEndTime")
	public void setTranEndTime(String str) {
		this.tranEndTime = str;
	}

	@JsonProperty("itemType")
	public String getItemType() {
		return itemType;
	}

	@JsonProperty("itemType")
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@JsonProperty("itemCode")
	public String getItemCode() {
		return itemCode;
	}

	@JsonProperty("itemCode")
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@JsonProperty("totalQuantity")
	public int getTotalQuantity() {
		return totalQuantity;
	}

	@JsonProperty("totalQuantity")
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	@JsonProperty("totalAmount")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	@JsonProperty("totalAmount")
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "HSMixDataReqBean [brandCode=" + brandCode + ", storeCode=" + storeCode + ", salesTypeCode="
				+ salesTypeCode + ", tranDate=" + tranDate + ", tranStartTime=" + tranStartTime + ", tranEndTime="
				+ tranEndTime + ", itemType=" + itemType + ", itemCode=" + itemCode + ", totalQuantity=" + totalQuantity
				+ ", totalAmount=" + totalAmount + "]";
	}

}
