package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static final String first = "first.fxml";
    static final String second = "sample.fxml";
    static public String output = first;
    public Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(first));
      //  primaryStage.setTitle("Hello World");
       primaryStage.setScene(new Scene(root));
       stage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
