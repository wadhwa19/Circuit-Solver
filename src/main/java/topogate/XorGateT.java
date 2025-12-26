package topogate;

import java.util.List;
import java.util.Map;



public class XorGateT extends GateT{
    public XorGateT (String id , List<GateT> inputs){
        super(id,inputs);
    }
    @Override
    public boolean evaluate(Map<GateT, Boolean> values){
        boolean a= values.get(getInputs().get(0));
        boolean b= values.get(getInputs().get(1));
        return a^b;
    }
    
}
