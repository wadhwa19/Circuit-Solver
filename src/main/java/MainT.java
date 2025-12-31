import model.*;
import topogate.*;
public class MainT {
    public static void main(String[] args) {

        

        CircuitT circuit = new CircuitT();
        CircuitParser parser = new CircuitParser(circuit);

        // Build a circuit from string
        GateT output = parser.parse("AND(A, OR(B, NOT(C)))");

        // Set input values
        ((InputGateT) circuit.getGate("A")).setValue(true);
((InputGateT) circuit.getGate("B")).setValue(false);
((InputGateT) circuit.getGate("C")).setValue(false);


        // Prepare topological solver
        circuit.buildTopo();

        // Evaluate
        boolean result = circuit.evaluate(output);
        System.out.println("Output: " + result); // should be true
    }
}




