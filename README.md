# Lynx 

**_Current version: 1.0.0alpha1 (IN DEV)_** 
**Current phase: Migration to Gradle**

This project is still in development. MANY PARTS WILL CHANGE IN THE NEXT WEEKS. 


Lynx is an engine to build textual games with json files.  
This project is a personal project but feel free to fork it to play or to improve it. 
Currently only a console version is available. 

# How to build the project ?

1. Download or Clone this repository. 
2. Go to the repository folder and open a terminal. 
3. Run gradle with this command: `gradle build`. 
4. The complete instalation is in the **Lynx** folder. You can now run the **Lynx.jar** file (go to the Lynx folder and then run this command: `java -jar Lynx.jar`). 

# Configuration

You can configure Lynx by modifying the files in `vendor/config`. 

# How to create a game ? 

Lynx will load each games which meet those requirements: 
- the game is in a sub-folder of 'games' directory 
- this sub-folder contains a file named 'base.json' which describes the game

Lynx 
  |-- games 
    |-- Your_Game 
      |-- lang       // translations go here 
      |-- story      // game files go here 
      |-- base.json  // description of your game
    |-- Other_Games...  
  |-- vendor 
  | -- Lynx.jar  

## 'base.json' file

All the properties of this file are required.

Soon... 

## Translation files

Soon... 

## Story files

Soon...