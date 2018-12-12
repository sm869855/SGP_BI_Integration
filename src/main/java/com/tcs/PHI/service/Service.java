package com.tcs.PHI.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tcs.PHI.bean.FinDataReqBean;
import com.tcs.PHI.bean.HSMixDataReqBean;
import com.tcs.PHI.bean.HrlySalesReqBean;
import com.tcs.PHI.bean.InventoryTranxReqBean;
import com.tcs.PHI.bean.PettyCashReqBean;
import com.tcs.PHI.iikoApiAccess.IIikoApiHandler;
import com.tcs.PHI.res.ResBean;


public class Service {

	private static final Logger log = LoggerFactory.getLogger(Service.class);
	@Autowired
	IIikoApiHandler iikohandler;
	public List<FinDataReqBean> financialRequests=new ArrayList<FinDataReqBean>();
	public List<InventoryTranxReqBean> invTranxRequests=new ArrayList<InventoryTranxReqBean>();
	public List<HSMixDataReqBean> HSMixRequests=new ArrayList<HSMixDataReqBean>();
	public List<HrlySalesReqBean> HrlySalesRequests=new ArrayList<HrlySalesReqBean>();
	public List<PettyCashReqBean> PettyCashRequests=new ArrayList<PettyCashReqBean>();
	public String startTime,endTime;
	public int count;
	public String storeCode;
	public Service(String iikoConfigFile,String storeCode){
		this.iikohandler= new IIikoApiHandler(iikoConfigFile, storeCode);
		this.financialRequests=new ArrayList<FinDataReqBean>() ;
		this.invTranxRequests=new ArrayList<InventoryTranxReqBean>() ;
		this.storeCode=storeCode;
	}
	
	
public List<FinDataReqBean> makeFinancialRequests(String from ,String to){
	List<ResBean> iikoResponses =iikohandler.handleFinance(from, to);
	
	int financialCode=0;
	BigDecimal totalAmount;
	int totalTransaction;
	String tranDate;
	DecimalFormat df = new DecimalFormat();

	//Read iiko Api responses
	for(ResBean iikoResponse:iikoResponses){
		for(HashMap<String,String> map: iikoResponse.getData()) {
		
		//Logic to set Financial Code
		
		if(map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY") &&
		map.get("Delivery.ServiceType") == null){
			financialCode = 5;
		}else if(map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER") && 
		map.get("Delivery.ServiceType")!=null && map.get("Delivery.ServiceType").equalsIgnoreCase("PICKUP")){
			
			if(null!=map.get("OriginName")) {
				if(map.get("OriginName").equalsIgnoreCase("Call In")) financialCode = 12;
				else if(map.get("OriginName").equalsIgnoreCase("App/Internet")) financialCode = 15;
			}else {
				//financialCode = 0;
			}
			
		}else if(map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER") && 
				map.get("Delivery.ServiceType")!=null && map.get("Delivery.ServiceType").equalsIgnoreCase("COURIER")){
			
			if(null!=map.get("OriginName")) {
				if(map.get("OriginName").equalsIgnoreCase("Call In")) financialCode = 13;
				else if(map.get("OriginName").equalsIgnoreCase("App/Internet")) financialCode = 14;
			}else {
				//financialCode = 0;
			}
			
		}
		else if(map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY") && 
		map.get("Delivery.ServiceType") == null && map.get("Banquet").equalsIgnoreCase("TRUE")){
			financialCode = 8;
		}
		else if(map.get("NonCashPaymentType")!=null && map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE MEAL")){
			//Condition for any other type
			financialCode = 43;
		}
		
		/*
		 * Logic to set Financial data bean
		 */
		
		tranDate = map.get("OpenDate.Typed");
		totalTransaction = Integer.parseInt(map.get("UniqOrderId"));
		df.applyPattern("##############.00");
		double amount = Double.parseDouble(map.get("DishDiscountSumInt.withoutVAT"));
		totalAmount = new BigDecimal(df.format(amount));
		FinDataReqBean request = new FinDataReqBean(financialCode,"345",totalAmount,totalTransaction,tranDate.concat("T00:00:00.000Z"));
		
		/*requestJson = "{\"financialCode\":"+financialCode+",\"storeCode\":"+"\""+"345"+"\""+",\"totalAmount\":"+totalAmount+","
        		+ "\"totalTransaction\":"+totalTransaction+",\"tranDate\":"+"\""+tranDate+"\""+"}";*/
		financialRequests.add(request);
	}
	}
	
    return financialRequests;
}
	public void setHourly(LocalDateTime fromTime,LocalDateTime toTime){
		this.startTime=fromTime.toString();
		this.endTime=toTime.toString();
		this.count+=1;
		
	}

public List<InventoryTranxReqBean> makeInventoryTrnxRequests(String from ,String to){
	
	//InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
	List<ResBean> iikoResponses=iikohandler.handleInventoryTrnxOlapPart(from, to);//itemTrnx specific method call from iiko api
	ResBean iikoSalesResponse=iikoResponses.get(0);
	iikoResponses.remove(0);
	for(HashMap<String, String> map:iikoSalesResponse.getData()){
		if(map.get("NonCashPaymentType")!=null && map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE")){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
			requestBean.setTranType("SA");
		}
	}
	HashMap<String,Object> invStartingBalance=iikohandler.makeInventoryBalanceResponse(from, storeCode);
	HashMap<String,Object> invEndingBalance=iikohandler.makeInventoryBalanceResponse(to, storeCode);
	for(ResBean iikoTrnxResponse:iikoResponses){
	for(HashMap<String, String> map:iikoTrnxResponse.getData()){
		
		boolean supplierPurchase = map.get("TransactionType").equalsIgnoreCase("INVOICE") && map.get("Account.CounteragentType").equalsIgnoreCase("SUPPLIER");
		boolean storePurchase = map.get("TransactionType").equalsIgnoreCase("INVOICE") && map.get("Account.CounteragentType").equalsIgnoreCase("INTERNAL_SUPPLIER");
		boolean standardUsageIn=map.get("TransactionType").equalsIgnoreCase("INVENTORY_CORRECTION") && map.get("Account.CounteragentType").equalsIgnoreCase("NONE");
		boolean standardUsageOut=map.get("TransactionType").equalsIgnoreCase("SESSION_WRITEOFF") && map.get("Account.CounteragentType").equalsIgnoreCase("NONE");
		boolean standardUsage=standardUsageIn && standardUsageOut;
		boolean transferIn=map.get("TransactionType").equalsIgnoreCase("INVOICE") && map.get("Account.CounteragentType").equalsIgnoreCase("INTERNAL_SUPPLIER");
		boolean trnsferOut=map.get("TransactionType").equalsIgnoreCase("OUTGOING_INVOICE") && map.get("Account.CounteragentType").equalsIgnoreCase("INTERNAL_SUPPLIER");
		boolean wastage=map.get("TransactionType").equalsIgnoreCase("WRITEOFF") && map.get("Account.CounteragentType").equalsIgnoreCase("NONE");
		
		
		//DRTPURCHASE
		if(supplierPurchase || storePurchase){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
			
			//for supplier
			if(supplierPurchase){
			String supplierId=map.get("Counteragent.Id");
			HashMap<String,Object> supplierDetails =iikohandler.makeSuppliersResponse(supplierId); 		//calling suppliers api
			requestBean.setSupplierCode(supplierDetails.get("code").toString());
			}
			
			//for store to store purchase
			if(storePurchase){
				requestBean.setStoreCode(storeCode);
			}
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			//requestBean.setInvoiceNo(map.get("Document"))
			requestBean.setInventoryCount(Double.parseDouble(invEndingBalance.get("amount").toString()));
			requestBean.setTranInventoryAmount(Double.parseDouble(invEndingBalance.get("sum").toString()));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranPurchaseAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setPurchaseCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranPurchaseUnitCost(requestBean.getTranPurchaseAmount()/requestBean.getPurchaseCount());
			requestBean.setTranType("DRTPURCHASE");
		}
		
		//WASTAGE
		if(wastage){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
			
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranType("WASTAGE");
		}
		
		//TRFIN
		if(transferIn){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
			
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranType("TRFIN");
		}
		
		//TRFOUT
		if(transferIn){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
					
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranType("TRFOUT");
		}
		
		//WASTAGE
		if(wastage){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
					
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranType("TRFOUT");
		}
		
		//STDUSAGE
		double stdusage=0.00;
		double stdUsagecount=0.00;
		
		if(standardUsageIn ){
			InventoryTranxReqBean requestBean=new InventoryTranxReqBean();
					
			requestBean.setSkuCode(map.get("Product.Num"));
			requestBean.setSkuDesc(map.get("Product.Name"));
			requestBean.setTranDate(map.get("DateTime.Typed"));
			requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
			requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
			requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
			requestBean.setTranType("STDUSAGE");
			if(standardUsageOut && requestBean.getSkuCode().equals(map.get("Product.Num"))){
						
				requestBean.setSkuCode(map.get("Product.Num"));
				requestBean.setSkuDesc(map.get("Product.Name"));
				requestBean.setTranDate(map.get("DateTime.Typed"));
				requestBean.setInventoryCount(Double.parseDouble(map.get("Amount")));
				requestBean.setTranInventoryAmount(Double.parseDouble(map.get("Sum.ResignedSum")));
				requestBean.setTranInventoryUnitCost(requestBean.getTranInventoryAmount()/requestBean.getInventoryCount());
				requestBean.setTranType("STDUSAGE");
				
			}
		}
		
	}	
	}
		
	
	return invTranxRequests;
	}


/*********************************************************************************************************************************************************
 * Hourly Sales Mix Data Request Method
 **********************************************************************************************************************************************************/
public List<HSMixDataReqBean> makeHSMixRequests(String from, String to) {
	List<ResBean> iikoResponses = iikohandler.handleHSMix(from, to);
	mapHSMixRequests(iikoResponses);
	return HSMixRequests;
}


public void mapHSMixRequests(List<ResBean> iikoResponses) {

	for (ResBean iikoResponse : iikoResponses) {
		if (!iikoResponse.getData().isEmpty()) {
			for (HashMap<String, String> map : iikoResponse.getData()) {
				menu(map);
				payment(map);
				discount(map);
				// voucher(map); coupon(map);
			}
		}
	}
}

@SuppressWarnings("null")
public void menu(HashMap<String, String> map) {

	HSMixDataReqBean request = null;

	String tranDate = map.get("OpenDate.Typed").concat("T00:00:00.000Z");

	int totalQuantity = 0;
	String itemCode = null;
	BigDecimal totalAmount = null;
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("##############.00");

	if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null) {
		request.setSalesTypeCode(1);
		totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
		double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
		totalAmount = new BigDecimal(df.format(amount));
		itemCode = map.get("DishCode");

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("PICKUP")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Take out")) {
				request.setSalesTypeCode(2);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("DishCode");
			} else if (map.get("OriginName").equalsIgnoreCase("Pickup")) {
				request.setSalesTypeCode(3);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("DishCode");
			}
		} else {
			request.setSalesTypeCode(11);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("DishCode");
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("COURIER")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Online Internet ")) {
				request.setSalesTypeCode(4);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("DishCode");
			} else if (map.get("OriginName").equalsIgnoreCase("Delivery")) {
				request.setSalesTypeCode(5);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("DishCode");
			}
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null && map.get("Banquet").equalsIgnoreCase("TRUE")) {
		{
			request.setSalesTypeCode(8);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("DishCode");
		}
	} else if (map.get("NonCashPaymentType") != null
			&& map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE")) {
		{
			request.setSalesTypeCode(6);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("DishCode");
		}
	}
	request.setBrandCode("PH");
	request.setStoreCode("345");
	request.setTranDate(tranDate);
	request.setTranStartTime(this.startTime + ":00");
	request.setTranEndTime(this.endTime + ":01");
	request.setItemType("IM");
	request.setItemCode(itemCode);
	request.setTotalQuantity(totalQuantity);
	request.setTotalAmount(totalAmount);

	HSMixRequests.add(request);

}

