package topogate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InputGateT extends GateT {
    private boolean value;  // mutable

    public InputGateT(String id, boolean value) {
        super(id, Collections.emptyList()); // no inputs
        this.value = value;
    }

    @Override
    public boolean evaluate(Map<GateT, Boolean> values) {
        return value;
    }

    // New method to update input dynamically
    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public List<GateT> getInputs() {
        return Collections.emptyList();
    }
}
