package View.ViewItems.EditItem;

import DAO.EditDAO;
import Objects.Item;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EditItem implements Initializable, EditDAO {
    @FXML
    private TextField itemNameField, itemPriceField, itemCategoryField;

    @FXML
    private Button itemSubmitBtn;
	
	private int itemID;
	private Item updatedItem;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemSubmitBtn.setOnAction(event -> {
            onSubmitClick();
        });
	}
	
	private void onSubmitClick() {
		if (isValid()) {
			updatedItem = new Item();
			
			updatedItem.setItemID(itemID);
			updatedItem.setItemCategory(itemCategoryField.getText());
			updatedItem.setItemName(itemNameField.getText());
			updatedItem.setPrice(Double.parseDouble(itemPriceField.getText()));
			
			Stage stage = (Stage) itemSubmitBtn.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
			stage.close();
		}
	}

	private boolean isValid() {
		boolean success = true;

		if (itemNameField.getText().equals("")) {
			itemNameField.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
			success = false;
		}
		else { itemNameField.setStyle(null); }

		if (itemPriceField.getText().equals("")) {
			itemPriceField.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
			success = false;
		}
		else { itemPriceField.setStyle(null); }

        if (itemCategoryField.getText().equals("")) {
            itemCategoryField.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
            success = false;
        }
        else { itemCategoryField.setStyle(null); }

		return success;
	}

	public void setItemCategoryField(String itemCategory) {this.itemCategoryField.setText(itemCategory);}

	public void setItemNameField(String itemName) {
		this.itemNameField.setText(itemName);
	}
	
	public void setItemPriceField(String price) {
		this.itemPriceField.setText(price);
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public Item getUpdatedObject() {
		return this.updatedItem;
	}
}