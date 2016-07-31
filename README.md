# Lynx 

> **_Current version: 1.0.0alpha1 (IN DEV)_**  
> **Current phase: Migration to Gradle**

This project is still in development. MANY PARTS WILL CHANGE IN THE NEXT WEEKS.  
 
Lynx is an engine to build textual games with json files.  
This project is a personal project but feel free to fork it to play or to improve it. 
Currently only a console version is available. 

# How to build the project ?

1. Download or Clone this repository. 
2. Go to the repository folder and open a terminal. 
3. Run gradle with this command: `gradle bundle`. 
4. The complete instalation is in the **LynxBundle** folder. You can now run the **Lynx.jar** file (go to the LynxBundle folder and then run this command: `java -jar Lynx.jar`).  

Note: You can change the name of the bundle folder in the `gradle.properties` file.

# Configuration

You can configure Lynx by modifying the files in `vendor/config`. 

# How to create a game ? 

Lynx will load each games which meet those requirements: 
- the game is in a sub-folder of 'games' directory 
- this sub-folder contains a file named 'base.json' which describes the game  

Lynx  
&nbsp;&nbsp;|-- games  
&nbsp;&nbsp;&nbsp;&nbsp;|-- Your_Game  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- lang &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// translations go here   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- story &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// game files go here   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- base.json &nbsp;&nbsp;&nbsp;&nbsp;// description of your game  
&nbsp;&nbsp;&nbsp;&nbsp;|-- Other_Games...   
&nbsp;&nbsp;|-- vendor   
&nbsp;&nbsp;| -- Lynx.jar    

## 'base.json' file

All the properties of this file are required.

Soon... 

## Translation files

Soon... 

## Story files

Soon...
