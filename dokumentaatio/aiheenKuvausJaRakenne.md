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
