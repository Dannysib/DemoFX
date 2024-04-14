import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MenuDemoFX extends Application {

    private boolean isFirstPress = true;
    private TextField textField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Demo");

        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        MenuItem printDateTimeItem = new MenuItem("Print Date and Time");
        printDateTimeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printDateTime();
            }
        });

        MenuItem writeToFileItem = new MenuItem("Write to log.txt");
        writeToFileItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                writeToFile();
            }
        });

        MenuItem changeBackgroundColorItem = new MenuItem("Change Background Color");
        changeBackgroundColorItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeBackgroundColor(borderPane);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        menu.getItems().addAll(printDateTimeItem, writeToFileItem, changeBackgroundColorItem, exitItem);
        menuBar.getMenus().addAll(menu);

        borderPane.setTop(menuBar);

        textField = new TextField();
        textField.setPrefWidth(400);
        borderPane.setBottom(textField);

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void printDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date());
        textField.setText(dateTime);
    }

    private void writeToFile() {
        String content = textField.getText();
        try {
            FileWriter writer = new FileWriter("log.txt");
            writer.write(content);
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Text written to log.txt successfully.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error writing to file: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void changeBackgroundColor(BorderPane borderPane) throws Exception {
        if (isFirstPress) {
            borderPane.setStyle("-fx-background-color: green;");
            isFirstPress = false;
        } else {
            Random random = new Random();
            float hue = random.nextFloat() * 0.3f + 0.3f; // Limiting to a range of green hues
            Color color = Color.hsb(hue * 360, 0.6, 0.9);
            borderPane.setStyle("-fx-background-color: #" + color.toString().substring(2, 8) + ";");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
