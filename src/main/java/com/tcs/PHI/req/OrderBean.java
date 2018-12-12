package com.tcs.PHI.req;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderBean {
	

	
	private String number;
	
	private long orderNumber ;
	
	private String orderDate;
	
	private String sellerCode;
	
	private String sellerName;
	
	private String sellerGln;
	
	private String sellerAddress;
	
	private String sellerTelephone;
	
	private String sellerFAX;
	
	private String sellerEmail;
	
	private String buyerId;
	
	private String buyerGln;
	
	private String buyerCode;
	
	private  String buyerName;
	
	private String storeName ;
	
	private String storeCode ;
	
	private String deliveryDate;
		
	private String storeManager;
	
	private ArrayList<Item> items= new ArrayList<Item>();
	
	private BigDecimal itemsTotalPrice; 
	
	public OrderBean(){}
	/*
	 * parameterized constructor
	 * @param String store code
	 */
	public OrderBean(String storeCode){
		this.storeCode=storeCode;
		//System.out.println(this.storeCode);
	}
	
	
	
	public String getNumber() {
		return number;
	}




	public void setNumber(String number) {
		this.number = number;
	}




	public long getOrderNumber() {
		return orderNumber;
	}




	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public void setOrderNumberForIDN(/*String number*/) {
		//number=this.getNumber();
		this.orderNumber=Integer.parseInt((this.getNumber()).split(this.getStoreCode())[1]);
	}
	


	public String getOrderDate() {
		return orderDate;
	}




	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}




	public String getSellerName() {
		return sellerName;
	}




	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}




	public String getSellerGln() {
		return sellerGln;
	}




	public void setSellerGln(String sellerGln) {
		this.sellerGln = sellerGln;
	}




	public String getSellerAddress() {
		return sellerAddress;
	}




	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}




	public String getBuyerId() {
		return buyerId;
	}




	public String getSellerCode() {
		return sellerCode;
	}




	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}




	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}




	public String getBuyerGln() {
		return buyerGln;
	}




	public void setBuyerGln(String buyerGln) {
		this.buyerGln = buyerGln;
	}




	public String getBuyerCode() {
		return buyerCode;
	}




	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}




	public String getBuyerName() {
		return buyerName;
	}




	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}




	public String getSellerEmail() {
		return sellerEmail;
	}




	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}




	public String getStoreName() {
		return storeName;
	}




	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}




	public String getStoreCode() {
		return storeCode;
	}




	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}




	public String getDeliveryDate() {
		return deliveryDate;
	}




	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}




	public String getStoreManager() {
		return storeManager;
	}




	public void setStoreManager(String storeManager) {
		this.storeManager = storeManager;
	}




	public ArrayList<Item> getItems() {
		return items;
	}




	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}




	public String getSellerTelephone() {
		return sellerTelephone;
	}




	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}


	
	public String getSellerFAX() {
		return sellerFAX;
	}




	public void setSellerFAX(String sellerFAX) {
		this.sellerFAX = sellerFAX;
	}

	public BigDecimal getItemsTotalPrice() {
		return itemsTotalPrice;
	}
	
	public void setItemsTotalPrice(BigDecimal itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}

	public String toString(){
		
		StringBuilder s= new StringBuilder(this.getStoreCode()+","+this.getStoreName()+","+this.getSellerCode()+","+this.getSellerName());
		for(Item i:this.getItems()){
			s.append("\t"+i.toString());
		}
		return s.toString();
	}

	public static class Item{
	
	private int sequenceNum;
	private String itemId;
	private String itemName;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemTotalPrice;
	private String itemQuantity;
	private String itemUnit;
	private String itemUnitCode;
	private String itemContainerUnit;
	private String itemContainerUnitCode;
	private String itemContainerQuantity;
	private String itemPerContainerQuantity;
	
	public int getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public String getItemUnitCode() {
		return itemUnitCode;
	}
	public void setItemUnitCode(String propertyFileToFetchUnitCodes) throws IOException {
		Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(propertyFileToFetchUnitCodes));
		if(props.getProperty(this.itemUnit)!=null)
			this.itemUnitCode =props.getProperty(this.itemUnit);
		else
			this.itemUnitCode ="1";
	}
	public String getItemContainerUnit() {
		return itemContainerUnit;
	}
	public void setItemContainerUnit(String itemContainerUnit) {
		this.itemContainerUnit = itemContainerUnit;
	}
	public String getItemContainerUnitCode() {
		return itemContainerUnitCode;
	}
	public void setItemContainerUnitCode(String propertyFileToFetchUnitCodes) throws IOException {
		Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(propertyFileToFetchUnitCodes));
		this.itemContainerUnitCode = props.getProperty(this.itemContainerUnit);
	}
	public String getItemContainerQuantity() {
		return itemContainerQuantity;
	}
	public void setItemContainerQuantity(String itemContainerQuantity) {
		this.itemContainerQuantity = itemContainerQuantity;
	}
	public BigDecimal getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(BigDecimal itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	public String getItemPerContainerQuantity() {
		return itemPerContainerQuantity;
	}
	public void setItemPerContainerQuantity(String itemPerContainerQuantity) {
		this.itemPerContainerQuantity = itemPerContainerQuantity;
	}
	public String toString(){
		
		return (this.getItemId()+","+this.getItemQuantity()+","+this.getItemUnit()+"\t");
	}
	
	}

}
