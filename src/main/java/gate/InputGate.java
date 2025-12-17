package gate;
import java.util.Collections;

public class InputGate extends Gate{
    private final boolean value;
    
    public InputGate(String id,boolean value){
        super(id, Collections.emptyList());
        this.value=value;
    }
    @Override
    public boolean evaluate(){
        return value;
    }
    
}
