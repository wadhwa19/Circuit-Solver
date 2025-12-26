package topogate;
import java.util.List;
import java.util.Map;
public class NorGateT extends GateT{
   public NorGateT(String id, List<GateT> inputs) {
        super(id, inputs);
    }

    @Override
    public boolean evaluate(Map<GateT, Boolean> values) {
        for (GateT g : getInputs()) {
            if (values.get(g)) return false;
        }
        return true;
    } 
}
