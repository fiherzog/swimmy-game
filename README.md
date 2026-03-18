# Swimmy 🐟

A Flappy Bird-style swimming game where you guide a koi fish through an underwater obstacle course. Fight against gravity, dodge incoming pipes, and watch out for swinging fish hooks!

**Prerequisites:**

Java 8 or higher — Download here
Any Java-compatible IDE (IntelliJ IDEA, Eclipse, VS Code with Java extensions, etc.)

**How to Run**

Open the project folder via File → Open
Make sure the src directory is marked as the Sources Root (right-click → Mark Directory as → Sources Root)
Locate Game.java and click the green Run button, or right-click → Run 'Game.main()'

**How to Play**

Guide your koi fish through an endless underwater gauntlet. Pipes and swinging hooks scroll toward you — don't touch them, and don't let gravity pull you off-screen!
↑ (Up Arrow): Swim upward (fight gravity!)
Space: Pause / Unpause
Q: Save game and quit
C: Continue from last saved game
Reset button: Restart from the beginning
Scoring: You earn a point for each pair of pipes you successfully pass through. Your high score is saved between sessions.

**Obstacles**

Pipes — Pairs of pipes scroll in from the right with a gap to swim through. The gap position varies randomly each time.
Fish Hooks (Pendulums) — Swinging hooks that drop in periodically. Their arc widens as they travel across the screen — time your movements carefully!


**Save & Load**
The game automatically saves your high score. Pressing Q saves your full game state (score, fish position, and all active obstacles) to files/data.txt, which you can resume later by pressing C at the start screen.
