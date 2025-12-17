package main.java.gate;
import java.util.List;

public abstract class Gate {
    private final String id;
    private final List<Gate> inputs;
    
    protected Gate(String id, List<Gate> inputs){
        this.id =id;
        this.inputs = inputs;

    }
    public String getId(){
        return id;

    }
    public List<Gate> getInputs(){
        return inputs;
    }

    public abstract boolean evaluate();
}
