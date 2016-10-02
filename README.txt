This is a Flight Application Data Analyser. It runs on Java and has dependencys JavaFX, SQLITE3 and Maven.
If you are using Eclipse as an IDE please follow the instructions on http://www.eclipse.org/efxclipse/install.html to add JavaFX Library.

To Import this project:
git clone https://eng-git.canterbury.ac.nz/fwy13/SENG202.git
Open your preferred IDE and then Import Maven Project.

To Run:
Double click seng202_2016_team9_phase2.jar this will run the Application.

Alternatively:

Open the terminal and locate the file that seng202_2016_team9_phase2.jar located in.
Run java -jar seng202_2016_team9_phase2.jar

Necessary Files:
/res/userdb.db

To not have userdb.db clash problems run:
git update-index --assume-unchanged res/userdb.db

Getting started:
The application is shipped with all the example data files pre loaded into the database. If the user wants add more data
from a file then they can select a file to import using File -> Import <data> where data is the type of data you are
choosing to import. ie Airline, Airport, Route or Flight data.
This will automatically take you to the summary page for the type of data imported.
Also, all buttons will work without any data loaded except the tables will be empty.

Each page will also have buttons leading to other summary pages of different data types.
The raw data pages has the ability to add data by filling in the fields and pressing Add.
The raw data pages for Airline, Airport and Routes can also filter/search through the data by typing in the fields needed
and pressing Go.
The Analyse button in the raw data will take you to a graph if it has been implemented.
There is only one option currently in the Analysis tab, which calculates the distance between two airports in the database.
