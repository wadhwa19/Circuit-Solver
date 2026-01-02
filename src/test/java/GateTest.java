package test;

import topogate.*;
import java.util.*;

/**
 * Unit tests for logic gate implementations
 * Run with: java test.GateTest
 */
public class GateTest {
    
    private static int passed = 0;
    private static int failed = 0;
    
    public static void main(String[] args) {
        System.out.println("Running Logic Gate Unit Tests...\n");
        
        testAndGate();
        testOrGate();
        testNotGate();
        testNandGate();
        testNorGate();
        testXorGate();
        
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
    
    private static void testAndGate() {
        System.out.println("Testing AND Gate:");
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        AndGateT and = new AndGateT("AND", Arrays.asList(a, b));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        // Test case 1: false AND false = false
        a.setValue(false);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("AND(0,0)", false, and.evaluate(values));
        
        // Test case 2: false AND true = false
        a.setValue(false);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("AND(0,1)", false, and.evaluate(values));
        
        // Test case 3: true AND false = false
        a.setValue(true);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("AND(1,0)", false, and.evaluate(values));
        
        // Test case 4: true AND true = true
        a.setValue(true);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("AND(1,1)", true, and.evaluate(values));
        
        System.out.println();
    }
    
    private static void testOrGate() {
        System.out.println("Testing OR Gate:");
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        OrGateT or = new OrGateT("OR", Arrays.asList(a, b));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        a.setValue(false);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("OR(0,0)", false, or.evaluate(values));
        
        a.setValue(false);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("OR(0,1)", true, or.evaluate(values));
        
        a.setValue(true);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("OR(1,0)", true, or.evaluate(values));
        
        a.setValue(true);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("OR(1,1)", true, or.evaluate(values));
        
        System.out.println();
    }
    
    private static void testNotGate() {
        System.out.println("Testing NOT Gate:");
        
        InputGateT a = new InputGateT("A", false);
        NotGateT not = new NotGateT("NOT", Arrays.asList(a));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        a.setValue(false);
        values.put(a, a.evaluate(values));
        assertEquals("NOT(0)", true, not.evaluate(values));
        
        a.setValue(true);
        values.put(a, a.evaluate(values));
        assertEquals("NOT(1)", false, not.evaluate(values));
        
        System.out.println();
    }
    
    private static void testNandGate() {
        System.out.println("Testing NAND Gate:");
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        NandGateT nand = new NandGateT("NAND", Arrays.asList(a, b));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        a.setValue(false);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NAND(0,0)", true, nand.evaluate(values));
        
        a.setValue(false);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NAND(0,1)", true, nand.evaluate(values));
        
        a.setValue(true);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NAND(1,0)", true, nand.evaluate(values));
        
        a.setValue(true);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NAND(1,1)", false, nand.evaluate(values));
        
        System.out.println();
    }
    
    private static void testNorGate() {
        System.out.println("Testing NOR Gate:");
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        NorGateT nor = new NorGateT("NOR", Arrays.asList(a, b));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        a.setValue(false);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NOR(0,0)", true, nor.evaluate(values));
        
        a.setValue(false);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NOR(0,1)", false, nor.evaluate(values));
        
        a.setValue(true);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NOR(1,0)", false, nor.evaluate(values));
        
        a.setValue(true);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("NOR(1,1)", false, nor.evaluate(values));
        
        System.out.println();
    }
    
    private static void testXorGate() {
        System.out.println("Testing XOR Gate:");
        
        InputGateT a = new InputGateT("A", false);
        InputGateT b = new InputGateT("B", false);
        XorGateT xor = new XorGateT("XOR", Arrays.asList(a, b));
        
        Map<GateT, Boolean> values = new HashMap<>();
        
        a.setValue(false);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("XOR(0,0)", false, xor.evaluate(values));
        
        a.setValue(false);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("XOR(0,1)", true, xor.evaluate(values));
        
        a.setValue(true);
        b.setValue(false);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("XOR(1,0)", true, xor.evaluate(values));
        
        a.setValue(true);
        b.setValue(true);
        values.put(a, a.evaluate(values));
        values.put(b, b.evaluate(values));
        assertEquals("XOR(1,1)", false, xor.evaluate(values));
        
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