package main.java.gate;
import java.util.List;

public class AndGate extends Gate{

    public AndGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    public boolean evaluate(){
        for(Gate gate: getInputs()){
            if(!gate.evaluate()){
                return false;
            }

        }
        return true;
    }
}
