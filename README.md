# BusApp Java Application

The **BusApp** is a Gradle-based Java Spring boot application designed to manage Tap ON and Tap OFF actions for bus rides. It processes these actions into trips and calculates fares based on the traveled route.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Running the Application](#running-the-application)
- [Assumptions](#assumptions)
- [Limitations](#limitations)

## Introduction

This app is running:
1. Java 1.8
2. Spring 2.7.14

### Prerequisites

To run the **BusApp** application, you need the following prerequisites:

1. Java Development Kit (JDK) 1.8
2. Gradle Build Tool

### Running the Application

Follow these steps to run the **BusApp** application:

1. Clone the repository:

   ```bash
   git clone https://github.com/HEdmeades/busapp.git
   cd busapp   
   
2. Choose Java 1.8 as language level and module SDK for project (if not already) 

3. Run test cases


### Assumptions

1. In the test data there was an OFF without a corresponding ON. I have asummed this is to be ignored and the user isnt to be charged and a trip isnt to be created.
2. INCOMPLETE trips will not have a finished time in export result
3. INCOMPLETE trips will have a duration seconds of startTime to end of day

 
### Limitations

1. ExportCSVService is not implemented generically as my generic bean implementation was formatting the columns uppercase and not in order and I ran out of time to debug
2. I couldn't find a pattern in the fair calculation so the values are hard coded based on sectors

