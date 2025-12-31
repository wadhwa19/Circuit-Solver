package model;

import java.util.*;
import topogate.*;

public class CircuitT {
    private final Map<String, GateT> gates = new LinkedHashMap<>();
    private final List<InputGateT> inputGates = new ArrayList<>();
    private GateT outputGate;
    private final Toposolver solver = new Toposolver();

    public void addGate(String id, GateT gate) {
        gates.put(id, gate);
        solver.addGate(id, gate);

        if (gate instanceof InputGateT input) {
            inputGates.add(input);
        }
    }

    public Collection<GateT> getAllGates() {
        return gates.values();
    }

    public List<InputGateT> getInputGates() {
        return inputGates;
    }

    public GateT getGate(String id) {
        return gates.get(id);
    }

    public void setOutputGate(GateT gate) {
        this.outputGate = gate;
    }

    public GateT getOutputGate() {
        return outputGate;
    }

    // --- Add Toposolver methods ---
    public void buildTopo() {
        solver.createAdj();
        solver.toposort();
    }

    public boolean evaluate(GateT output) {
        return solver.evaluate(output);
    }
}
