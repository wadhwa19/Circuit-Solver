package frontend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pin extends Circle {

    public enum PinType {
        INPUT,
        OUTPUT
    }

    private final PinType type;
    private final VisualGate owner;

    public Pin(PinType type, VisualGate owner) {
        super(8); // Increased radius from 5 to 8 for better visibility
        this.type = type;
        this.owner = owner;

        setFill(type == PinType.INPUT ? Color.DODGERBLUE : Color.CRIMSON);
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        
        // Make it easier to click by detecting clicks in a larger area
        setPickOnBounds(true);

        setOnMouseClicked(e -> {
            CircuitCanvas canvas = (CircuitCanvas) owner.getParent();
            if (canvas != null) {
                canvas.handlePinClick(this);
            }
            e.consume();
        });
        
        // Add hover effect for better UX
        setOnMouseEntered(e -> {
            setScaleX(1.5);
            setScaleY(1.5);
            setStrokeWidth(3);
        });
        
        setOnMouseExited(e -> {
            setScaleX(1.0);
            setScaleY(1.0);
            // Don't reset stroke width if this pin is selected
            if (getStroke() != Color.YELLOW) {
                setStrokeWidth(2);
            }
        });
    }

    public PinType getType() {
        return type;
    }

    public VisualGate getOwner() {
        return owner;
    }

    /** Visual feedback during evaluation */
    public void setSignal(boolean value) {
        setFill(value ? Color.LIMEGREEN : Color.DARKGRAY);
    }
}