@SuppressWarnings("null")
public void payment(HashMap<String, String> map) {

	HSMixDataReqBean request = null;
	String tranDate = map.get("OpenDate.Typed").concat("T00:00:00.000Z");
	int totalQuantity = 0;
	String itemCode = null;
	BigDecimal totalAmount = null;
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("##############.00");

	if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null) {
		request.setSalesTypeCode(1);
		totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
		double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
		totalAmount = new BigDecimal(df.format(amount));
		itemCode = map.get("PayTypes");

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("PICKUP")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Take out")) {
				request.setSalesTypeCode(2);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("PayTypes");
			} else if (map.get("OriginName").equalsIgnoreCase("Pickup")) {
				request.setSalesTypeCode(3);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("PayTypes");
			}
		} else {
			request.setSalesTypeCode(11);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("PayTypes");
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("COURIER")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Online Internet ")) {
				request.setSalesTypeCode(4);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("PayTypes");
			} else if (map.get("OriginName").equalsIgnoreCase("Delivery ")) {
				request.setSalesTypeCode(5);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("PayTypes");
			}
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null && map.get("Banquet").equalsIgnoreCase("TRUE")) {
		{
			request.setSalesTypeCode(8);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("PayTypes");
		}
	} else if (map.get("NonCashPaymentType") != null
			&& map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE")) {
		{
			request.setSalesTypeCode(6);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("PayTypes");
		}
	}
	request.setBrandCode("PH");
	request.setStoreCode("345");
	request.setTranDate(tranDate);
	request.setTranStartTime(this.startTime + ":00");
	request.setTranEndTime(this.endTime + ":01");
	request.setItemType("IP");
	request.setItemCode(itemCode);
	request.setTotalQuantity(totalQuantity);
	request.setTotalAmount(totalAmount);

	HSMixRequests.add(request);
}

