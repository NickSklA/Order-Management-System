package View.ViewRoomInfo.EditRoomInfo.CheckOutRoom;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckoutRoom implements Initializable{

    @FXML
    private Label totalDebtsLabel;

    @FXML
    private JFXCheckBox totalDebtsCheckBox;

    @FXML
    private Label barDebtsLabel;

    @FXML
    private JFXCheckBox barDebtsCheckBox;

    @FXML
    private Label extraDebtsLabel;

    @FXML
    private JFXCheckBox extraDebtsCheckBox;

    @FXML
    private JFXButton checkoutRoomBtn;

    private String checkout = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalDebtsCheckBox.setText("");
        barDebtsCheckBox.setText("");
        extraDebtsCheckBox.setText("");

        checkoutRoomBtn.setOnAction(event -> {
            if (!openDialogMessage())
                return;

            // set choice checkout
            setCheckout();

            // close stage
            Stage stage = (Stage) checkoutRoomBtn.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        });
    }

    public void onTotalDebtsCkeckBoxClick() {
        if (totalDebtsCheckBox.isSelected()) {
            barDebtsCheckBox.setSelected(true);
            extraDebtsCheckBox.setSelected(true);
        }
        else {
            barDebtsCheckBox.setSelected(false);
            extraDebtsCheckBox.setSelected(false);
        }
    }

    public void onCheckBoxClick() {
        if (!barDebtsCheckBox.isSelected() || !extraDebtsCheckBox.isSelected()) {
            totalDebtsCheckBox.setSelected(false);
        }
        else if (barDebtsCheckBox.isSelected() || extraDebtsCheckBox.isSelected()) {
            totalDebtsCheckBox.setSelected(true);
        }
    }

    public void setTotalDebtsLabel(String totalDebts) {
        this.totalDebtsLabel.setText(totalDebts);
    }

    public void setBarDebtsLabel(String barDebts) {
        this.barDebtsLabel.setText(barDebts);
    }

    public void setExtraDebtsLabel(String extraDebts) {
        this.extraDebtsLabel.setText(extraDebts);
    }

    private void setCheckout() {
        this.checkout = getChoice();
    }

    public String getCheckout() {
        return checkout;
    }

    private String getChoice() {
        if (totalDebtsCheckBox.isSelected()) {
            return  "All Categories";
        }
        else if (barDebtsCheckBox.isSelected()) {
            return "Bar Category";
        }
        else if (extraDebtsCheckBox.isSelected()) {
            return "Extra Category";
        }
        return null;
    }

    private boolean openDialogMessage() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirm Check Out");
        alert.setHeaderText(null);
        alert.setContentText("Confirm checkout " + getChoice() + " ?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get().equals(yesBtn);
    }
}
