# University Library Management System

## Description
This project is a Java-based university library management system developed using object-oriented programming principles. The system manages different types of library items and users, and processes borrowing, returning, penalty handling, and display operations through text-based input files.

## Features
- Management of multiple library item types:
  - Books
  - Magazines
  - DVDs
- Management of different user roles:
  - Students
  - Academic Members
  - Guests
- Borrowing and returning operations
- User-specific borrowing limits and item restrictions
- Automatic overdue handling and penalty calculation
- Penalty payment control before new borrowing
- Display of users and items sorted by ID
- File-based input and output processing

## Object-Oriented Design
This project applies core OOP concepts such as:
- Inheritance
- Encapsulation
- Class-based design
- Role-specific behavior for users and items

## Technologies
- Java
- File I/O
- Object-Oriented Programming

## Input Files
The program uses the following input files:
- `items.txt`
- `users.txt`
- `commands.txt`

## How to Run
```bash
javac Main.java
java Main items.txt users.txt commands.txt output.txt
