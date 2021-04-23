# HuntTheWumpus
![alt text](https://raw.githubusercontent.com/DaveDeDave/HuntTheWumpus/main/docs/gamePreview.png?token=ASKQWYVZQZKMUTLIL3HPCXLARRXIC)
The classical Hunt The Wumpus game written in Java. 
This game has been developed for a project at the University of Eastern Piedmont during the course of programming paradigms: OOP in collaboration of LukeAz

## Description
This is a java implementation of the game **Hunt The Wumpus**.
Few modifications have been made to the original game. Two new characters have been added:
- Miniwumpus
- Survivor

The miniwumpus moves randomly around the map and steals items from the player if it ends up in the same cell.
The survivors are locked in cells on the map; if the player finds them he will receive a gift

As with the original game, you win by killing the wumpus and lose if you end up on the wumpus' cell.
The next section will better deepen the game events.

## Commands and Event
| Event | Action |
| ----------- | ----------- |
| Press directional keys | Move the player in the map |
| Press AWSD keys | Shoot in a direction in the map |
| Shoot at the wumpus | Win |
| End up in a cell with a wumpus | Lose |
| End up in a cell with a miniwumpus | Lose a random item. if you don't have items you lose |
| End up in a cell with a survivor | Gain a random item |
| Timer expires | The player will move in a random direction in the map |

## Technologies
- Java 1.8 api
- Eclipse IDE
- MVC pattern
- Observer pattern (deprecated)
- Javadoc (english and italian version)
- JUnit Test 5

## Disclaimer
All images and icons used in this project belong to their rightful owners
