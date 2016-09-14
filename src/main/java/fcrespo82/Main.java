package fcrespo82;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by fxcrespo on 14/09/16.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tocou na 89 fm");

        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
