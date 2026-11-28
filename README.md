CS 3354 Team 1 Project (Trap Tile)

## Team members
- Group Members: Adiyat Abubakirov, Eladio Gonzalez, Benjamin Guzman, Brume Esabunor-Nukie.
| Full Name         | GitHub nickname |
| ----------------- | --------------- |
| Adiyat Abubakirov | kezy228-pixel   |
| Eladio Gonzalez   |                 |
| Benjamin Guzman   |                 |
| Brume Esabunor-Nukie |              |

## Overview
- Starter source: https://github.com/albertocahdez/cs3354
- Copied only groupproject/ from instructor commit: 00e1fd7a0382c1e8b8d405e6ad0f2aaedce4acdd.
- The lecture exercises are not needed for this game.
- This repository is the team's working project, not a finished Canvas submission.
- See groupproject/edu/txst/project/requirements.md for the full assignment.

## Feature Added: Trap Tile
- Our team added a Trap Tile (freeze trap) to the maze. 
    - CSV code 4 is a red trap. 
    - Attempting to enter it adds exactly 3 steps to the counter.
    - The player stays in place and the trap stays active. Repeated attempts each cost another 3 steps.
    - Trap tiles are drawn in RED on the board so they are visible to the player.
    - There is no extra normal-move step for a trap attempt.

## Build & Run (from the repository's root folder)
- Requires JDK 14 or later because the starter uses arrow switch statements.
```bash
mkdir -p build/classes
javac -d build/classes groupproject/edu/txst/project/*.java
java -cp build/classes edu.txst.project.MazeGUI
```

## Gameplay & Manual Testing
- Use Game > Open to choose a CSV level (e.g., level2.csv or level3.csv). Use the arrow keys to move.
- To test the trap feature manually:
    - Open tests/levels/trap-demo.csv with Game > Open.
    - The blue player starts directly left of a red trap. Steps shows 0.
    - Press Right: player stays put, trap stays red, Steps shows 3.
    - Press Right again: same positions, Steps shows 6.
    - Press Up into the wall: Steps stays 6.
    - Press Down, Right, Right, Up: player goes around to the trap's right.
    - Game > Reset: player returns to start, trap is red, Steps shows 0.

## Automated Feature Test (from repository root)
```bash
javac -d build/classes groupproject/edu/txst/project/*.java tests/edu/txst/project/TrapTileTest.java
java -cp build/classes edu.txst.project.TrapTileTest
```
- Expected: PASS, with no AssertionError.
