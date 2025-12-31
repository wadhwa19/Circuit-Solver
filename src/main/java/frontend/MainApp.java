package frontend;



import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import model.*;
import topogate.*;

public class MainApp extends Application {
   /*Stage window;
   Scene scene1, scene2;*/
    private CircuitT circuit;
    private Toposolver solver;
    private GateT outputGate;
    private VBox layout;
    private Label outputLabel;

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

        /*window =primaryStage;
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
        window.show();*/
        primaryStage.setTitle("Logic Circuit Evaluator");

        TextField expressionInput = new TextField();
        expressionInput.setPromptText("Enter logic expression (e.g., A AND B OR NOT C)");

        Button parseButton = new Button("Parse Expression");
        outputLabel = new Label("Output will appear here");

        layout = new VBox(10, expressionInput, parseButton, outputLabel);
        layout.setPrefWidth(400);

        parseButton.setOnAction(e -> parseExpression(expressionInput.getText()));

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void parseExpression(String expression) {
    try {
        circuit = new CircuitT();
        CircuitParser parser = new CircuitParser(circuit);

        GateT outputGateLocal = parser.parse(expression);
        this.outputGate = outputGateLocal;

        solver = new Toposolver();

        for (GateT gate : circuit.getAllGates()) {
            solver.addGate(gate.getId(), gate);
        }

        solver.createAdj();
        solver.toposort();

        layout.getChildren().removeIf(n -> n instanceof CheckBox);

        for (InputGateT input : circuit.getInputGates()) {
            CheckBox cb = new CheckBox(input.getId());
            cb.setSelected(input.getValue());
            cb.setOnAction(e -> {
                input.setValue(cb.isSelected());
                evaluateCircuit();
            });
            layout.getChildren().add(cb);
        }

        evaluateCircuit();

    } catch (Exception ex) {
        outputLabel.setText("Error parsing expression: " + ex.getMessage());
    }
}


    private void evaluateCircuit() {
        try {
            boolean result = solver.evaluate(outputGate);
            outputLabel.setText("Output: " + result);
        } catch (Exception ex) {
            outputLabel.setText("Error evaluating circuit: " + ex.getMessage());
        }
    }
       

    
    
}
