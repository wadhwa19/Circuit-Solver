package model;

import java.util.ArrayList;

import java.util.Stack;
import topogate.*;
import java.util.List;

public class CircuitParser {
     private final CircuitT circuit;

    public CircuitParser(CircuitT circuit) {
        this.circuit = circuit;
    }

    // Simple recursive parser
    public GateT parse(String expr) {
        expr = expr.replaceAll("\\s+", ""); // remove spaces

        if (expr.startsWith("AND(")) {
            return parseGate(expr, "AND");
        } else if (expr.startsWith("OR(")) {
            return parseGate(expr, "OR");
        } else if (expr.startsWith("NOT(")) {
            return parseGate(expr, "NOT");
        } else if (expr.startsWith("XOR(")) {
            return parseGate(expr, "XOR");
        } else {
            // must be input
            InputGateT input = new InputGateT(expr, false);
            circuit.addGate(expr, input);
            return input;
        }
    }

    private GateT parseGate(String expr, String type) {
        // remove gate type and parentheses
        String inside = expr.substring(type.length() + 1, expr.length() - 1);
        List<String> parts = splitTopLevel(inside);

        List<GateT> inputs = new ArrayList<>();
        for (String p : parts) {
            inputs.add(parse(p));
        }

        GateT gate;
        String gateId = type + "_" + System.nanoTime();
        switch (type) {
            case "AND" -> gate = new AndGateT(gateId, inputs);
            case "OR"  -> gate = new OrGateT(gateId, inputs);
            case "NOT" -> gate = new NotGateT(gateId, inputs);
            case "XOR" -> gate = new XorGateT(gateId, inputs);
            default -> throw new IllegalArgumentException("Unknown gate type: " + type);
        }

        circuit.addGate(gateId, gate);
        return gate;
    }

    // Splits a comma-separated list at top-level only (not inside nested parentheses)
    private List<String> splitTopLevel(String s) {
        List<String> res = new ArrayList<>();
        int level = 0;
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == ',' && level == 0) {
                res.add(sb.toString());
                sb.setLength(0);
            } else {
                if (c == '(') level++;
                else if (c == ')') level--;
                sb.append(c);
            }
        }
        res.add(sb.toString());
        return res;
    }
}
