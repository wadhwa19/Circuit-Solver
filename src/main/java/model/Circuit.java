package model;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import gate.Gate;

public class Circuit {
    private final Map<String, Gate> idMap;

    public Circuit() {
        idMap = new HashMap<>();
    }

    public void addGate(Gate gate) {

        String id = gate.getId();
        if (idMap.containsKey(id)) {
            throw new IllegalArgumentException("Gate ID already exists");
        }
        idMap.put(id, gate);

    }

    public Gate getGate(String id) {
        if (!idMap.containsKey(id)) {
            throw new IllegalArgumentException("Gate ID not found");
        } else {
            return idMap.get(id);
        }
    }

    public boolean evaluate(String id) {
        Gate a = idMap.get(id);
        return a.evaluate();
    }

    public Set<String> getAllGateIds() {
        return idMap.keySet();
    }

}
