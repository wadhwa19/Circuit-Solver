package model;

import java.util.*;

import gate.Gate;
import gate.InputGate;

public class Toposolver {
    private final Map<String, Gate> idMap;
    private final Map<Gate, List<Gate>> adjList = new HashMap<>();
    private final Map<Gate, Integer> inDegree = new HashMap<>();
    private final Map<Gate, Boolean> values = new HashMap<>();
    private final Set<Gate> visited = new HashSet<>();
    private final List<Gate> topoOrder = new ArrayList<>();

    public Toposolver() {
        idMap = new HashMap<>();
    }

    // Add gate to solver
    public void addGate(String id, Gate gate){
        if (idMap.containsKey(id)) {
            throw new IllegalArgumentException("Gate ID already exists");
        }
        idMap.put(id, gate);
    }

    // Build adjacency list and in-degree map
    public void createAdj() {
        List<Gate> allGates = new ArrayList<>(idMap.values());
        for (Gate g : allGates) {
            adjList.put(g, new ArrayList<>());
            inDegree.put(g, 0);
        }

        for (Gate g : allGates) {
            for (Gate input : g.getInputs()) {
                adjList.get(input).add(g);               // input → g
                inDegree.put(g, inDegree.get(g) + 1);   // increment in-degree
            }
        }
    }

    // DFS post-order
    private void dfsPost(Gate node) {
        if (visited.contains(node)) return;
        visited.add(node);
        for (Gate neighbor : adjList.get(node)) {
            dfsPost(neighbor);
        }
        topoOrder.add(node);  // add after visiting all neighbors
    }

    // Generate topological order
    public void toposort() {
        List<Gate> allGates = new ArrayList<>(idMap.values());
        for (Gate g : allGates) {
            if (!visited.contains(g)) {
                dfsPost(g);
            }
        }
        Collections.reverse(topoOrder);  // reverse post-order to get topo order
    }

    // Evaluate the circuit and return output
    public boolean evaluate(Gate outputGate) {
        for (Gate g : topoOrder) {
            if (g instanceof InputGate) {
                values.put(g, ((InputGate) g).evaluate());
            } else {
                values.put(g, g.evaluate());  // g.evaluate() should uses inputs' values
            }
        }

        return values.get(outputGate);
    }

    //  get gate by id
    public Gate getGate(String id) {
        if (!idMap.containsKey(id)) throw new IllegalArgumentException("Gate ID not found");
        return idMap.get(id);
    }
}
