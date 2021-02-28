# Past-Projects  
Current year/sem: Y2S2.  
These are the projects I have done so far, arranged according to the year and semester. More details can be found under each project description section.  
  
**Table of Contents**
- [Canteen System (Y1S1)](Past-Projects#canteen-system--y1s1-)
- [Exploration on Epilepsy Data (Y1S2)](#exploration-on-epilepsy-data--y1s2-)
- [Graph Search Algorithm (Y2S1)](#graph-search-algorithm--y2s1-)
- [Sequential Search Algorithm (Y2S1)](#sequential-search-algorithm--y2s1-)
- [Course Registration System (Y2S1)](#course-registration-system--y2s1-)
- [Database Design & Implementation (Y2S1)](#database-design---implementation--y2s1-)


## Canteen System (Y1S1)
Language used: Python  
Modules used: PyQt5  
The project requirements are to create an interactive application, whether console 
or GUI, that tracks the menu items in a specific canteen on campus. 
Depending on the day and time, different items will be available and the menu shown 
will update accordingly. The main objective of this project was to put our basic 
programming skills to the test and to provide an opportunity to learn more if desired. 
The module I chose to design the app with was PyQt5 and prior to this project, 
I had no knowledge of Object-Oriented Programming. However, I took the challenge 
head on and managed to come up with the app as pushed to the repository. Another 
struggle was reading the PyQt5 documentation as it was mainly written in C++ 
which I have yet to learn.  
  
## Exploration on Epilepsy Data (Y1S2)
Language used: Python (Jupyter Notebook)  
Modules used: seaborn, scikit, numpy, pandas, matplotlib  
This project served as an introduction to Data Science to apply the basic concepts 
and analysis methods taught as well as to explore additional tools not yet taught 
by the module itself. Our main objective was to detect epilepsy and see if we are 
able to predict it. Basics include using pandas to create a dataframe from the 
data provided and proceeding to get the statistics summary of the dataframe. Following 
that, the basic graphs (histogram/frequency, box and whisker, violin, bar plot) are used 
to present the data. Other basic models used were confusion matrices, decision trees and 
logistic regressions. Branching out from the basics, my group and I went on to explore 
SVM, parallel plots, 5-fold validation.  
  
## Graph Search Algorithm (Y2S1)  
Language used: Java  
This project required us to use our knowledge of BFS and/or other graph traversal algorithms 
to find the shortest k paths from every node to certain nodes marked out as hospitals. 
A multi-source BFS algorithm, which only gave the length of the shortest path, was used as 
reference. It was modified such that the entire path was reconstructed and it will find the 
number of different shortest paths specified by the user.  
  
## Sequential Search Algorithm (Y2S1)  
Language used: Python
Main objective of this project is to improve on the brute force sequential search algorithm 
used to find a certain query string within a string, in terms of its time complexity. Analysis 
of our own algorithm was done after testing that our algorithm worked. 2 algorithms were needed 
, of which we thought of ways to skip over certain portions of the string. The first was to 
skip over redundant portions which were already checked for matching characters while the 
second algorithm relied on matching the ASCII sum of k characters, where k is the number of 
characters in the query string.  
  
## Course Registration System (Y2S1)  
Language used: Java  
This was a console application, mimicking the actual course registration system used. It was 
a chance to train our minds to design an application using Object-Oriented Programming principles. 
Similar features such as having Admin and Student accounts, Admins having limited access to certain 
functions like being able to create a new course while students can only register for courses, etc. 
The system was designed to notify the user via email when they are on a waiting list and a spot has 
opened up for them.  
  
## Database Design & Implementation (Y2S1)  
Language used: SQL  
To put the theory taught to use, a database based on an online shop needed to be designed. The flow 
of the project was to design and refine an ER diagram, specify the functional dependencies, ensure 
that it satisfies at least 3NF and then implementing it in MSSQL. Upon implementation, the task was 
to populate the database and then using queries to answer the questions specified by the manual.  
    