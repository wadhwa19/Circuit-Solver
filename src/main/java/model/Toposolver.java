package model;

import java.util.*;

import topogate.GateT;
import topogate.InputGateT;

public class Toposolver {
    private final Map<String, GateT> idMap;
    private final Map<GateT, List<GateT>> adjList = new HashMap<>();
    private final Map<GateT, Integer> inDegree = new HashMap<>();
    private final Map<GateT, Boolean> values = new HashMap<>();
    private final Set<GateT> visited = new HashSet<>();
    private final List<GateT> topoOrder = new ArrayList<>();

    public Toposolver() {
        idMap = new HashMap<>();
    }

    // Add gate to solver
    public void addGate(String id, GateT gate){
        if (idMap.containsKey(id)) {
            throw new IllegalArgumentException("Gate ID already exists");
        }
        idMap.put(id, gate);
    }

    // Build adjacency list and in-degree map
    public void createAdj() {
        List<GateT> allGates = new ArrayList<>(idMap.values());
        for (GateT g : allGates) {
            adjList.put(g, new ArrayList<>());
            inDegree.put(g, 0);
        }

        for (GateT g : allGates) {
            for (GateT input : g.getInputs()) {
                adjList.get(input).add(g);               // input → g
                inDegree.put(g, inDegree.get(g) + 1);   // increment in-degree
            }
        }
    }

    // DFS post-order
    private void dfsPost(GateT node) {
        if (visited.contains(node)) return;
        visited.add(node);
        for (GateT neighbor : adjList.get(node)) {
            dfsPost(neighbor);
        }
        topoOrder.add(node);  // add after visiting all neighbors
    }

    // Generate topological order
    public void toposort() {
        List<GateT> allGates = new ArrayList<>(idMap.values());
        for (GateT g : allGates) {
            if (!visited.contains(g)) {
                dfsPost(g);
            }
        }
        Collections.reverse(topoOrder);  // reverse post-order to get topo order
    }

    // Evaluate the circuit and return output
    public boolean evaluate(GateT outputGate) {
        for (GateT g : topoOrder) {
            if (g instanceof InputGateT) {
                values.put(g, ((InputGateT) g).evaluate(values));
            } else {
                values.put(g, g.evaluate(values));  // g.evaluate() should uses inputs' values
            }
        }

        return values.get(outputGate);
    }

    //  get gate by id
    public GateT getGate(String id) {
        if (!idMap.containsKey(id)) throw new IllegalArgumentException("Gate ID not found");
        return idMap.get(id);
    }
}
