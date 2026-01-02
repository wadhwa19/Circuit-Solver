package test;

import model.CircuitT;
import topogate.*;
import java.util.*;

/**
 * Integration tests for complete circuit evaluation
 * Tests topological sorting and circuit solving
 */
public class CircuitTest {
    
    private static int passed = 0;
    private static int failed = 0;
    
    public static void main(String[] args) {
        System.out.println("Running Circuit Integration Tests...\n");
        
        testSimpleAndCircuit();
        testHalfAdder();
        testComplexCircuit();
        
        System.out.println("\n========== TEST RESULTS ==========");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total:  " + (passed + failed));
        System.out.println("==================================");
        
        if (failed == 0) {
            System.out.println("✓ All tests passed!");
        } else {
            System.out.println("✗ Some tests failed.");
            System.exit(1);
        }
    }
    
    /**
     * Test simple AND circuit: A AND B
     */
    private static void testSimpleAndCircuit() {
        System.out.println("Test 1: Simple AND Circuit (A AND B)");
        
        CircuitT circuit = new CircuitT();
        
        InputGateT a = new InputGateT("A", true);
        InputGateT b = new InputGateT("B", true);
        AndGateT and = new AndGateT("AND", new ArrayList<>(Arrays.asList(a, b)));
        
        circuit.addGate("A", a);
        circuit.addGate("B", b);
        circuit.addGate("AND", and);
        circuit.setOutputGate(and);
        
        circuit.buildTopo();
        
        // Test 1: true AND true = true
        a.setValue(true);
        b.setValue(true);
        assertEquals("1 AND 1", true, circuit.evaluate(and));
        
        // Test 2: true AND false = false
        a.setValue(true);
        b.setValue(false);
        assertEquals("1 AND 0", false, circuit.evaluate(and));
        
        System.out.println();
    }
    
    /**
     * Test Half Adder: XOR for sum, AND for carry
     * Sum = A XOR B
     * Carry = A AND B
     */
    private static void testHalfAdder() {
        System.out.println("Test 2: Half Adder Circuit");
        
        CircuitT circuit = new CircuitT();
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        XorGateT xor = new XorGateT("XOR", new ArrayList<>(Arrays.asList(a, b)));
        AndGateT and = new AndGateT("AND", new ArrayList<>(Arrays.asList(a, b)));
        
        circuit.addGate("A", a);
        circuit.addGate("B", b);
        circuit.addGate("XOR", xor);
        circuit.addGate("AND", and);
        
        circuit.buildTopo();
        
        // Test case: 0 + 0 = sum:0, carry:0
        a.setValue(false);
        b.setValue(false);
        circuit.setOutputGate(xor);
        assertEquals("Half Adder (0+0) Sum", false, circuit.evaluate(xor));
        circuit.setOutputGate(and);
        assertEquals("Half Adder (0+0) Carry", false, circuit.evaluate(and));
        
        // Test case: 1 + 0 = sum:1, carry:0
        a.setValue(true);
        b.setValue(false);
        circuit.setOutputGate(xor);
        assertEquals("Half Adder (1+0) Sum", true, circuit.evaluate(xor));
        circuit.setOutputGate(and);
        assertEquals("Half Adder (1+0) Carry", false, circuit.evaluate(and));
        
        // Test case: 1 + 1 = sum:0, carry:1
        a.setValue(true);
        b.setValue(true);
        circuit.setOutputGate(xor);
        assertEquals("Half Adder (1+1) Sum", false, circuit.evaluate(xor));
        circuit.setOutputGate(and);
        assertEquals("Half Adder (1+1) Carry", true, circuit.evaluate(and));
        
        System.out.println();
    }
    
    /**
     * Test complex multi-level circuit
     * Output = (A AND B) OR (NOT C)
     */
    private static void testComplexCircuit() {
        System.out.println("Test 3: Complex Circuit ((A AND B) OR (NOT C))");
        
        CircuitT circuit = new CircuitT();
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        InputGateT c = new InputGateT("C", false);
        
        AndGateT and = new AndGateT("AND", new ArrayList<>(Arrays.asList(a, b)));
        NotGateT not = new NotGateT("NOT", new ArrayList<>(Arrays.asList(c)));
        OrGateT or = new OrGateT("OR", new ArrayList<>(Arrays.asList(and, not)));
        
        circuit.addGate("A", a);
        circuit.addGate("B", b);
        circuit.addGate("C", c);
        circuit.addGate("AND", and);
        circuit.addGate("NOT", not);
        circuit.addGate("OR", or);
        circuit.setOutputGate(or);
        
        circuit.buildTopo();
        
        // Test: A=0, B=0, C=0 → (0 AND 0) OR (NOT 0) = 0 OR 1 = 1
        a.setValue(false);
        b.setValue(false);
        c.setValue(false);
        assertEquals("Complex (0,0,0)", true, circuit.evaluate(or));
        
        // Test: A=1, B=1, C=0 → (1 AND 1) OR (NOT 0) = 1 OR 1 = 1
        a.setValue(true);
        b.setValue(true);
        c.setValue(false);
        assertEquals("Complex (1,1,0)", true, circuit.evaluate(or));
        
        // Test: A=0, B=0, C=1 → (0 AND 0) OR (NOT 1) = 0 OR 0 = 0
        a.setValue(false);
        b.setValue(false);
        c.setValue(true);
        assertEquals("Complex (0,0,1)", false, circuit.evaluate(or));
        
        // Test: A=1, B=1, C=1 → (1 AND 1) OR (NOT 1) = 1 OR 0 = 1
        a.setValue(true);
        b.setValue(true);
        c.setValue(true);
        assertEquals("Complex (1,1,1)", true, circuit.evaluate(or));
        
        System.out.println();
    }
    
    private static void assertEquals(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            System.out.println("  ✓ " + testName + " PASSED");
            passed++;
        } else {
            System.out.println("  ✗ " + testName + " FAILED (expected: " + expected + ", got: " + actual + ")");
            failed++;
        }
    }
}