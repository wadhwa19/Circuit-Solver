package gate;

import java.util.List;

public class OrGate extends Gate {
    public OrGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    @Override
    public boolean evaluate(){
        for(Gate gate: getInputs()){
            if(gate.evaluate()){
                return true;
            }

        }
        return false;
    }

}
