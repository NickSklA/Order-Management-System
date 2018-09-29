package View.ViewBookedRooms;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ContentBookedRooms extends VBox{

    public ContentBookedRooms() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentBookedRoomsPane.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}