package gate;
import java.util.List;

public class XorGate extends Gate{
    public XorGate (String id , List<Gate> inputs){
        super(id,inputs);
    }
    @Override
    public boolean evaluate(){
        boolean a= getInputs().get(0).evaluate();
        boolean b= getInputs().get(1).evaluate();
        return a^b;
    }
    
}
