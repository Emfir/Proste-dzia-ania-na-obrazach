package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import javafx.util.FXPermission;

public class twoPictures implements Initializable {
  //  public FXPermission obiekt = new FXPermission("setWindowAlwaysOnTop");
    ;
    public ImageView wind2;
    public ImageView wind1;

    javafx.scene.image.Image first, second;


    @FXML

    public void reciving(javafx.scene.image.Image a, Image b) {
        first = a;
        second = b;
        wind1.setImage(first);
        wind2.setImage(second);
    }


    @FXML
    public void dodawanie(MouseEvent mouseEvent) throws IOException {


    }

    @FXML
    public void mnorzenie(MouseEvent mouseEvent) throws IOException {


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void mnoz(MouseEvent mouseEvent) {
        PixelReader krata1 = first.getPixelReader();
        PixelReader krata2 = second.getPixelReader();
        int x1 = (int) first.getWidth();
        int x2 = (int) second.getWidth();
        int y1 = (int) first.getHeight();
        int y2 = (int) second.getHeight();
        WritableImage goBack = new WritableImage(x1, y1);
        PixelWriter dodaj = goBack.getPixelWriter();
        if (x1 == x2 && y1 == y2) {
            for (int x = 0; x < x1; x++) {
                for (int y = 0; y < y1; y++) {
                    dodaj.setColor(x, y, Color.color((krata1.getColor(x, y).getRed() * krata2.getColor(x, y).getRed()),
                            (krata1.getColor(x, y).getGreen() * krata2.getColor(x, y).getGreen()) ,
                            (krata1.getColor(x, y).getBlue() * krata2.getColor(x, y).getBlue())
                    ));
                }
            }
            first = goBack;
            wind1.setImage(first);
        }

    }

    public void dodaj(MouseEvent mouseEvent) {
        PixelReader krata1 = first.getPixelReader();
        PixelReader krata2 = second.getPixelReader();
        int x1 = (int) first.getWidth();
        int x2 = (int) second.getWidth();
        int y1 = (int) first.getHeight();
        int y2 = (int) second.getHeight();
        WritableImage goBack = new WritableImage(x1, y1);
        PixelWriter dodaj = goBack.getPixelWriter();
        if (x1 == x2 && y1 == y2) {
            for (int x = 0; x < x1; x++) {
                for (int y = 0; y < y1; y++) {
                    dodaj.setColor(x, y, Color.color((krata1.getColor(x, y).getRed() + krata2.getColor(x, y).getRed()) * 0.5,
                            (krata1.getColor(x, y).getGreen() + krata2.getColor(x, y).getGreen()) * 0.5,
                            (krata1.getColor(x, y).getBlue() + krata2.getColor(x, y).getBlue()) * 0.5
                    ));
                }
            }
            first = goBack;
            wind1.setImage(first);
        }
    }
}