@SuppressWarnings("null")
public void discount(HashMap<String, String> map) {

	HSMixDataReqBean request = null;
	String tranDate = map.get("OpenDate.Typed").concat("T00:00:00.000Z");
	int totalQuantity = 0;
	String itemCode = null;
	BigDecimal totalAmount = null;
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("##############.00");

	if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null) {
		request.setSalesTypeCode(1);
		totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
		double amount = Double.parseDouble(map.get("DiscountSum"));
		totalAmount = new BigDecimal(df.format(amount));
		itemCode = map.get("OrderDiscount.Type");

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("PICKUP")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Take out")) {
				request.setSalesTypeCode(2);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DiscountSum"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("OrderDiscount.Type");
			} else if (map.get("OriginName").equalsIgnoreCase("Pickup")) {
				request.setSalesTypeCode(3);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DiscountSum"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("OrderDiscount.Type");
			}
		} else {
			request.setSalesTypeCode(11);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DiscountSum"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("OrderDiscount.Type");
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
			&& map.get("Delivery.ServiceType") != null
			&& map.get("Delivery.ServiceType").equalsIgnoreCase("COURIER")) {

		if (null != map.get("OriginName")) {
			if (map.get("OriginName").equalsIgnoreCase("Online Internet ")) {
				request.setSalesTypeCode(4);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DiscountSum"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("OrderDiscount.Type");
			} else if (map.get("OriginName").equalsIgnoreCase("Delivery ")) {
				request.setSalesTypeCode(5);
				totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
				double amount = Double.parseDouble(map.get("DiscountSum"));
				totalAmount = new BigDecimal(df.format(amount));
				itemCode = map.get("OrderDiscount.Type");
			}
		}

	} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
			&& map.get("Delivery.ServiceType") == null && map.get("Banquet").equalsIgnoreCase("TRUE")) {
		{
			request.setSalesTypeCode(8);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DiscountSum"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("OrderDiscount.Type");
		}
	} else if (map.get("NonCashPaymentType") != null
			&& map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE")) {
		{
			request.setSalesTypeCode(6);
			totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
			double amount = Double.parseDouble(map.get("DiscountSum"));
			totalAmount = new BigDecimal(df.format(amount));
			itemCode = map.get("OrderDiscount.Type");
		}
	}
	request.setBrandCode("PH");
	request.setStoreCode("345");
	request.setTranDate(tranDate);
	request.setTranStartTime(this.startTime + ":00");
	request.setTranEndTime(this.endTime + ":01");
	request.setItemType("ID");
	request.setItemCode(itemCode);
	request.setTotalQuantity(totalQuantity);
	request.setTotalAmount(totalAmount);

	HSMixRequests.add(request);
}

