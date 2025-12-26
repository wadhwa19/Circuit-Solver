package topogate;

import java.util.List;
import java.util.Map;

public class NandGateT extends GateT {
    public NandGateT(String id, List<GateT> inputs) {
        super(id, inputs);
    }

    @Override
    public boolean evaluate(Map<GateT, Boolean> values) {
        for (GateT gate : getInputs()) {
            if (!values.get(gate)) {
                return true; // if any input is false, NAND = true
            }
        }
        return false; // all inputs are true → NAND = false
    }
}
