This project is a group assignment for the Operating System (CIT3002) module at the University of Technology, Jamaica. It involves developing a process scheduling simulator to demonstrate and evaluate the performance of various CPU scheduling algorithms.


The simulator combines theoretical research with practical implementation, providing hands-on experience with fundamental operating system concepts.

⚙️ Implementation Details
Algorithm Selection
Our simulator implements exactly four (4) CPU scheduling algorithms. Our selection fulfills the mandatory requirements, including a non-preemptive, a preemptive, and a multi-level technique:


First Come First Serve (FCFS)  (Non-preemptive)


Shortest Job First (SJF)  (Non-preemptive)


Multi-level Queue (MLQ)  (Multi-level)


Priority Scheduling (Preemptive)  (Preemptive)



Input: Process specifications, including arrival time, burst time, and priority.


Output: Performance metrics and a comparative analysis.

Core Features
The simulator includes the following key functionalities:

Process creation and management.

Implementation of selected scheduling algorithms.

Calculation of performance metrics.

Comparative analysis output.

A user-friendly command-line interface (CLI).

Performance Metrics
The following minimum performance metrics are calculated and displayed for each process and algorithm:


Average Waiting Time 


Average Turnaround Time 


Average Response Time 


CPU Utilization 


Throughput 

