
package com.tcs.PHI.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tranDate",
    "brandCode",
    "storeCode",
    "skuCode",
    "skuDesc",
    "tranType",
    "supplierCode",
    "purchaseCount",
    "inventoryCount",
    "tranPurchaseUnitCost",
    "tranInventoryUnitCost",
    "tranPurchaseAmount",
    "tranInventoryAmount"
})
public class InventoryTranxReqBean implements Serializable {
	
	  private final static long serialVersionUID = -1L;

    @JsonProperty("tranDate")
    private String tranDate;
    @JsonProperty("brandCode")
    private String brandCode;
    @JsonProperty("storeCode")
    private String storeCode;
    @JsonProperty("skuCode")
    private String skuCode;
    @JsonProperty("skuDesc")
    private String skuDesc;
    @JsonProperty("tranType")
    private String tranType;
    @JsonProperty("supplierCode")
    private String supplierCode;
    @JsonProperty("purchaseCount")
    private Double purchaseCount;
    @JsonProperty("inventoryCount")
    private Double inventoryCount;
    @JsonProperty("tranPurchaseUnitCost")
    private Double tranPurchaseUnitCost;
    @JsonProperty("tranInventoryUnitCost")
    private Double tranInventoryUnitCost;
    @JsonProperty("tranPurchaseAmount")
    private Double tranPurchaseAmount;
    @JsonProperty("tranInventoryAmount")
    private Double tranInventoryAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public InventoryTranxReqBean() {
    }

    /**
     * 
     * @param tranDate
     * @param purchaseCount
     * @param tranInventoryUnitCost
     * @param tranPurchaseUnitCost
     * @param tranInventoryAmount
     * @param supplierCode
     * @param tranType
     * @param skuCode
     * @param inventoryCount
     * @param skuDesc
     * @param tranPurchaseAmount
     * @param brandCode
     * @param storeCode
     */
    public InventoryTranxReqBean(String tranDate, String brandCode, String storeCode, String skuCode, String skuDesc, String tranType, String supplierCode, Double purchaseCount, Double inventoryCount, Double tranPurchaseUnitCost, Double tranInventoryUnitCost, Double tranPurchaseAmount, Double tranInventoryAmount) {
        super();
        this.tranDate = tranDate;
        this.brandCode = brandCode;
        this.storeCode = storeCode;
        this.skuCode = skuCode;
        this.skuDesc = skuDesc;
        this.tranType = tranType;
        this.supplierCode = supplierCode;
        this.purchaseCount = purchaseCount;
        this.inventoryCount = inventoryCount;
        this.tranPurchaseUnitCost = tranPurchaseUnitCost;
        this.tranInventoryUnitCost = tranInventoryUnitCost;
        this.tranPurchaseAmount = tranPurchaseAmount;
        this.tranInventoryAmount = tranInventoryAmount;
    }

    @JsonProperty("tranDate")
    public String getTranDate() {
        return tranDate;
    }

    @JsonProperty("tranDate")
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
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

    @JsonProperty("skuCode")
    public String getSkuCode() {
        return skuCode;
    }

    @JsonProperty("skuCode")
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @JsonProperty("skuDesc")
    public String getSkuDesc() {
        return skuDesc;
    }

    @JsonProperty("skuDesc")
    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    @JsonProperty("tranType")
    public String getTranType() {
        return tranType;
    }

    @JsonProperty("tranType")
    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @JsonProperty("supplierCode")
    public String getSupplierCode() {
        return supplierCode;
    }

    @JsonProperty("supplierCode")
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @JsonProperty("purchaseCount")
    public Double getPurchaseCount() {
        return purchaseCount;
    }

    @JsonProperty("purchaseCount")
    public void setPurchaseCount(Double purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    @JsonProperty("inventoryCount")
    public Double getInventoryCount() {
        return inventoryCount;
    }

    @JsonProperty("inventoryCount")
    public void setInventoryCount(Double inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @JsonProperty("tranPurchaseUnitCost")
    public Double getTranPurchaseUnitCost() {
        return tranPurchaseUnitCost;
    }

    @JsonProperty("tranPurchaseUnitCost")
    public void setTranPurchaseUnitCost(Double tranPurchaseUnitCost) {
        this.tranPurchaseUnitCost = tranPurchaseUnitCost;
    }

    @JsonProperty("tranInventoryUnitCost")
    public Double getTranInventoryUnitCost() {
        return tranInventoryUnitCost;
    }

    @JsonProperty("tranInventoryUnitCost")
    public void setTranInventoryUnitCost(Double tranInventoryUnitCost) {
        this.tranInventoryUnitCost = tranInventoryUnitCost;
    }

    @JsonProperty("tranPurchaseAmount")
    public Double getTranPurchaseAmount() {
        return tranPurchaseAmount;
    }

    @JsonProperty("tranPurchaseAmount")
    public void setTranPurchaseAmount(Double tranPurchaseAmount) {
        this.tranPurchaseAmount = tranPurchaseAmount;
    }

    @JsonProperty("tranInventoryAmount")
    public Double getTranInventoryAmount() {
        return tranInventoryAmount;
    }

    @JsonProperty("tranInventoryAmount")
    public void setTranInventoryAmount(Double tranInventoryAmount) {
        this.tranInventoryAmount = tranInventoryAmount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
