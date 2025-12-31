package frontend;

import java.awt.event.ActionEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;


public class MainApp extends Application {
   Stage window;
   Scene scene1, scene2;

    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception{
        /*primaryStage.setTitle("Title of window");
        button = new Button();
        button.setText("click");
        button.setOnAction(e-> System.out.println("hello"));
        
        

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene( layout, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();*/

        window =primaryStage;
        Label label1 = new Label("scene 1");
        Button button3 = new Button("to scene2");
        button3.setOnAction(e->window.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout.getChildren().addAll(label1,button3);
        scene1 = new Scene(layout1,200,200);

      Button button2 = new Button("to scene1");
        button2.setOnAction(e->window.setScene(scene1));
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 300,300);
        window.setScene(scene1);
        window.setTitle("lets go");
        window.show();
    }
    
       

    
    
}
