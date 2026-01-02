package frontend;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import model.CircuitT;
import topogate.*;

public class VisualGate extends Group {

    private static final double WIDTH = 80;
    private static final double HEIGHT = 50;

    private final GateType type;
    private final GateT gate;
    private final Pin outputPin;
    private final Pin inputPin1;
    private Pin inputPin2 = null;

    public VisualGate(GateType type, String label, CircuitT circuit) {
        this.type = type;
        this.gate = createBackendGate(type, label, circuit);
        
        Shape body = drawGate(label);
        body.setFill(Color.LIGHTGRAY);
        body.setStroke(Color.BLACK);

        Text text = new Text(label);
        text.setX(5);
        text.setY(HEIGHT / 1.8);
        text.setStyle("-fx-font-size: 9px;");

        getChildren().addAll(body, text);

        // context menu based on gate type
        setupContextMenu(body);

        // Added pins based on gate type for input and output
        if (type == GateType.INPUT) {
            // Input gate: only output pin
            outputPin = new Pin(Pin.PinType.OUTPUT, this);
            outputPin.setCenterX(WIDTH / 2 + 15);
            outputPin.setCenterY(HEIGHT / 4);
            getChildren().add(outputPin);
            inputPin1 = null;
        } else if (type == GateType.NOT) {
            // NOT gate: 1 input, 1 output
            inputPin1 = new Pin(Pin.PinType.INPUT, this);
            inputPin1.setCenterX(-8); // Position outside gate
            inputPin1.setCenterY(HEIGHT / 2);
            
            outputPin = new Pin(Pin.PinType.OUTPUT, this);
            outputPin.setCenterX(WIDTH + 8); // Position outside gate
            outputPin.setCenterY(HEIGHT / 2);
            
            getChildren().addAll(inputPin1, outputPin);
        } else {
            // Binary gates: 2 inputs, 1 output
            inputPin1 = new Pin(Pin.PinType.INPUT, this);
            inputPin1.setCenterX(-8); // Position outside gate
            inputPin1.setCenterY(HEIGHT / 3);
            
            inputPin2 = new Pin(Pin.PinType.INPUT, this);
            inputPin2.setCenterX(-8); // Position outside gate
            inputPin2.setCenterY(2 * HEIGHT / 3);
            
            outputPin = new Pin(Pin.PinType.OUTPUT, this);
            outputPin.setCenterX(WIDTH + 15); // Position outside gate
            outputPin.setCenterY(HEIGHT / 2);
            
            getChildren().addAll(inputPin1, inputPin2, outputPin);
        }
    }
    
    private GateT createBackendGate(GateType type, String label, CircuitT circuit) {
        GateT g;
        switch (type) {
            case AND -> g = new AndGateT(label, new java.util.ArrayList<>());
            case OR -> g = new OrGateT(label, new java.util.ArrayList<>());
            case NOT -> g = new NotGateT(label, new java.util.ArrayList<>());
            case NAND -> g = new NandGateT(label, new java.util.ArrayList<>());
            case NOR -> g = new NorGateT(label, new java.util.ArrayList<>());
            case XOR -> g = new XorGateT(label, new java.util.ArrayList<>());
            case INPUT -> g = new InputGateT(label, false);
            default -> throw new IllegalStateException("Unexpected gate type: " + type);
        }
        circuit.addGate(label, g);
        return g;
    }

    private Shape drawGate(String label) {
        switch (type) {
            case AND -> { return drawAnd(); }
            case OR -> { return drawOr(); }
            case NOT -> { return drawNot(); }
            case NAND -> { return drawNand(); }
            case NOR -> { return drawNor(); }
            case XOR -> { return drawXor(); }
            case INPUT -> { return drawInput(); }
            default -> throw new IllegalStateException("Unexpected gate: " + type);
        }
    }

    private Shape drawAnd() {
        Rectangle rect = new Rectangle(0, 0, WIDTH / 2, HEIGHT);
        Arc arc = new Arc(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2, -90, 180);
        arc.setType(ArcType.CHORD);
        return Shape.union(rect, arc);
    }

    private Shape drawOr() {
        Path path = new Path();
        path.getElements().addAll(
            new MoveTo(0, 0),
            new QuadCurveTo(WIDTH / 2, HEIGHT / 2, 0, HEIGHT),
            new QuadCurveTo(WIDTH, HEIGHT, WIDTH, HEIGHT / 2),
            new QuadCurveTo(WIDTH, 0, 0, 0)
        );
        return path;
    }

    private Shape drawNot() {
        Polygon triangle = new Polygon(0, 0, 0, HEIGHT, WIDTH - 10, HEIGHT / 2);
        Circle bubble = new Circle(WIDTH - 5, HEIGHT / 2, 5);
        return Shape.union(triangle, bubble);
    }

    private Shape drawNand() {
        Shape and = drawAnd();
        Circle bubble = new Circle(WIDTH + 5, HEIGHT / 2, 5);
        return Shape.union(and, bubble);
    }

    private Shape drawNor() {
        Shape or = drawOr();
        Circle bubble = new Circle(WIDTH + 5, HEIGHT / 2, 5);
        return Shape.union(or, bubble);
    }

    private Shape drawXor() {
        Path main = new Path();
        main.getElements().addAll(
            new MoveTo(10, 0),
            new QuadCurveTo(WIDTH / 2 + 10, HEIGHT / 2, 10, HEIGHT),
            new QuadCurveTo(WIDTH + 10, HEIGHT, WIDTH + 10, HEIGHT / 2),
            new QuadCurveTo(WIDTH + 10, 0, 10, 0)
        );
        Path extra = new Path();
        extra.getElements().addAll(
            new MoveTo(0, 0),
            new QuadCurveTo(5, HEIGHT / 2, 0, HEIGHT)
        );
        extra.setStroke(Color.BLACK);
        extra.setFill(null);
        return Shape.union(main, extra);
    }

    private Shape drawInput() {
        Rectangle rect = new Rectangle(0, 0, WIDTH / 2, HEIGHT / 2);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        return rect;
    }

    public GateType getType() {
        return type;
    }

    public GateT getGateT() {
        return gate;
    }

    public Pin getOutputPin() {
        return outputPin;
    }

    private void setupContextMenu(Shape body) {
        ContextMenu menu = new ContextMenu();
        
        if (type == GateType.INPUT) {
            // For INPUT gates: toggle value
            MenuItem toggle = new MenuItem("Toggle Value (Currently: false)");
            toggle.setOnAction(e -> {
                InputGateT input = (InputGateT) gate;
                input.setValue(!input.getValue());
                body.setFill(input.getValue() ? Color.LIGHTGREEN : Color.LIGHTGRAY);
                toggle.setText("Toggle Value (Currently: " + input.getValue() + ")");
            });
            menu.getItems().add(toggle);
            
            SeparatorMenuItem sep = new SeparatorMenuItem();
            menu.getItems().add(sep);
        }
        
        // Storing reference for MainApp to use 
        this.setUserData(menu);
        
        this.setOnContextMenuRequested(e -> {
            menu.show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }
    
    public ContextMenu getContextMenu() {
        return (ContextMenu) getUserData();
    }
    
    public String getGateId() {
        return gate.getId();
    }
}