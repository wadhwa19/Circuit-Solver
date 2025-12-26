import topogate.*;
import model.Toposolver;

import java.util.List;
public class MainT {
    public static void main(String[] args) {

        

        // 1Create input gates
        GateT A = new InputGateT("A", true);
        GateT B = new InputGateT("B", false);
        GateT C = new InputGateT("C", true);
        GateT D = new InputGateT("D", false);

        // Create logic gates
        GateT G1 = new AndGateT("G1", List.of(A, B));      // AND(A, B)
        GateT G2 = new OrGateT("G2", List.of(B, C));       // OR(B, C)
        GateT G3 = new NotGateT("G3", List.of(C));         // NOT(C)
        GateT G4 = new XorGateT("G4", List.of(A, D));      // XOR(A, D)
        GateT G5 = new NandGateT("G5", List.of(G1, G2));  // NAND(G1, G2)
        GateT G6 = new NorGateT("G6", List.of(G3, G4));   // NOR(G3, G4)

        // Output gate
        GateT F = new OrGateT("F", List.of(G5, G6));      // OR(G5, G6)

        //  Create Toposolver and add gates
        Toposolver solver = new Toposolver();
        for (GateT g : List.of(A, B, C, D, G1, G2, G3, G4, G5, G6, F)) {
            solver.addGate(g.getId(), g);
        }

        // Build adjacency list and topological order
        solver.createAdj();
        solver.toposort();

        // Evaluate the circuit
        boolean output = solver.evaluate(F);
        System.out.println("Circuit output: " + output);
    }
}




