package frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CircuitT;
import topogate.GateT;
import topogate.InputGateT;

import java.util.List;
import java.util.Map;

public class MainApp extends Application {

    private CircuitCanvas canvas;
    private CircuitT circuit;
    private int gateCounter = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visual Logic Circuit Editor");

        circuit = new CircuitT();
        canvas = new CircuitCanvas();

        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        // Toolbar with all gate types
        ToolBar toolbar = new ToolBar();
        Button addInput = new Button("INPUT");
        Button addAnd = new Button("AND");
        Button addOr = new Button("OR");
        Button addNot = new Button("NOT");
        Button addNand = new Button("NAND");
        Button addNor = new Button("NOR");
        Button addXor = new Button("XOR");
        
        Separator sep = new Separator();
        
        Button evalBtn = new Button("Evaluate Circuit");
        Button clearBtn = new Button("Clear All");

        toolbar.getItems().addAll(
            addInput, addAnd, addOr, addNot, addNand, addNor, addXor,
            sep, evalBtn, clearBtn
        );
        root.setTop(toolbar);

        // Instructions panel
        VBox instructions = new VBox(10);
        instructions.setPadding(new Insets(10));
        instructions.setPrefWidth(200);
        Label title = new Label("Instructions:");
        title.setStyle("-fx-font-weight: bold;");
        Label inst1 = new Label("1. Click gate buttons to add gates");
        Label inst2 = new Label("2. Drag gates to position");
        Label inst3 = new Label("3. Click OUTPUT pin (red), then INPUT pin (blue) to connect");
        Label inst4 = new Label("4. Click canvas to cancel connection");
        Label inst5 = new Label("5. Right-click INPUT gates to toggle value");
        Label inst6 = new Label("6. Right-click any gate and select 'Set as Output'");
        Label inst7 = new Label("7. Click 'Evaluate Circuit'");
        
        inst1.setWrapText(true);
        inst2.setWrapText(true);
        inst3.setWrapText(true);
        inst4.setWrapText(true);
        inst5.setWrapText(true);
        inst6.setWrapText(true);
        inst7.setWrapText(true);
        
        instructions.getChildren().addAll(title, inst1, inst2, inst3, inst4, inst5, inst6, inst7);
        root.setRight(instructions);

        // Button actions
        addInput.setOnAction(e -> addGate(GateType.INPUT));
        addAnd.setOnAction(e -> addGate(GateType.AND));
        addOr.setOnAction(e -> addGate(GateType.OR));
        addNot.setOnAction(e -> addGate(GateType.NOT));
        addNand.setOnAction(e -> addGate(GateType.NAND));
        addNor.setOnAction(e -> addGate(GateType.NOR));
        addXor.setOnAction(e -> addGate(GateType.XOR));

        evalBtn.setOnAction(e -> evaluateCircuit());
        clearBtn.setOnAction(e -> clearCircuit());

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addGate(GateType type) {
        String id = type.name() + "_" + (gateCounter++);
        VisualGate vGate = new VisualGate(type, id, circuit);
        
        // Add "Set as Output Gate" and "Delete" options to the gate's existing menu
        ContextMenu menu = vGate.getContextMenu();
        if (menu != null) {
            MenuItem setOutput = new MenuItem("Set as Output Gate");
            setOutput.setOnAction(e -> {
                circuit.setOutputGate(vGate.getGateT());
                showAlert("Output gate set to: " + id);
            });
            menu.getItems().add(setOutput);
            
            SeparatorMenuItem sep = new SeparatorMenuItem();
            menu.getItems().add(sep);
            
            MenuItem delete = new MenuItem("Delete Gate");
            delete.setOnAction(e -> {
                deleteGate(vGate);
            });
            menu.getItems().add(delete);
        }
        
        canvas.addGate(vGate, 100 + Math.random() * 200, 100 + Math.random() * 300);
    }
    
    private void deleteGate(VisualGate vGate) {
        // Remove from canvas (also removes wires)
        canvas.removeGate(vGate);
        
        // Remove from circuit backend
        // Note: We can't easily remove from CircuitT's internal maps,
        // but it won't affect evaluation since disconnected gates won't be in the topo sort
        
        // If this was the output gate, clear it
        if (circuit.getOutputGate() == vGate.getGateT()) {
            circuit.setOutputGate(null);
        }
    }

    private void evaluateCircuit() {
        try {
            // Rebuild gate connections based on visual wires
            Map<VisualGate, List<VisualGate>> connections = canvas.getConnections();
            
            for (Map.Entry<VisualGate, List<VisualGate>> entry : connections.entrySet()) {
                VisualGate toGate = entry.getKey();
                List<VisualGate> fromGates = entry.getValue();
                
                GateT toBackend = toGate.getGateT();
                toBackend.getInputs().clear();
                
                for (VisualGate fromGate : fromGates) {
                    toBackend.getInputs().add(fromGate.getGateT());
                }
            }
            
            // Build topology and evaluate
            circuit.buildTopo();
            GateT outputGate = circuit.getOutputGate();
            
            if (outputGate == null) {
                showAlert("Please set an output gate first!\n(Right-click a gate and select 'Set as Output Gate')");
                return;
            }
            
            boolean result = circuit.evaluate(outputGate);
            
            // Show input states
            StringBuilder inputStates = new StringBuilder("Input States:\n");
            for (InputGateT input : circuit.getInputGates()) {
                inputStates.append(input.getId())
                          .append(" = ")
                          .append(input.getValue())
                          .append("\n");
            }
            
            showAlert(inputStates.toString() + "\nOutput: " + result);
            
        } catch (Exception ex) {
            showAlert("Error evaluating circuit:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void clearCircuit() {
        canvas.getChildren().clear();
        circuit = new CircuitT();
        gateCounter = 0;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Circuit Evaluation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}