package model;

import java.util.*;
import topogate.*;

public class CircuitT {

    private final Map<String, GateT> gates = new LinkedHashMap<>();
    private final List<InputGateT> inputGates = new ArrayList<>();
    private GateT outputGate;

    public void addGate(String id, GateT gate) {
        gates.put(id, gate);
        if (gate instanceof InputGateT ig) {
            inputGates.add(ig);
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
}
