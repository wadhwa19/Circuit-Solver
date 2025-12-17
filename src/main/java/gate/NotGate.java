package gate;

import java.util.List;

public  class NotGate extends Gate {
    public NotGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    @Override
    public boolean evaluate(){
        return !getInputs().get(0).evaluate();
    }
}
    

