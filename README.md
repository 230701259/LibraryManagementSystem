# Library Management System (Java)

## Problem Statement

This project is a **Library Management System** developed in Java.
It helps a librarian to manage books, members, borrowing, returning, and fines using a simple menu-driven program.

The system should:
* Store book details
* Store member details
* Allow issuing and returning books
* Calculate fines for late returns

##  Modules in the Project

* **books** → manages books (add, remove, view)
* **member** → manages members and borrowing records
* **librarian** → handles login and fine calculation
* **LibraryManagementSystem** → main program (menu + controls all operations)

##  Approach / Logic Used

* Uses **Object-Oriented Programming (OOP)** concepts
* Data stored using **ArrayList** (no database)

### Main Logic:

* Books and members are stored in lists
* When a book is issued:
  * Check availability
  * Reduce quantity
  * Set issue date & due date (14 days)
* When returned:
  * Increase quantity
  * Mark return date
* Fine calculation:
  Fine = number of days × fine per day


* Menu-driven program
* Data handling using collections
