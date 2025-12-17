package main.java.gate;
import java.util.List;

public class NorGate extends Gate{
    public NorGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    public boolean evaluate(){
        for(Gate gate: getInputs()){
            if(gate.evaluate()){
                return false;
            }

        }
        return true;
    }
}
