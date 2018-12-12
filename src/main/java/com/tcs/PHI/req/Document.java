package com.tcs.PHI.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * <p>Java class for incomingInvoiceDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incomingInvoiceDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="items" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item" type="{}incomingInvoiceItemDto" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conception" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateIncoming" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defaultStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supplier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="incomingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useDefaultDocumentTime" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="status" type="{}documentStatus" minOccurs="0"/>
 *         &lt;element name="incomingDocumentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="employeePassToAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportInvoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkedOutgoingInvoiceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@JsonPropertyOrder({
    "items",
    "id",
    "conception",
    "comment",
    "documentNumber",
    "dateIncoming",
    "invoice",
    "defaultStore",
    "supplier",
    "dueDate",
    "incomingDate",
    "useDefaultDocumentTime",
    "status",
    "incomingDocumentNumber",
    "employeePassToAccount",
    "transportInvoiceNumber",
    "linkedOutgoingInvoiceId"
})
@JacksonXmlRootElement(localName = "document")
public class Document
    implements Serializable
{

    private final static long serialVersionUID = -1L;
   @JacksonXmlElementWrapper(localName="items")
   @JacksonXmlProperty(localName = "item")
    protected List<Document.Item> item= new ArrayList<Document.Item>();
    protected String id;
    protected String conception;
    protected String comment;
    protected String documentNumber;
    protected String dateIncoming;
    protected String invoice;
    protected String defaultStore;
    protected String supplier;
    protected String dueDate;
    protected String incomingDate;
    @JsonProperty(defaultValue="false")
    protected Boolean useDefaultDocumentTime;
    protected DocumentStatus status;
    protected String incomingDocumentNumber;
    protected String employeePassToAccount;
    protected String transportInvoiceNumber;
    protected String linkedOutgoingInvoiceId;

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link Document.Item }
     *     
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link Document.Item }
     *     
     */
    public void setItem(Document.Item value) {
        this.item.add(value);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the conception property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConception() {
        return conception;
    }

    /**
     * Sets the value of the conception property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConception(String value) {
        this.conception = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the dateIncoming property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateIncoming() {
        return dateIncoming;
    }

    /**
     * Sets the value of the dateIncoming property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateIncoming(String value) {
        this.dateIncoming = value;
    }

    /**
     * Gets the value of the invoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * Sets the value of the invoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoice(String value) {
        this.invoice = value;
    }

    /**
     * Gets the value of the defaultStore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultStore() {
        return defaultStore;
    }

    /**
     * Sets the value of the defaultStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultStore(String value) {
        this.defaultStore = value;
    }

    /**
     * Gets the value of the supplier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Sets the value of the supplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplier(String value) {
        this.supplier = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDate(String value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the incomingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomingDate() {
        return incomingDate;
    }

    /**
     * Sets the value of the incomingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomingDate(String value) {
        this.incomingDate = value;
    }

    /**
     * Gets the value of the useDefaultDocumentTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseDefaultDocumentTime() {
        return useDefaultDocumentTime;
    }

    /**
     * Sets the value of the useDefaultDocumentTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseDefaultDocumentTime(Boolean value) {
        this.useDefaultDocumentTime = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentStatus }
     *     
     */
    public DocumentStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentStatus }
     *     
     */
    public void setStatus(DocumentStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the incomingDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomingDocumentNumber() {
        return incomingDocumentNumber;
    }

    /**
     * Sets the value of the incomingDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomingDocumentNumber(String value) {
        this.incomingDocumentNumber = value;
    }

    /**
     * Gets the value of the employeePassToAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployeePassToAccount() {
        return employeePassToAccount;
    }

    /**
     * Sets the value of the employeePassToAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployeePassToAccount(String value) {
        this.employeePassToAccount = value;
    }

    /**
     * Gets the value of the transportInvoiceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransportInvoiceNumber() {
        return transportInvoiceNumber;
    }

    /**
     * Sets the value of the transportInvoiceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransportInvoiceNumber(String value) {
        this.transportInvoiceNumber = value;
    }

    /**
     * Gets the value of the linkedOutgoingInvoiceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkedOutgoingInvoiceId() {
        return linkedOutgoingInvoiceId;
    }

    /**
     * Sets the value of the linkedOutgoingInvoiceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkedOutgoingInvoiceId(String value) {
        this.linkedOutgoingInvoiceId = value;
    }


    /**
     * <p>Java class for incomingInvoiceItemDto complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType name="incomingInvoiceItemDto">
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="supplierProduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="supplierProductArticle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="product" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="productArticle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="producer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="containerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="amountUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="actualUnitWeight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="sum" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="discountSum" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="vatPercent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="vatSum" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="priceUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="store" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customsDeclarationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="actualAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */

    @JsonPropertyOrder({
        "amount",
        "supplierProduct",
        "supplierProductArticle",
        "product",
        "productArticle",
        "producer",
        "num",
        "containerId",
        "amountUnit",
        "actualUnitWeight",
        "sum",
        "discountSum",
        "vatPercent",
        "vatSum",
        "priceUnit",
        "price",
        "code",
        "store",
        "customsDeclarationNumber",
        "actualAmount"
    })
    @JacksonXmlRootElement(localName="item")
    public static class Item
        implements Serializable
    {



        private final static long serialVersionUID = -1L;
        protected BigDecimal amount;
        protected String supplierProduct;
        protected String supplierProductArticle;
        protected String product;
        protected String productArticle=new String();
        protected String producer;
        protected int num;
        protected String containerId;
        protected String amountUnit;
        protected BigDecimal actualUnitWeight;
        @JsonProperty(required = true)
        protected BigDecimal sum;
        protected BigDecimal discountSum;
        protected BigDecimal vatPercent;
        protected BigDecimal vatSum;
        protected String priceUnit;
        protected BigDecimal price;
        protected String code;
        protected String store;
        protected String customsDeclarationNumber;
        protected BigDecimal actualAmount;

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        /**
         * Gets the value of the supplierProduct property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupplierProduct() {
            return supplierProduct;
        }

        /**
         * Sets the value of the supplierProduct property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupplierProduct(String value) {
            this.supplierProduct = value;
        }

        /**
         * Gets the value of the supplierProductArticle property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupplierProductArticle() {
            return supplierProductArticle;
        }

        /**
         * Sets the value of the supplierProductArticle property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupplierProductArticle(String value) {
            this.supplierProductArticle = value;
        }

        /**
         * Gets the value of the product property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProduct() {
            return product;
        }

        /**
         * Sets the value of the product property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProduct(String value) {
            this.product = value;
        }

        /**
         * Gets the value of the productArticle property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductArticle() {
            return productArticle;
        }

        /**
         * Sets the value of the productArticle property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductArticle(String value) {
            this.productArticle = value;
        }

        /**
         * Gets the value of the producer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProducer() {
            return producer;
        }

        /**
         * Sets the value of the producer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProducer(String value) {
            this.producer = value;
        }

        /**
         * Gets the value of the num property.
         * 
         */
        public int getNum() {
            return num;
        }

        /**
         * Sets the value of the num property.
         * 
         */
        public void setNum(int value) {
            this.num = value;
        }

        /**
         * Gets the value of the containerId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContainerId() {
            return containerId;
        }

        /**
         * Sets the value of the containerId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContainerId(String value) {
            this.containerId = value;
        }

        /**
         * Gets the value of the amountUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmountUnit() {
            return amountUnit;
        }

        /**
         * Sets the value of the amountUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmountUnit(String value) {
            this.amountUnit = value;
        }

        /**
         * Gets the value of the actualUnitWeight property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getActualUnitWeight() {
            return actualUnitWeight;
        }

        /**
         * Sets the value of the actualUnitWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setActualUnitWeight(BigDecimal value) {
            this.actualUnitWeight = value;
        }

        /**
         * Gets the value of the sum property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSum() {
            return sum;
        }

        /**
         * Sets the value of the sum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSum(BigDecimal value) {
            this.sum = value;
        }

        /**
         * Gets the value of the discountSum property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDiscountSum() {
            return discountSum;
        }

        /**
         * Sets the value of the discountSum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDiscountSum(BigDecimal value) {
            this.discountSum = value;
        }

        /**
         * Gets the value of the vatPercent property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getVatPercent() {
            return vatPercent;
        }

        /**
         * Sets the value of the vatPercent property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setVatPercent(BigDecimal value) {
            this.vatPercent = value;
        }

        /**
         * Gets the value of the vatSum property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getVatSum() {
            return vatSum;
        }

        /**
         * Sets the value of the vatSum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setVatSum(BigDecimal value) {
            this.vatSum = value;
        }

        /**
         * Gets the value of the priceUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPriceUnit() {
            return priceUnit;
        }

        /**
         * Sets the value of the priceUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPriceUnit(String value) {
            this.priceUnit = value;
        }

        /**
         * Gets the value of the price property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPrice() {
            return price;
        }

        /**
         * Sets the value of the price property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPrice(BigDecimal value) {
            this.price = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the store property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStore() {
            return store;
        }

        /**
         * Sets the value of the store property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStore(String value) {
            this.store = value;
        }

        /**
         * Gets the value of the customsDeclarationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomsDeclarationNumber() {
            return customsDeclarationNumber;
        }

        /**
         * Sets the value of the customsDeclarationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomsDeclarationNumber(String value) {
            this.customsDeclarationNumber = value;
        }

        /**
         * Gets the value of the actualAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getActualAmount() {
            return actualAmount;
        }

        /**
         * Sets the value of the actualAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setActualAmount(BigDecimal value) {
            this.actualAmount = value;
        }
}}
