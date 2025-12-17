

import model.Circuit;
import gate.AndGate;
import gate.InputGate;
import java.util.List;



public class Main {

    public static void main(String[] args) {
        Circuit circuit = new Circuit();

        InputGate A = new InputGate("A", true);
        InputGate B = new InputGate("B", false);
        AndGate G1 = new AndGate("G1", List.of(A, B));

        circuit.addGate(A);
        circuit.addGate(B);
        circuit.addGate(G1);

        System.out.println("G1 (A AND B) = " + circuit.evaluate("G1"));
    }
}
/*import model.Circuit;
import gate.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the circuit
        Circuit circuit = new Circuit();

        // Define input combinations (A, B, C)
        boolean[][] inputCombinations = {
            {false, false, false},
            {false, false, true},
            {false, true, false},
            {false, true, true},
            {true, false, false},
            {true, false, true},
            {true, true, false},
            {true, true, true}
        };

        System.out.println("A\tB\tC\tAND\tOR\tNOT_C\tXOR\tNAND\t(AND OR NOT_C)");
        System.out.println("----------------------------------------------------------");

        for (boolean[] inputs : inputCombinations) {
            // Clear circuit for each combination
            circuit = new Circuit();

            // Inputs
            InputGate A = new InputGate("A", inputs[0]);
            InputGate B = new InputGate("B", inputs[1]);
            InputGate C = new InputGate("C", inputs[2]);

            // Gates
            AndGate G1 = new AndGate("G1", List.of(A, B));         // A AND B
            OrGate G2 = new OrGate("G2", List.of(A, B));           // A OR B
            NotGate G3 = new NotGate("G3", List.of(C));            // NOT C
            XorGate G4 = new XorGate("G4", List.of(A, C));         // A XOR C
            NandGate G5 = new NandGate("G5", List.of(B, C));       // B NAND C
            OrGate G6 = new OrGate("G6", List.of(G1, G3));         // (A AND B) OR (NOT C)

            // Add to circuit
            circuit.addGate(A);
            circuit.addGate(B);
            circuit.addGate(C);
            circuit.addGate(G1);
            circuit.addGate(G2);
            circuit.addGate(G3);
            circuit.addGate(G4);
            circuit.addGate(G5);
            circuit.addGate(G6);

            // Print results
            System.out.printf("%b\t%b\t%b\t%b\t%b\t%b\t%b\t%b\t%b\n",
                    inputs[0],
                    inputs[1],
                    inputs[2],
                    circuit.evaluate("G1"),
                    circuit.evaluate("G2"),
                    circuit.evaluate("G3"),
                    circuit.evaluate("G4"),
                    circuit.evaluate("G5"),
                    circuit.evaluate("G6"));
        }
    }
}
 */