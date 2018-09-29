package View.ViewRoomInfo;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ContentRoomInfo extends VBox{

    public ContentRoomInfo() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentRoomInfoPane.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
