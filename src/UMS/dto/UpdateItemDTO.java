package UMS.dto;


import java.sql.Date;

public class UpdateItemDTO {

    private int quantity;
    private Date date;
    private String empID;
    private String providerName;
    private String itemName;
    private String site;

    public UpdateItemDTO(int quantity, Date date, String empID, String providerName, String itemName, String site) {
        this.quantity = quantity;
        this.date = date;
        this.empID = empID;
        this.providerName = providerName;
        this.itemName = itemName;
        this.site = site;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
