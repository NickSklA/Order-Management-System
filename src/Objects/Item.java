package Objects;

public class Item {
    private int itemID;
    private String itemCategory;
    private String itemName;
    private double price;

    public Item(String itemCategory, String itemName, double price) {
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.price = price;
    }

    public Item() {
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return "{" + itemID + ", " + itemCategory + ", " + itemName + ", " + price + "}";
    }


}
