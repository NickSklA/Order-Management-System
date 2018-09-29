package Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.DateFormatSymbols;

public class Order {
    private int orderID;
    private int roomID;
    private String roomNumber;
    private List<Item> itemList;
    private String date;
    private String time;
    private boolean status;

    // to print fields //
    private VBox vboxToPrint;
    private String itemListToPrint;
    private ImageView statusToPrint = new ImageView("/Icons/incorrect.png");
    private String totalCostToPrint;

    public Order(int roomID, String date, String time, boolean status) {
        this.roomID = roomID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Order() {
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        setItemListToPrint();
    }

    public String getItemListToPrint() {
        return itemListToPrint;
    }

    public void setItemListToPrint() {
        Map<String, Integer> itemCount = new HashMap<>();
        StringBuilder toPrint = new StringBuilder();

        for (Item item: itemList) {
            String key = item.getItemName();

            if (itemCount.containsKey(key)) {               // if exist on map
                itemCount.put(key, itemCount.get(key) + 1); // increase his count
            }
            else {                                          // if doesnt exist on map
                itemCount.put(key, 1);                      // initialize value
            }
        }

        // build String
        for (Map.Entry<String, Integer> entry : itemCount.entrySet()) {
            toPrint.append(entry.getValue()).append("x  ").append(entry.getKey()).append("\n");
        }

        this.itemListToPrint = toPrint.toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private ImageView getStatusToPrint() {
        setStatusToPrint();
        return statusToPrint;
    }

    private void setStatusToPrint() {
        if (status) {
            this.statusToPrint = new ImageView("/Icons/correct.png");
        }
        statusToPrint.setFitHeight(24);
        statusToPrint.setFitWidth(24);
    }

    public VBox getVboxToPrint() {
        setVboxToPrint();
        return vboxToPrint;
    }

    private void setVboxToPrint() {
        String[] date = this.date.split("-");
        Label dateLbl = new Label(date[2]+" "+ new DateFormatSymbols().getMonths()[Integer.parseInt(date[1])-1]);
        String[] time = this.time.split(":");
        Label timeLbl = new Label(time[0]+":"+time[1]);
        ImageView statusImg = getStatusToPrint();
        this.vboxToPrint = new VBox(dateLbl, timeLbl, statusImg);
        this.vboxToPrint.setAlignment(Pos.CENTER);
        this.vboxToPrint.setSpacing(5);
        this.vboxToPrint.setPadding(new Insets(0,0,5,0));
    }

    public String getTotalCostToPrint() {
        setTotalCostToPrint();
        return totalCostToPrint;
    }

    private void setTotalCostToPrint() {
        double totalCost = 0;
        for (Item item : itemList) {
            totalCost += item.getPrice();
        }
        DecimalFormat df = new DecimalFormat("#.00",
                DecimalFormatSymbols.getInstance(Locale.US));
        String result = df.format(totalCost);
        this.totalCostToPrint = result + " €";
    }

    @Override
    public String toString() {
        return "{" + orderID + ", " + roomID + ", " + date + ", " + time + ", " + status + "}";
    }
}
