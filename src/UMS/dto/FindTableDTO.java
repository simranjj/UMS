package UMS.dto;

import java.util.Date;

public class FindTableDTO {

    private String empId;
    private int itemCount;
    private String itemName;
    private String quantity;
    private Date orderDate;

    public FindTableDTO(String empId, int itemCount, String itemName, String quantity, Date orderDate) {
        this.empId = empId;
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
