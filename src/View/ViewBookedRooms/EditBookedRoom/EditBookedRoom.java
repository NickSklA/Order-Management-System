package View.ViewBookedRooms.EditBookedRoom;

import DAO.EditDAO;
import Objects.BookedRoom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.print.Book;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditBookedRoom implements Initializable, EditDAO{

    @FXML
    private TextField roomNumberField;

    @FXML
    private DatePicker arrivalDatePicker;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Button bookedRoomSubmit;

    private int bookedRoomID;
    private BookedRoom updatedbookedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookedRoomSubmit.setOnAction(event -> {
            onSubmitClick();
        });

    }

    private void onSubmitClick() {
        if (isValid()) {
            updatedbookedRoom = new BookedRoom();

            updatedbookedRoom.setRoomID(bookedRoomID);
            updatedbookedRoom.setRoomNumber(roomNumberField.getText());
            updatedbookedRoom.setArrivalDate(arrivalDatePicker.getValue().toString());
            updatedbookedRoom.setDepartureDate(departureDatePicker.getValue().toString());

            Stage stage = (Stage) bookedRoomSubmit.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    public boolean isValid() {
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

    public void setRoomNumberField(String roomNumber) {
        this.roomNumberField.setText(roomNumber);
    }

    public void setArrivalDatePicker(String arrivalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(arrivalDate, formatter);

        this.arrivalDatePicker.setValue(localDate);
    }

    public void setDepartureDatePicker(String departureDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(departureDate, formatter);

        this.departureDatePicker.setValue(localDate);
    }

    public void setBookedRoomID(int bookedRoomID) {
        this.bookedRoomID = bookedRoomID;
    }

    @Override
    public Object getUpdatedObject() {
        return updatedbookedRoom;
    }
}