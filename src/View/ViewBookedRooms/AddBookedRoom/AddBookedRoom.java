package View.ViewBookedRooms.AddBookedRoom;


import DAO.AddDAO;
import Objects.BookedRoom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookedRoom implements Initializable, AddDAO{

    @FXML
    private TextField roomNumberField;

    @FXML
    private DatePicker arrivalDatePicker;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Button addRoomSubmit;

    private BookedRoom insertedBookedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addRoomSubmit.setOnAction(event -> {
            onSubmitClick();
        });
    }

    private void onSubmitClick() {
        if (isValid()) {
            insertedBookedRoom = new BookedRoom();

            insertedBookedRoom.setRoomNumber(roomNumberField.getText());
            insertedBookedRoom.setArrivalDate(arrivalDatePicker.getValue().toString());
            insertedBookedRoom.setDepartureDate(departureDatePicker.getValue().toString());

            Stage stage = (Stage) addRoomSubmit.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    private boolean isValid() {
        boolean success = true;

        if (roomNumberField.getText().equals("")) {
            roomNumberField.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
            success = false;
        }
        else { roomNumberField.setStyle(null); }

        if (arrivalDatePicker.getValue() == null) {
            arrivalDatePicker.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
            success = false;
        }
        else { arrivalDatePicker.setStyle(null); }

        if (departureDatePicker.getValue() == null) {
            departureDatePicker.setStyle("-fx-text-box-border: red; -fx-focus-color: red; -fx-faint-focus-color: #d3524422");
            success = false;
        }
        else { departureDatePicker.setStyle(null); }

        return success;
    }

    @Override
    public Object getObject() {
        return insertedBookedRoom;
    }
}
