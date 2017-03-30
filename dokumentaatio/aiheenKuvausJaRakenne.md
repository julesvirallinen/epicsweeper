## :bomb: Minesweeper, labra17 :bomb:
### Intro

#### Subject

Minesweeper game with some twists, probably mostly graphical

#### Rules
 
 Basic rules same as in traditional minesweeper:

- The game is played on a grid of squares. 
- A predetermined amount of nodes contains a bomb
- The player must locate each bomb without clicking on them, using tips from non-bomb nodes. 
- if a player clicks on a node that is not a bomb, the game reveals all nodes not near bombs, connected to clicked node
- a revealed non-bomb node has the number of adjascent bombs, or is blank, if there are none. 

### Special 

The game is personified, either by an arbitrary story, or special graphics, like emoji. 

### Structure 

#### UI

At the moment the game can be played with a textUI, graphical coming soon. Looks something like this: 

```
Enter action and x, then y-coordinate, like this: click 1 0
flag 8 7
    0  1  2  3  4  5  6  7  8  9  
    _  _  _  _  _  _  _  _  _  _  
0|  ❑  1        1  ⚑  ❑  ❑  ❑  ❑  
1|  ⚑  1        1  1  1  1  ❑  ❑  
2|  1  1                 1  ⚑  ❑  
3|        1  1  1        1  1  1  
4|        1  ⚑  1                 
5|        1  1  1        1  1  1  
6|                       1  ❑  ❑  
7|                       1  1  1  
8|                                
9|                                

Enter action and x, then y-coordinate, like this: click 1 0
flag 8 6
Congrats, you won!
```

#### UML 

![UML diagram](uml.png)

#### Game

- Has amount of bombs
- Has an array of nodes
- Has stats
- Has it been won? 
- Maybe easy - medium - hard 

#### Node

- Knows if it's a bomb
- Knows if it's flagged
- Knows if it has been revealed
- Knows amount of adjescent bombs 

#### Game stats 

- Time played
- Bombs left unflagged
