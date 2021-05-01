package fxTesting;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Main extends Application {
    @Override
    public void init() throws Exception{
        System.out.println("Runs before start function");
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("JavaFX testing!");
        // Change Width and Height of the stage
        stage.setWidth(500);
        stage.setHeight(500);
        // Change start position of the stage
        stage.setX(500); // Left -> Right
        stage.setY(200); // Up -> Down

        VBox parent = new VBox();
        Label label1 = new Label("This is a first text label"); // child node
        Label label2 = new Label("This is a second text label");
        // parent.getChildren().add(label1);
        parent.getChildren().addAll(label1, label2);
        Scene scene1 = new Scene(parent);
        // scene1.setCursor(Cursor.CROSSHAIR); // different cursor
        stage.setScene(scene1);

        stage.show();

        //Stage stage2 = new Stage();
        //stage2.setTitle("Second window");

        //Modality
        // stage2.initModality(Modality.APPLICATION_MODAL); // Other windows cannot be used until this closed
        //stage2.show();
    }

    @Override
    public void stop() throws Exception{
        System.out.println("Runs after start's end");
    }

    public static void main(String[] args) {
        launch();
    }
}
