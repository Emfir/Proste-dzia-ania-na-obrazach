package sample;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller_1 {


    public Button button;

    @FXML
    public void handleButton1(MouseEvent mouseEvent) throws IOException {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

            Parent root = (Parent) loader.load();
            Controller secondController = loader.getController();
            secondController.reciving(selectedFile);


            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
            newStage.show();

        }


    }

}
