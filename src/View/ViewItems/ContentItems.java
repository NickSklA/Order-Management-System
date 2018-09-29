package View.ViewItems;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;


public class ContentItems extends VBox {

    public ContentItems() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentItemsPane.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
