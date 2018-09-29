package View.ViewOrders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ContentOrders extends VBox{

    public ContentOrders() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentOrdersPane.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
