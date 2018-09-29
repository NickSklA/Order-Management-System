package View.ViewOrders.EditOrder;

import DAO.EditDAO;
import DAOImplementation.BookedRoomDAOImplementation;
import DAOImplementation.ItemDAOImplementation;
import DAOImplementation.ViewDAOImplementation;
import Objects.BookedRoom;
import Objects.Item;
import Objects.Order;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class EditOrder implements Initializable, EditDAO{

    @FXML
    private JFXTextField orderIDTextField;

	@FXML
	private JFXComboBox<String> roomNumberComboBox;

	@FXML
	private JFXDatePicker orderDatePicker;

	@FXML
	private JFXTimePicker orderTimePicker;

	@FXML
	private JFXCheckBox orderPaidCheckBox;

	@FXML
	private JFXListView<Label> addedItemListView;

    @FXML
    private TreeTableView<Item> treeTableView;

	@FXML
	private Label totalCostLabel;

	@FXML
	private JFXButton editOrderSubmit;

	private List<Item> itemList;

	private Map<String, Integer> roomNumber_roomID = new HashMap<>();

	private Order newOrder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// set all rooms on combobox..
		initializeComboBoxValues();

		// set all items on treeTableView
        initializeTableViewValues();

        editOrderSubmit.setOnAction(event -> {
			onSubmitClick();
		});
	}

	private void initializeTableViewValues() {

		TreeTableColumn<Item, String> itemNameColumn = new TreeTableColumn<Item, String>("ITEM NAME");
		TreeTableColumn<Item, String> itemPriceColumn = new TreeTableColumn<Item, String>("ITEM PRICE");

		itemNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Item, String>("itemName"));
		itemPriceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Item, String>("price"));

		itemNameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 80 ); // 80% width
		itemPriceColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width


		treeTableView.getColumns().addAll(itemNameColumn, itemPriceColumn);

		ItemDAOImplementation itemDAOImplementation = new ItemDAOImplementation();
		List<Item> itemList = itemDAOImplementation.getAll();
		TreeItem<Item> dummyRoot = new TreeItem<>();

		// create map that storage for each category his treeItem
		Map<String, TreeItem<Item>> categoryTreeItemMap = new HashMap<>();
		for (Item item : itemList) {
			boolean found = false;
			for (Map.Entry<String, TreeItem<Item>> entry : categoryTreeItemMap.entrySet()) {
				if (item.getItemCategory().equals(entry.getKey())) {
					found = true;
					entry.getValue().getChildren().add(new TreeItem<Item>(item));
					break;
				}
			}

			if (!found) {
				Item root = new Item(item.getItemCategory(), item.getItemCategory(), 0);
				TreeItem<Item> anotherRoot = new TreeItem<Item>(root);
				categoryTreeItemMap.put(root.getItemCategory(), anotherRoot);
				dummyRoot.getChildren().add(anotherRoot);
				categoryTreeItemMap.get(root.getItemCategory()).getChildren().add(new TreeItem<Item>(item));
			}
		}

		treeTableView.setRoot(dummyRoot);
		treeTableView.setShowRoot(false);
	}

    public void addFromTable() {
        int index = treeTableView.getSelectionModel().getSelectedIndex();

        // avoid errors..
        if (index == -1)
            return;
		else if (treeTableView.getTreeItem(index).getValue().getPrice() == 0)
			return;

        // get item from table
        Item item = treeTableView.getTreeItem(index).getValue();

        // add Item to listView
        addedItemListView.getItems().add(new Label(item.getItemName()));

        // add Item to itemList
        itemList.add(item);

        // calculate cost
        double cost = Double.valueOf(totalCostLabel.getText());
        cost += item.getPrice();

        // print new cost
        DecimalFormat df = new DecimalFormat("#.00",
                DecimalFormatSymbols.getInstance(Locale.US));
        String result = df.format(cost);
        totalCostLabel.setText(result);

        treeTableView.getSelectionModel().clearSelection();
    }

	private void initializeComboBoxValues() {
		BookedRoomDAOImplementation bookedRoomDAOImplementation = new BookedRoomDAOImplementation();
		List<BookedRoom> roomList = bookedRoomDAOImplementation.getAll();

		for (BookedRoom room: roomList) {
			LocalDate roomDepartureDate = LocalDate.parse(room.getDepartureDate());

			// check if room is booked right now
			if (roomDepartureDate.isAfter(LocalDate.now()) || roomDepartureDate.isEqual(LocalDate.now())) {

				// add room to map
				roomNumber_roomID.put(room.getRoomNumber(), room.getRoomID());

				// add roomNumber to combobox
				roomNumberComboBox.getItems().add(room.getRoomNumber());
			}
		}
	}

	private void onSubmitClick() {
		if (isValid()) {
            newOrder = new Order();

            String[] id = orderIDTextField.getText().split("#");
            newOrder.setOrderID(Integer.parseInt(id[1]));
			newOrder.setRoomNumber(roomNumberComboBox.getValue());
			newOrder.setRoomID(roomNumber_roomID.get(roomNumberComboBox.getValue()));
			newOrder.setItemList(itemList);
			newOrder.setDate(orderDatePicker.getValue().toString());

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String timeNow = sdf.format(cal.getTime());

			newOrder.setTime(timeNow);
			newOrder.setStatus(orderPaidCheckBox.isSelected());

			Stage stage = (Stage) editOrderSubmit.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
			stage.close();
		}
	}

	private boolean isValid() {
		boolean success = true;

		// check for errors..
		if (roomNumberComboBox.getSelectionModel().isEmpty()) {
			success = false;
		}

		if (addedItemListView.getItems().size() == 0) {
			success = false;
		}

		return success;
	}

	public void setOrderID(int orderID) {
		this.orderIDTextField.setText("#" + orderID);
	}

	public void setOrderRoomNumberField(String orderRoomIDField) {
		this.roomNumberComboBox.setValue(orderRoomIDField);
	}

	public void setOrderDateField(String orderDateField) {
		this.orderDatePicker.setValue(LocalDate.parse(orderDateField));
	}

	public void setOrderTimeField(String orderTimeField) {
		this.orderTimePicker.setValue(LocalTime.parse(orderTimeField));
	}

	public void setOrderStatusBox(boolean orderStatusBox) {
		this.orderPaidCheckBox.setSelected(orderStatusBox);
	}

    public void setItemList(List<Item> itemList) {
	    double cost = 0;
        for (Item item : itemList) {
            addedItemListView.getItems().add(new Label(item.getItemName()));
            cost += item.getPrice();
        }
        DecimalFormat df = new DecimalFormat("#.00",
                DecimalFormatSymbols.getInstance(Locale.US));
        String result = df.format(cost);
        totalCostLabel.setText(result);

        this.itemList = new ArrayList<Item>();
        this.itemList.addAll(itemList);
    }

    @Override
    public Object getUpdatedObject() {
        return newOrder;
    }
}
