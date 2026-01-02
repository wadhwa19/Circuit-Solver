package topogate;
import java.util.List;
import java.util.Map;

public class NotGateT extends GateT{
    public NotGateT  (String id, List<GateT> inputs) {
        super(id, inputs);
    }

    @Override
    public boolean evaluate(Map<GateT, Boolean> values) {
        // NOT gate has only 1 input
        return !values.get(getInputs().get(0));
    }
}




