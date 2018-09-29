package View.ViewDashboard;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ContentDashboard extends VBox{

    public ContentDashboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentDashboardPane.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
