package frontend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Wire extends Line {
    private final VisualGate from;
    private final VisualGate to;

    public Wire(VisualGate from, VisualGate to) {
        this.from = from;
        this.to = to;
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        update();
    }

    public void update() {
        setStartX(from.getLayoutX() + 80); // right side of from gate
        setStartY(from.getLayoutY() + 25);
        setEndX(to.getLayoutX()); // left side of to gate
        setEndY(to.getLayoutY() + 25);
    }
    
    public VisualGate getFromGate() {
        return from;
    }
    
    public VisualGate getToGate() {
        return to;
    }
}