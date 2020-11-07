package invmgr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <h1>Main</h1>
 * This class handles the application bootstrap for inventory manager.
 * <p>
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    /**
     * This method is used initialize the application's stage.
     * @param primaryStage This is the Stage of the app
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/StartScreen.fxml"));
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This method is the class constructor.
     * @param args constructor arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}