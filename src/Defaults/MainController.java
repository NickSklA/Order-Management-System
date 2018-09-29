package Defaults;

import View.ViewBookedRooms.ContentBookedRooms;
import View.ViewDashboard.ContentDashboard;
import View.ViewItems.ContentItems;
import View.ViewOrders.ContentOrders;


import java.net.URL;
import java.util.ResourceBundle;

import View.ViewRoomInfo.ContentRoomInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
    @FXML
    BorderPane mainPane;

    @FXML
    public void onItemClick() {
        ContentItems contentItems = new ContentItems();
        mainPane.setCenter(contentItems);
    }

    @FXML
    public void onOrderClick() {
        ContentOrders contentOrders = new ContentOrders();
        mainPane.setCenter(contentOrders);
    }

    public void onBookedRoomClick() {
        ContentBookedRooms contentBookedRooms = new ContentBookedRooms();
        mainPane.setCenter(contentBookedRooms);
    }

    public void onReceptionClick() {
        ContentRoomInfo contentRoomInfo = new ContentRoomInfo();
        mainPane.setCenter(contentRoomInfo);
    }

    public void onDashboardClick() {
        ContentDashboard contentDashboard = new ContentDashboard();
        mainPane.setCenter(contentDashboard);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContentOrders contentOrders = new ContentOrders();
        mainPane.setCenter(contentOrders);
    }
}
