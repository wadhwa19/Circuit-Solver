package frontend;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircuitCanvas extends Pane {
    private final List<Wire> wires = new ArrayList<>();
    private Pin selectedPin = null;
    private Line tempLine = null;
    
    // Track connections: which gates feed into which
    private final Map<VisualGate, List<VisualGate>> connections = new HashMap<>();

    public CircuitCanvas() {
        setPrefSize(800, 600);
        setStyle("-fx-background-color: #ebe0e0e0;");
    }

    public void addGate(VisualGate gate, double x, double y) {
        gate.setLayoutX(x);
        gate.setLayoutY(y);
        getChildren().add(gate);
        connections.put(gate, new ArrayList<>());

        // dragging functionality
        gate.setOnMousePressed(event -> {
            gate.setUserData(new double[]{
                event.getSceneX() - gate.getLayoutX(), 
                event.getSceneY() - gate.getLayoutY()
            });
        });
        
        gate.setOnMouseDragged(event -> {
            double[] offset = (double[]) gate.getUserData();
            gate.setLayoutX(event.getSceneX() - offset[0]);
            gate.setLayoutY(event.getSceneY() - offset[1]);
            wires.forEach(Wire::update);
        });
    }

    public void handlePinClick(Pin pin) {
        if (selectedPin == null) {
            // First click - start connection
            if (pin.getType() == Pin.PinType.OUTPUT) {
                selectedPin = pin;
                selectedPin.setStroke(Color.YELLOW);
                selectedPin.setStrokeWidth(3);
                
                tempLine = new Line();
                tempLine.setStroke(Color.BLUE);
                tempLine.setStrokeWidth(2);
                tempLine.getStrokeDashArray().addAll(5.0, 5.0);
                getChildren().add(tempLine);
                
                
                setOnMouseMoved(e -> {
                    if (tempLine != null && selectedPin != null) {
                        double startX = selectedPin.getOwner().getLayoutX() + 
                                      selectedPin.getCenterX();
                        double startY = selectedPin.getOwner().getLayoutY() + 
                                      selectedPin.getCenterY();
                        tempLine.setStartX(startX);
                        tempLine.setStartY(startY);
                        tempLine.setEndX(e.getX());
                        tempLine.setEndY(e.getY());
                    }
                });
                
                // Click anywhere on canvas to cancel
                setOnMouseClicked(e -> {
                    if (e.getTarget() == this) {
                        cancelConnection();
                    }
                });
            }
        } else {
            // Second click - complete connection
            if (pin.getType() == Pin.PinType.INPUT && 
                pin.getOwner() != selectedPin.getOwner()) {
                connectGates(selectedPin.getOwner(), pin.getOwner());
            }
            cancelConnection();
        }
    }
    
    private void cancelConnection() {
        // remove  temp  connection line
        if (tempLine != null) {
            getChildren().remove(tempLine);
            tempLine = null;
        }
        
        // Reset selected pin appearance
        if (selectedPin != null) {
            selectedPin.setStroke(Color.BLACK);
            selectedPin.setStrokeWidth(2);
            selectedPin = null;
        }
        
        setOnMouseMoved(null);
        setOnMouseClicked(null);
    }

    private void connectGates(VisualGate from, VisualGate to) {
        // Create a wire
        Wire wire = new Wire(from, to);
        wires.add(wire);
        getChildren().add(0, wire); // put it behind gates
        
        // Track connection between wires
        connections.get(to).add(from);
    }

    public Map<VisualGate, List<VisualGate>> getConnections() {
        return connections;
    }

    public List<Wire> getWires() {
        return wires;
    }
    
    public void removeGate(VisualGate gate) {
        // Remove all wires connected to this gate
        List<Wire> wiresToRemove = new ArrayList<>();
        for (Wire wire : wires) {
            if (wire.getFromGate() == gate || wire.getToGate() == gate) {
                wiresToRemove.add(wire);
            }
        }
        
        for (Wire wire : wiresToRemove) {
            wires.remove(wire);
            getChildren().remove(wire);
        }
        
        // Remove from connections map
        connections.remove(gate);
        
        // Remove this gate from other gates' input lists
        for (List<VisualGate> inputs : connections.values()) {
            inputs.remove(gate);
        }
        
        // Remove visual gate from canvas
        getChildren().remove(gate);
    }
}