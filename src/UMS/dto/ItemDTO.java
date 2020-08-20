package UMS.dto;

public class ItemDTO {

    private String itemName;
    private String cost;
    private String minCount;

    public ItemDTO(String itemName, String cost, String minCount) {
        this.itemName = itemName;
        this.cost = cost;
        this.minCount = minCount;
    }

    public ItemDTO(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMinCount() {
        return minCount;
    }

    public void setMinCount(String minCount) {
        this.minCount = minCount;
    }
}