/*
 * public void voucher(HashMap<String, String> map) { * //just copy-paste from
 * menu and change the itemCode to IV while setting it to the request }
 * 
 * public void coupon(HashMap<String, String> map) { * //just copy-paste from
 * menu and change the itemCode to IC while setting it to the request }
 */
/***************************************************************************************************************************************************************
 * Hourly sales
 **************************************************************************************************************************************************************/

@SuppressWarnings("null")
public List<HrlySalesReqBean> makeHrlySalesRequests(String from, String to) {

	List<ResBean> iikoResponses = iikohandler.handleHrlySales(from, to);
	HrlySalesReqBean request = new HrlySalesReqBean();
	int totalQuantity = 0;
	BigDecimal totalAmount = null;

	DecimalFormat df = new DecimalFormat();
	df.applyPattern("##############.00");

	for (ResBean iikoResponse : iikoResponses) {
		if (!iikoResponse.getData().isEmpty()) {
			log.debug("yoyo---->"+iikoResponse.getData());
			for (HashMap<String, String> map : iikoResponse.getData()) {

				if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
						&& map.get("Delivery.ServiceType") == null) {
					request.setSalesTypeCode(1);
					totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
					double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
					totalAmount = new BigDecimal(df.format(amount));

				} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
						&& map.get("Delivery.ServiceType") != null
						&& map.get("Delivery.ServiceType").equalsIgnoreCase("PICKUP")) {

					if (null != map.get("OriginName")) {
						if (map.get("OriginName").equalsIgnoreCase("Take out")) {
							request.setSalesTypeCode(2);
							totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
							double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
							totalAmount = new BigDecimal(df.format(amount));

						} else if (map.get("OriginName").equalsIgnoreCase("Pickup")) {
							request.setSalesTypeCode(3);
							totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
							double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
							totalAmount = new BigDecimal(df.format(amount));

						}
					} else {
						request.setSalesTypeCode(11);
						totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
						double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
						totalAmount = new BigDecimal(df.format(amount));

					}

				} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("DELIVERY_ORDER")
						&& map.get("Delivery.ServiceType") != null
						&& map.get("Delivery.ServiceType").equalsIgnoreCase("COURIER")) {

					if (null != map.get("OriginName")) {
						if (map.get("OriginName").equalsIgnoreCase("Online Internet ")) {
							request.setSalesTypeCode(4);
							totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
							double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
							totalAmount = new BigDecimal(df.format(amount));

						} else if (map.get("OriginName").equalsIgnoreCase("Delivery")) {
							request.setSalesTypeCode(5);
							totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
							double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
							totalAmount = new BigDecimal(df.format(amount));

						}
					}

				} else if (map.get("Delivery.IsDelivery").equalsIgnoreCase("ORDER_WITHOUT_DELIVERY")
						&& map.get("Delivery.ServiceType") == null && map.get("Banquet").equalsIgnoreCase("TRUE")) {
					{
						request.setSalesTypeCode(8);
						totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
						double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
						totalAmount = new BigDecimal(df.format(amount));

					}
				} else if (map.get("NonCashPaymentType") != null
						&& map.get("NonCashPaymentType").equalsIgnoreCase("EMPLOYEE")) {
					{
						request.setSalesTypeCode(6);
						totalQuantity = Integer.parseInt(map.get("UniqOrderId"));
						double amount = Double.parseDouble(map.get("DishDiscountSumInt"));
						totalAmount = new BigDecimal(df.format(amount));

					}
				}

				String transDate = map.get("OpenDate.Typed").concat("T00:00:00.000Z");

				request.setBrandCode("PH");
				request.setStoreCode("345");
				// salesTypeCode already being set above in the if-else
				request.setTranDate(transDate);
				request.setTranStartTime(this.startTime + ":00");
				request.setTranEndTime(this.endTime + ":01");
				request.setPaidTranCount(totalQuantity);
				request.setNetSales(totalAmount);

				HrlySalesRequests.add(request);
			}
		}
	}

	return HrlySalesRequests;
}

