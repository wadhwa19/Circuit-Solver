package topogate;
import java.util.List;
import java.util.Map;

public abstract class GateT {
    private final String id;
    private final List<GateT> inputs;

    protected GateT(String id, List<GateT> inputs) {
        this.id = id;
        this.inputs = inputs;
    }

    public String getId() {
        return id;
    }

    public List<GateT> getInputs() {
        return inputs;
    }

    // Evaluate using values map (inputs must be already evaluated)
    public abstract boolean evaluate(Map<GateT, Boolean> values);
}



