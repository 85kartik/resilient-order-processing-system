# Resilient Order Processing System

A small beginner-friendly Java project that checks customer orders and processes valid orders using multiple threads. It uses only Core Java; it does not need Spring Boot or a database.

## Project folder structure

```text
ResilientOrderProcessingSystem/
├── README.md
└── src/
    └── com/
        └── example/
            ├── exceptions/
            │   ├── InvalidOrderException.java
            │   └── ProcessingException.java
            ├── orders/
            │   ├── Main.java
            │   ├── Order.java
            │   └── OrderProcessor.java
            └── util/
                └── OrderValidator.java
```

The folder names under `src` match the package names at the top of each Java file. For example, `Order.java` starts with `package com.example.orders;` and is stored in `src/com/example/orders/`.

## What each file does

### `Order.java`

This class represents one order. It stores four pieces of information: the order ID, customer name, product name, and price. Its constructor fills in those values, and its getter methods let other classes read them.

### `InvalidOrderException.java`

This is a custom checked exception for an order that cannot be accepted. The validator uses it when required details are missing or the price is not greater than zero.

### `ProcessingException.java`

This is a custom checked exception for a problem that happens while processing an otherwise valid order. The sample program uses it to demonstrate how a processing failure is handled.

### `OrderValidator.java`

This class checks that an order is present, its ID, customer name, and product name are not blank, and its price is a finite number greater than zero. If a check fails, it throws `InvalidOrderException` with a message describing the problem.

### `OrderProcessor.java`

This class implements Java's `Runnable` interface, which means it can be run as a task by a thread. Each instance handles one order. Its `try-catch-finally` block catches a processing error and prints a completion message for the attempt. A failure in one task does not stop other tasks in the thread pool.

The product named `Failing Product` deliberately throws `ProcessingException`. This is included so you can see the error handling in action; it is not a real payment or shipping failure.

### `Main.java`

This is where the program starts. It creates six sample orders, validates each order, and sends valid orders to an `ExecutorService` created with a fixed pool of three threads. The executor can run up to three submitted tasks at the same time. At the end, `Main` shuts down the pool and waits for its tasks to finish.

## How the program flows

1. `Main` creates the sample orders.
2. `OrderValidator` checks each order before it is submitted.
3. Invalid orders are caught and printed as rejected. They are not submitted to the pool.
4. Valid orders are submitted as `OrderProcessor` tasks.
5. The three-thread pool processes those tasks. The order of console messages can vary because tasks run concurrently.
6. If a task has a processing error, that task catches it and finishes; other tasks continue.
7. The thread pool shuts down after all submitted tasks finish.

## Requirements

- A JDK installed (the project uses standard Java concurrency features).
- VS Code with the Java Extension Pack, or another Java IDE.

No external libraries, database, or build tool is required.

## Compile and run in VS Code

1. Open the `ResilientOrderProcessingSystem` folder in VS Code.
2. Open **Terminal > New Terminal**. Make sure the terminal is at the project root (the folder containing `src`).
3. Compile the Java files:

   **Windows PowerShell:**

   ```powershell
   javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
   ```

   **macOS, Linux, or a shell that supports wildcards:**

   ```bash
   javac -d out src/com/example/exceptions/*.java src/com/example/util/*.java src/com/example/orders/*.java
   ```

4. Run the program:

   ```text
   java -cp out com.example.orders.Main
   ```

The `-d out` option puts compiled `.class` files in an `out` folder. The fully qualified name `com.example.orders.Main` tells Java which packaged class contains the `main` method.

## Example output

The exact order of processing messages can change between runs because three threads may work at the same time. One possible output is:

```text
Order O105 rejected: Order ID, customer name, and product name are required.
Order O106 rejected: Price must be a valid number greater than zero.
Processing order O103 for Mina: Backpack ($35.0)
Processing order O101 for Asha: Notebook ($12.5)
Processing order O102 for Ravi: Pen Set ($5.0)
Order O102 completed successfully.
Order O103 completed successfully.
Order O101 completed successfully.
Finished attempt for order O102
Finished attempt for order O103
Order O104 failed: simulated processing error.
Finished attempt for order O104
Finished attempt for order O101
All order tasks have been handled.
```