/***************************************************************************************************************************************************************
 * Petty Cash
 ***************************************************************************************************************************************************************/

@SuppressWarnings("null")
public List<PettyCashReqBean> makePettyCashRequests(String from, String to) {

	List<ResBean> iikoResponses = iikohandler.handlePettyCash(from, to);
	PettyCashReqBean request = null;
	int totalQuantity = 0;

	BigDecimal totalAmount = null;
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("##############.00");

	for (ResBean iikoResponse : iikoResponses) {
		for (HashMap<String, String> map : iikoResponse.getData()) {

			if (map.get("Account.Name").equalsIgnoreCase("Medical Fee")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(129);
			} else if (map.get("Account.Name").equalsIgnoreCase("Repair & Maint")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(131);
			} else if (map.get("Account.Name").equalsIgnoreCase("Clean Supplies")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(132);
			} else if (map.get("Account.Name").equalsIgnoreCase("Smallwares")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(133);
			} else if (map.get("Account.Name").equalsIgnoreCase("Print/Stationery")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(134);
			} else if (map.get("Account.Name").equalsIgnoreCase("M/Vehicle Exp")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(135);
			} else if (map.get("Account.Name").equalsIgnoreCase("Local Transport")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(136);
			} else if (map.get("Account.Name").equalsIgnoreCase("Bank Chrgs")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(137);
			} else if (map.get("Account.Name").equalsIgnoreCase("Misc Exp")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(138);
			} else if (map.get("Account.Name").equalsIgnoreCase("Cost of Sales")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(139);
			} else if (map.get("Account.Name").equalsIgnoreCase("RJEP")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(140);
			} else if (map.get("Account.Name").equalsIgnoreCase("Trainee Mgr Exp")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(141);
			} else if (map.get("Account.Name").equalsIgnoreCase("Uniform")
					&& map.get("TransactionType").equalsIgnoreCase("PAYOUT")) {
				request.setFinancialCode(142);
			}

			String tranDate = map.get("DateTime.Typed").concat("T00:00:00.000Z");
			totalQuantity = Integer.parseInt(map.get("Amount"));
			double amount = Double.parseDouble(map.get("Sum.ResignedSum"));
			totalAmount = new BigDecimal(df.format(amount));

			// financialCode is set above in if-else
			request.setStoreCode("345");
			request.setTotalAmount(totalAmount);
			request.setTotalTransaction(totalQuantity);
			request.setTranDate(tranDate);

			PettyCashRequests.add(request);
		}
	}

	return PettyCashRequests;
}


}
