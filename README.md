# Visual Logic Circuit Editor

A JavaFX-based visual logic circuit simulator that allows users to design, connect, and evaluate digital logic circuits through an intuitive drag-and-drop interface.
## Motivation 

As an electrical engineering student, combinational circuits are everywhere - op-amps, digital systems, basically the building blocks of anything cool I want to design. They're supposed to be foundational, but here's the thing: when I first learned about them, I was really confused.
Picture this: me, drowning in massive truth tables that looked like abstract art gone wrong. Eight inputs? That's 256 rows. Good luck keeping track of which gate connects to what. It was chaos.
Then I took a Data Structures and Algorithms class, and something clicked. 
I learned about graph algorithms - depth-first search, topological sorting, all that good stuff - and suddenly it struck to me: Wait... circuits are just graphs! Gates are nodes, wires are edges, and evaluating a circuit is literally just a topological sort problem!
So I thought, "Why am I still drawing these circuits by hand? I know how to code. I know graph algorithms."
And that's how this project was born:
Dragging and dropping gates and
watch the computer do all that messy evaluation for me so I can really understand what's happening instead of drowning in truth tables.

This tool has changed how I solve combinational circuits now. No more crossed-out truth tables, no more "wait, which input was this again?" Just clean, visual, algorithm-powered circuit solving.
If you've ever felt overwhelmed by combinational logic, this one's for you. Circuits like half adders and sr latches are now easy to visualise !!!

## Screenshots of UI
![alt text](<screenshots of ui and tests/ui1.png>)
![alt text](<screenshots of ui and tests/ui2.png>)
![alt text](<screenshots of ui and tests/ui3.png>)
![alt text](<screenshots of ui and tests/ui4.png>)

## Features

### Gate Types
- **Input Gates**: Configurable true/false inputs
- **AND Gate**: Output is true only if all inputs are true
- **OR Gate**: Output is true if any input is true
- **NOT Gate**: Inverts the input signal
- **NAND Gate**: Negated AND operation
- **NOR Gate**: Negated OR operation
- **XOR Gate**: Exclusive OR operation

### Core Functionality
- **Visual Circuit Design**: Drag-and-drop gate placement on canvas
- **Wire Connections**: Click-based pin-to-pin connections with visual feedback
- **Input Toggle**: Right-click input gates to toggle between true (green) and false (gray)
- **Circuit Evaluation**: Topological sort-based circuit simulation
- **Gate Deletion**: Remove individual gates and their connections
- **Clear Canvas**: Reset entire circuit with one click

## Project Structure

```
src/main/java/
├── frontend/
│   ├── MainApp.java          # Main application entry point
│   ├── CircuitCanvas.java    # Canvas for gate placement and wiring
│   ├── VisualGate.java       # Visual representation of logic gates
│   ├── Pin.java              # Input/output connection points
│   ├── Wire.java             # Visual connection between gates
│   └── GateType.java         # Enum for gate types
├── model/
│   ├── CircuitT.java         # Circuit data structure
│   └── Toposolver.java       # Topological sort and evaluation engine
└── topogate/
    ├── GateT.java            # Abstract gate base class
    ├── InputGateT.java       # Input gate implementation
    ├── AndGateT.java         # AND gate logic
    ├── OrGateT.java          # OR gate logic
    ├── NotGateT.java         # NOT gate logic
    ├── NandGateT.java        # NAND gate logic
    ├── NorGateT.java         # NOR gate logic
    └── XorGateT.java         # XOR gate logic
```

## Requirements

- **Java**: JDK 11 or higher
- **JavaFX**: Version 25.0.1 or compatible


## Installation & Setup

### 1. Download JavaFX SDK
Download the JavaFX SDK from [openjfx.io](https://openjfx.io/) and extract it to a location on your system.

### 2. Compile the Project
Navigate to the `src/main/java/` directory and compile:

```bash
javac --module-path "path/to/javafx-sdk/lib" \
      --add-modules javafx.controls \
      frontend/MainApp.java
```

**Example (Windows):**
```bash
javac --module-path "C:/javafx-sdk-25.0.1/lib" --add-modules javafx.controls frontend/MainApp.java
```

### 3. Run the Application
```bash
java --module-path "path/to/javafx-sdk/lib" \
     --add-modules javafx.controls \
     frontend.MainApp
```
## Running Tests
The project has comprehensive gate logic and complete circuit integration tests.
### Gate Logic Tests
``` bash

javac -cp . test/GateTest.java
java test.GateTest
```
![alt text](<screenshots of ui and tests/gate_tests.png>)
### Run Integration Tests for Circuit Evaluation
``` bash
javac -cp . test/CircuitTest.java 
java test.CircuitTest
```
![alt text](<screenshots of ui and tests/circuit_int_test.png>)
## How to Use

### Building a Circuit

1. **Add Gates**: Click gate buttons (INPUT, AND, OR, NOT, etc.) in the toolbar to add gates to the canvas

2. **Position Gates**: Click and drag gates to arrange them on the canvas

3. **Connect Gates**:
   - Click a red OUTPUT pin (right side of gate)
   - The pin turns yellow and a dashed line appears
   - Click a blue INPUT pin (left side of target gate) to complete the connection
   - Click empty canvas to cancel connection

4. **Set Input Values**:
   - Right-click INPUT gates
   - Select "Toggle Value" to switch between true (green) and false (gray)

5. **Set Output Gate**:
   - Right-click the gate whose output you want to evaluate
   - Select "Set as Output Gate"

6. **Evaluate Circuit**:
   - Click "Evaluate Circuit" button
   - View input states and final output in the dialog

7. **Delete Gates**:
   - Right-click any gate
   - Select "Delete Gate" to remove it and all connected wires

8. **Clear All**:
   - Click "Clear All" button to reset the entire circuit

## Architecture

### Backend (Logic Layer)

**GateT Hierarchy**: Abstract base class with concrete implementations for each gate type. Each gate evaluates its output based on input values.

**CircuitT**: Manages the collection of gates and delegates evaluation to the Toposolver.

**Toposolver**: 
- Builds adjacency list representation of the circuit
- Performs topological sort using DFS post-order traversal
- Evaluates gates in topological order to compute circuit output

### Frontend (Visual Layer)

**MainApp**: JavaFX application controller managing UI components and user interactions.

**CircuitCanvas**: Pane managing visual gates, wires, and pin connection logic.

**VisualGate**: Visual representation of logic gates with context menus and pin management.

**Pin**: Clickable connection points with hover effects and visual feedback.

**Wire**: Line connecting two gates that updates position when gates are dragged.


## Future Developments 

-Add feedback loop functionality to circuits and enhance UI.

