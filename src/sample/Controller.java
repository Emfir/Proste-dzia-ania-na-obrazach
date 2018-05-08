package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

//import javafx.util.FXPermission;

public class Controller implements Initializable {


    public ImageView obraz;
    public Image image;
    public Image secondImage;
    public Button odniesienie;
  //  public FXPermission obiekt = new FXPermission("setWindowAlwaysOnTop");
    public Button gray;
    public Button binary;
    public ScrollBar dodawanieStalej;
    public TextField mnorzPrzezStal;
    public LineChart lineChart;


    public void isGray(boolean a) {
        gray.setVisible(!a);
        binary.setVisible(a);
        dodawanieStalej.setVisible(a);
    }

    @FXML

    public void reciving(File file) {
        lineChart.setCreateSymbols(false);

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            obraz.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        histogram();
        isGray(false);

    }

    @FXML

    public void RGB2Grey(MouseEvent mouseEvent) throws IOException {
        PixelReader pixelReader = image.getPixelReader();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage grayImage = new WritableImage(width, height);


        // Put this outside the loops
        // so we have easier access to the writer.
        PixelWriter pixelWriter = grayImage.getPixelWriter();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, pixelReader.getColor(x, y).grayscale());
            }
        }

        image = grayImage;
        obraz.setImage(image);
        isGray(true);
        histogram();
    }

    @FXML

    public void handleButton1(MouseEvent mouseEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", ".jpeg")


        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            obraz.setImage(image);

            isGray(false);

        }
        histogram();
    }

    @FXML
    public void drugiObrazek(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("twoPictures.fxml"));
        Parent root = loader.load();
        twoPictures controler = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Stage current = (Stage) odniesienie.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")


        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            secondImage = SwingFXUtils.toFXImage(bufferedImage, null);
            controler.reciving(image, secondImage);
        }

        stage.show();


        stage.alwaysOnTopProperty();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void Negatyw(MouseEvent mouseEvent) {
        PixelReader pixelReader = image.getPixelReader();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage negative = new WritableImage(width, height);


        // Put this outside the loops
        // so we have easier access to the writer.
        PixelWriter pixelWriter = negative.getPixelWriter();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, pixelReader.getColor(x, y).invert());
            }
        }

        image = negative;
        histogram();
        obraz.setImage(image);
    }

    public void histogram(){
        lineChart.getData().clear();
        PixelReader pixelReader = image.getPixelReader();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage hist = new WritableImage(width, height);

        XYChart.Series seriesRed;
        XYChart.Series seriesGreen;
        XYChart.Series seriesBlue;


        PixelWriter pixelWriter = hist.getPixelWriter();
        int red[] = new int[256];
        int green[] = new int[256];
        int blue[] = new int[256];


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int redIndex = (int)Math.round(pixelReader.getColor(x, y).getRed() * 255);
                red[redIndex]++;
                int greenIndex = (int)Math.round(pixelReader.getColor(x, y).getGreen() * 255);
                green[greenIndex]++;
                int blueIndex = (int)Math.round(pixelReader.getColor(x, y).getBlue() * 255);
                blue[blueIndex]++;
            }
        }

        seriesRed = new XYChart.Series();
        seriesGreen = new XYChart.Series();
        seriesBlue = new XYChart.Series();
        seriesRed.setName("red");
        seriesGreen.setName("green");
        seriesBlue.setName("blue");

        for (int i = 0; i < 256; i++) {
            seriesRed.getData().add(new XYChart.Data(String.valueOf(i), red[i]));
            seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), green[i]));
            seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), blue[i]));
        }
        lineChart.getData().addAll(seriesBlue,seriesGreen,seriesRed);



    }

    public void Binaryzacja(MouseEvent mouseEvent) {
        double suma;
        try {
            Scanner scan = new Scanner(new BufferedReader(new StringReader(mnorzPrzezStal.getCharacters().toString())));
            if (scan.hasNextDouble() ) {
                suma = scan.nextDouble()/ 255;
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                PixelReader pixelReader = image.getPixelReader();

                WritableImage dodane = new WritableImage(width, height);

                double colors;

                PixelWriter pixelWriter = dodane.getPixelWriter();

                try {

                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            pixelWriter.setColor(x, y, pixelReader.getColor(x, y).grayscale());
                            colors = pixelReader.getColor(x, y).getRed();
                            //System.out.println(colors);
                            if (colors > suma)
                                pixelWriter.setColor(x,y , Color.BLACK);
                            else
                                pixelWriter.setColor(x, y, Color.WHITE);

                        }
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception);
                }
                obraz.setImage(dodane);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("zakonczyl binaryzacje");

        histogram();

    }

    public void procescDodawania(ScrollEvent scrollEvent) {
        System.out.println(dodawanieStalej.getValue());
        PixelReader pixelReader = image.getPixelReader();
        histogram();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage dodane = new WritableImage(width, height);

        double red, green, blue;

        double suma;
        PixelWriter pixelWriter = dodane.getPixelWriter();
        try {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    suma = dodawanieStalej.getValue();
                    red = pixelReader.getColor(x, y).getRed() + suma;

                    if (red > 1)
                        red = 1;
                    else if (red < 0)
                        red = 0;

                    suma = dodawanieStalej.getValue();
                    //System.out.println(suma);

                    pixelWriter.setColor(x, y, Color.color(red, red, red));
                }
            }
        } catch (RuntimeException exception) {
            System.out.println(exception);
        }
        System.out.println(dodawanieStalej.getValue());
        obraz.setImage(dodane);

    }


    public void mnorzeniePrzezStala(MouseEvent mouseEvent) {

        double suma;
        try {
            Scanner scan = new Scanner(new BufferedReader(new StringReader(mnorzPrzezStal.getCharacters().toString())));
            if (scan.hasNextDouble()) {
                suma = scan.nextDouble();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                PixelReader pixelReader = image.getPixelReader();

                WritableImage dodane = new WritableImage(width, height);

                double[] colors = new double[3];

                PixelWriter pixelWriter = dodane.getPixelWriter();

                try {

                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            colors[0] = pixelReader.getColor(x, y).getRed();
                            colors[1] = pixelReader.getColor(x, y).getGreen();
                            colors[2] = pixelReader.getColor(x, y).getBlue();

                            for (int i = 0; i < 3; i++) {
                                colors[i] *= suma;
                                if (colors[i]> 1)
                                    colors[i]= 1;
                                else if (colors[i]< 0)
                                    colors[i]= 0;
                              //  System.out.println(colors[i]);
                            }







                            pixelWriter.setColor(x, y, Color.color(colors[0], colors[1], colors[2]));
                        }
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception);
                }
                obraz.setImage(dodane);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("zakonczyl mnorzenie");
        histogram();
    }

    public void korGamma(MouseEvent mouseEvent) {

        double suma;
        try {
            Scanner scan = new Scanner(new BufferedReader(new StringReader(mnorzPrzezStal.getCharacters().toString())));
            if (scan.hasNextDouble()) {
                suma = scan.nextDouble();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                PixelReader pixelReader = image.getPixelReader();

                WritableImage dodane = new WritableImage(width, height);

                double[] colors = new double[3];

                PixelWriter pixelWriter = dodane.getPixelWriter();

                try {

                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            colors[0] = pixelReader.getColor(x, y).getRed();
                            colors[1] = pixelReader.getColor(x, y).getGreen();
                            colors[2] = pixelReader.getColor(x, y).getBlue();

                            for (int i = 0; i < 3; i++) {
                                colors[i] = Math.pow(colors[i], suma);
                                if (colors[i]> 1)
                                    colors[i]= 1;
                                else if (colors[i]< 0)
                                    colors[i]= 0;
                                //  System.out.println(colors[i]);
                            }







                            pixelWriter.setColor(x, y, Color.color(colors[0], colors[1], colors[2]));
                        }
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception);
                }
                obraz.setImage(dodane);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("zakonczyl korekcje");
        histogram();
    }
}
