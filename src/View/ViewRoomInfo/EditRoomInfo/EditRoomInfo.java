package View.ViewRoomInfo.EditRoomInfo;

import DAO.EditDAO;
import DAOImplementation.OrderDAOImplementation;
import DAOImplementation.ViewDAOImplementation;
import Objects.Item;
import Objects.Order;
import View.ViewRoomInfo.EditRoomInfo.CheckOutRoom.CheckoutRoom;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.*;

public class EditRoomInfo implements Initializable, EditDAO{

    @FXML
    private JFXTextField roomNumberField;

    @FXML
    private JFXTextField totalCostField;

    @FXML
    private JFXDatePicker arrivalDateField;

    @FXML
    private JFXDatePicker departureDateField;

    @FXML
    private TableView<Order> tableView;

    @FXML
    private JFXButton checkOutBtn;

    @FXML
    private JFXComboBox<String> comboBox;

    private List<Order> allOrders;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set comboBox values
        comboBox.getItems().addAll("All Categories", "Bar Category", "Extra Category");
        comboBox.setValue("All Categories");

        checkOutBtn.setOnAction(event -> {
            checkOutRoom();
        });
    }

    private ViewDAOImplementation viewDAOImplementation = new ViewDAOImplementation();

    public void setRoomNumberField(String  roomNumberField) {
        this.roomNumberField.setText(roomNumberField);
    }

    public void setTotalCostField(String totalCostField) {
        this.totalCostField.setText(totalCostField);
    }

    public void setArrivalDateField(String arrivalDateField) {
        this.arrivalDateField.setValue(LocalDate.parse(arrivalDateField));
    }

    public void setDepartureDateField(String departureDateField) {
        this.departureDateField.setValue(LocalDate.parse(departureDateField));
    }

    // initialize table with
    // all orders for the specific room
    // like this
    // {order status} | {order's items} | {order total cost}
    public void setTableView(List<Order> orderList) {
        TableColumn itemListColumn = viewDAOImplementation.getColumnName("ITEMS", "itemListToPrint");
        TableColumn statusColumn = viewDAOImplementation.getColumnName("STATUS", "vboxToPrint");
        TableColumn orderTotalCost = viewDAOImplementation.getColumnName("TOTAL COST", "totalCostToPrint");

        // set column width
        itemListColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        statusColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 30 ); // 30% width
        orderTotalCost.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width

        TableColumn[] list = new TableColumn[] {statusColumn, itemListColumn, orderTotalCost};

        viewDAOImplementation.setTableView(tableView);
        viewDAOImplementation.addColumnNames(list);
        ObservableList<Order> orders =  FXCollections.observableArrayList(orderList);
        tableView.setItems(orders);

        // set all orders list
        allOrders = new ArrayList<Order>(tableView.getItems());
    }

    public void onComboBoxClick() {
        String choice = comboBox.getValue();
        List<Order> orderListToShow = null;

        orderListToShow = getOrderList(choice);

        double cost = calculateOrderTotalCost(orderListToShow);

        DecimalFormat df = new DecimalFormat("#,##0.00",
                DecimalFormatSymbols.getInstance(Locale.US));
        String result = df.format(cost);
        totalCostField.setText(result);

        tableView.getItems().clear();
        tableView.getItems().addAll(orderListToShow);
    }

    private ObservableList<Order> getOrderList(String choice) {
        List<Order> orderList = new ArrayList<Order>();

        switch (choice) {
            case "All Categories":
                orderList = new ArrayList<Order>(allOrders);
                break;
            case "Bar Category":
                for (Order order : allOrders) {
                    for (Item item : order.getItemList()) {
                        if (!item.getItemCategory().equals("Extra")) {
                            orderList.add(order);
                            break;
                        }
                    }
                }
                break;
            case "Extra Category":
                for (Order order : allOrders) {
                    for (Item item : order.getItemList()) {
                        if (item.getItemCategory().equals("Extra")) {
                            orderList.add(order);
                            break;
                        }
                    }
                }
                break;
        }
        return FXCollections.observableArrayList(orderList);
    }

    private double calculateOrderTotalCost(List<Order> orders) {
        double sum = 0;

        for (Order order : orders) {
            for (Item item : order.getItemList()) {
                sum += item.getPrice();
            }
        }
        return sum;
    }

    private double calculateOrderDebts(ObservableList<Order> orders) {
        double sum = 0;

        for (Order order : orders) {
            if (order.isStatus())
                continue;
            for (Item item : order.getItemList()) {
                sum += item.getPrice();
            }
        }
        return sum;
    }

    private void checkOutRoom() {

        // show checkout stage
        Stage editStage = new Stage();
        Parent root = null;
        CheckoutRoom controller = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ViewRoomInfo/EditRoomInfo/CheckOutRoom/CheckoutRoom.fxml"));
            root = fxmlLoader.load();
            controller = fxmlLoader.<CheckoutRoom>getController();

            DecimalFormat df = new DecimalFormat("#,##0.00",
                    DecimalFormatSymbols.getInstance(Locale.US));

            String totalDebts = df.format(calculateOrderDebts(getOrderList("All Categories"))) + " €";
            controller.setTotalDebtsLabel(totalDebts);
            String barDebts = df.format(calculateOrderDebts(getOrderList("Bar Category"))) + " €";
            controller.setBarDebtsLabel(barDebts);
            String extraDebts = df.format(calculateOrderDebts(getOrderList("Extra Category"))) + " €";
            controller.setExtraDebtsLabel(extraDebts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editStage.setTitle("Checkout Room");
        editStage.setScene(new Scene(root));
        editStage.getIcons().add(new Image("/Icons/edit.png"));
        editStage.show();
        CheckoutRoom finalController = controller;
        editStage.setOnCloseRequest(event -> {
            if (finalController.getCheckout() != null) {
                updateOrders(finalController.getCheckout());
            }
            else {
                System.out.println("null here");
            }

        });
    }

    private void updateOrders(String choice) {
        List<Order> orderList = getOrderList(choice);
        OrderDAOImplementation orderDAOImplementation = new OrderDAOImplementation();

        for (int i = 0; i < orderList.size(); i++) {                 // for each order on the room
            if (!orderList.get(i).isStatus()) {                      // if it's not paid
                orderList.get(i).setStatus(true);                    // set status to paid
                orderDAOImplementation.update(orderList.get(i));     // update order
            }
        }

        updateGUI(orderList);
    }

    private void updateGUI(List<Order> orderList) {
        comboBox.setValue("All Categories");
        tableView.getItems().clear();
        tableView.getItems().addAll(orderList);
    }

    @Override
    public Object getUpdatedObject() {
        return null;
    }
}
