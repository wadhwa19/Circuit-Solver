package topogate;
import java.util.Collections;
import java.util.Map;

public class InputGateT extends GateT{
     private final boolean value;

    public InputGateT(String id, boolean value) {
        super(id, Collections.emptyList());
        this.value = value;
    }

    @Override
    public boolean evaluate(Map<GateT, Boolean> values) {
        return value; // input is always known
    }
}




