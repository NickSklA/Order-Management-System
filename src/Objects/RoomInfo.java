package Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.StrictMath.round;

public class RoomInfo {
    private BookedRoom room;
    private List<Order> orderList;
    private double totalCost;
    private boolean status;

    // to print fields //
    private VBox roomNumberToPrint;
    private VBox orderToPrint;
    private VBox statusAndTotalCostToPrint;

    public RoomInfo() {
    }

    public RoomInfo(BookedRoom room, List<Order> orderList, double totalCost, boolean status) {
        this.room = room;
        this.orderList = orderList;
        this.totalCost = totalCost;
        this.status = status;
    }

    public BookedRoom getRoom() {
        return room;
    }

    public void setRoom(BookedRoom room) {
        this.room = room;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public double getTotalCost() {
        double totalCost = 0;

        for (Order order : orderList) {
            for (Item item : order.getItemList()) {
                totalCost += item.getPrice();
            }
        }
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    private boolean isStatus() {
        boolean status = true;

        for (Order order : orderList) {
            if (!order.isStatus()) {
                status = false;
                break;
            }
        }
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public VBox getRoomNumberToPrint() {
        setRoomNumberToPrint();
        return roomNumberToPrint;
    }

    private void setRoomNumberToPrint() {

        // calculate when room leaves..
        LocalDate now = LocalDate.now();
        LocalDate departure = LocalDate.parse(room.getDepartureDate());

        long diff = now.until(departure, ChronoUnit.DAYS);

        String days = "on " + diff + " days";
        if ( diff == 1) { days = "tomorrow"; }
        else if (diff == 0) { days = "today"; }

        Label roomNumberLbl = new Label(room.getRoomNumber());
        Label daysUntilRoomLeaves = new Label("Leaves " + days);

        this.roomNumberToPrint = new VBox(roomNumberLbl, daysUntilRoomLeaves);
        this.roomNumberToPrint.setSpacing(5);
        this.roomNumberToPrint.setPadding(new Insets(5,5,5,5));
        this.roomNumberToPrint.setAlignment(Pos.CENTER);
    }

    public VBox getOrderToPrint() {
        setOrderToPrint();
        return orderToPrint;
    }

    private void setOrderToPrint() {
        StringBuilder toPrint = null;

        for (Order order : orderList) {
            Map<String, Integer> itemCount = new HashMap<>();
            toPrint = new StringBuilder();

            for (Item item : order.getItemList()) {
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
                toPrint.append(entry.getValue()).append("x   ").append(entry.getKey()).append("\n");
            }

            // calculate last order date
            LocalDate now = LocalDate.now();
            LocalDate orderDate = LocalDate.parse(order.getDate());

            long diff = orderDate.until(now, ChronoUnit.DAYS);

            String days =  diff + " days ago";
            if ( diff == 1) { days = "yesterday"; }
            else if (diff == 0) { days = "today"; }


            toPrint.append("---------" + "\n" + "Last order ").append(days);
        }

        Label order;
        if (toPrint == null) {
            order = new Label("No orders found!");
        }
        else {
            order = new Label(toPrint.toString());
        }


        this.orderToPrint = new VBox(order);
    }

    public VBox getStatusAndTotalCostToPrint() {
        setStatusAndTotalCostToPrint();
        return statusAndTotalCostToPrint;
    }

    private void setStatusAndTotalCostToPrint() {
        String cost = String.format("%.2f", getTotalCost());
        Label totalCostLbl = new Label("Total Cost " + cost + " €");
        ImageView statusImage;
        if (isStatus()) {
            statusImage = new ImageView("/Icons/correct.png");
        }
        else {
            statusImage = new ImageView("/Icons/incorrect.png");
        }
        statusImage.setFitHeight(24);
        statusImage.setFitWidth(24);

        this.statusAndTotalCostToPrint = new VBox(totalCostLbl, statusImage);
        this.statusAndTotalCostToPrint.setAlignment(Pos.CENTER);
        this.statusAndTotalCostToPrint.setSpacing(5);
    }
}
