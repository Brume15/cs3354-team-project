CS 3354 Team Project

Starter source: https://github.com/albertocahdez/cs3354
Copied only groupproject/ from instructor commit
00e1fd7a0382c1e8b8d405e6ad0f2aaedce4acdd.
The lecture exercises are not needed for this game.

This repository is the team's working project, not a finished Canvas submission.
See groupproject/edu/txst/project/requirements.md for the full assignment.

BUILD AND RUN (from the repository's root folder)
Requires JDK 14 or later because the starter uses arrow switch statements.

mkdir -p build/classes
javac -d build/classes groupproject/edu/txst/project/*.java
java -cp build/classes edu.txst.project.MazeGUI

Use Game > Open to choose a CSV level. Use the arrow keys to move.

BRUME'S TRAP TILE FEATURE (feature/trap-tile)
CSV code 4 is a red trap. Attempting to enter it adds exactly 3 steps.
The player stays in place and the trap stays active. Repeated attempts each
cost another 3. There is no extra normal-move step for a trap attempt.
Game > Reset or opening another level refreshes the step label to zero.

MANUAL FEATURE TEST
Open tests/levels/trap-demo.csv with Game > Open.
1. The blue player starts directly left of a red trap. Steps shows 0.
2. Press Right: player stays put, trap stays red, Steps shows 3.
3. Press Right again: same positions, Steps shows 6.
4. Press Up into the wall: Steps stays 6.
5. Press Down, Right, Right, Up: player goes around to the trap's right.
6. Game > Reset: player returns to start, trap is red, Steps shows 0.
7. Press Right: Steps shows 3 again.
8. Open the demo again: Steps shows 0.

AUTOMATED FEATURE TEST (from repository root)
javac -d build/classes groupproject/edu/txst/project/*.java tests/edu/txst/project/TrapTileTest.java
java -cp build/classes edu.txst.project.TrapTileTest
Expected: PASS, with no AssertionError.

TEAM INTEGRATION STILL REQUIRED
The instructor starter's ordinary step counter, coin collection, win detection,
and score calculation remain unfinished. This branch implements the trap feature
and the step-label refresh it needs. Normal movement still adds zero in this
starter until Benjamin's fix is merged. Do not mistake that for trap behavior.
Benjamin: add the successful-move +1 AFTER the trap branch's early return.
Eladio: score = -steps + 5 * coins; use the total that includes trap penalties.
After those fixes, repeat the same route to the exit with zero and two trap
attempts; the second score must be exactly 6 lower, with the same coins.
A test of the actual final-score dialog must wait for those fixes.
The demo route to the exit is Down, Right seven times, Up.
With the normal-move fix this takes 9 steps; two trap attempts make it 15.
With no collected coins, the corresponding final scores are -9 and -15.

The test CSV is a small feature fixture. The team must still provide its two
additional submission levels, complete the Javadoc pass, generate /javadoc,
and finalize the submission README.txt and ZIP as requirements.md specifies.
The handout says the trap idea was reserved; that Canvas claim was not checked
from this repository. Confirm it with the team before submission.

For the game explanation, code walkthrough, and Git workflow, see
TRAP_TILE_GUIDE.txt.
