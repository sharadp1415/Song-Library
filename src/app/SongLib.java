/**
 * @author: Naman Bajaj and Sharad Prasad
 */

package app;

import view.ListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/library.fxml"));

        AnchorPane root = (AnchorPane) loader.load();

        ListController listController = loader.getController();
        listController.start(stage);

        Scene scene = new Scene(root, 610, 420);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}