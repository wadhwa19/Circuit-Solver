package gate;
import java.util.List;

public class NorGate extends Gate{
    public NorGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    @Override
    public boolean evaluate(){
        for(Gate gate: getInputs()){
            if(gate.evaluate()){
                return false;
            }

        }
        return true;
    }
}
