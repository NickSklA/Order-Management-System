package View.ViewItems.AddItem;


import DAO.AddDAO;
import Objects.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItem implements Initializable, AddDAO{

    @FXML
    private TextField itemNameField, itemPriceField, itemCategoryField;

    @FXML
    private Button itemSubmitBtn;

    private Item insertedItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemSubmitBtn.setOnAction(event -> {
            onSubmitClick();
        });
    }

    private void onSubmitClick() {
        if (isValid()) {
            insertedItem = new Item();

            insertedItem.setItemCategory(itemCategoryField.getText());
            insertedItem.setItemName(itemNameField.getText());
            insertedItem.setPrice(Double.parseDouble(itemPriceField.getText()));

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


    @Override
    public Object getObject() {
        return insertedItem;
    }
}
