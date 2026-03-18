=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 47803522
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections: I implemented collections to store an array list of all
  the obstacles present in my game. I iterate through this array list to check for collisions,
  drawing the obstacles and storing the appropriate data for File I/O.

  2. File I/O: I implemented this by ensuring that when the user presses q to quit
  the game, they are able to reload and return to the same spot as they were when they
  rerun the game. This happens when the user presses c. With File I/O, I store the
  position of the bird, the position of the obstacles and the current score + high score.
  Even when the user doesn't choose to save their progress, I ensure that the high score is
  always saved, even when you rerun the game.

  3. Inheritance and Subtyping: I implemented this by creating an Obstacle class. In it
  are methods for update and draw. Both pipe and pendulum extend obstacle. Their update and draw
  methods both differ greatly from each other as the pendulum swings back and forth as it moves
  across the string, while the pipe has type north and type south I need to account for.
  I use dynamic dispatch by creating an array list of obstacles that updates and draws everything
  as needed, rather than create several array lists and iterate through them multiple times.

  4. JUnit Testable Component: I tested various states and components of the game to ensure that
  everything runs smoothly, besides what I can see visually when running the game.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
Arena: This is where the bulk of the game comes together. In this class, I account for collisions,
draw the different game states and images in the game, increment points and set the screen of the game.
FileUtilities: In this class I write methods so that the we can save the data from the game and load
it into the data.txt file.
Fish: In this class I create the fish! Gravity is set so that the fish is constantly decrementing,
as well as limiting the effects of gravity when the user presses the up key.
LineIterator: This is a class that allows for us to parse the data in data.txt and load it into the game
so that the player can resume the game state.
Obstacle: This is a general class that Pendulum and Pipe extend. Its purpose is so that we only need
one array list that we iterate through rather than several for every single task (such as drawing,
collisions and more)
Pendulum: This is a fishhook thing that swings back and forth across the screen! It extends obstacle and
if the user crashes into it, they die.
Pipe: This is your standard pipe that moves across the screen that you associate with flappy bird. In
my game since it is underwater, I refer to it as coral in the game start screen. Pipe also extends obstacle.
Additionally, there is a "north"  pipe and a "south" pipe.
RunSwimmy: this is where the game is run.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
Originally, I was going to try to go for the Advanced Manipulations concept where I implemented
velocity to have the bounce around when it died. That ended up being way too hard for me to even begin
to attempt. I was stumped for several days trying to figure out how to get another concept. I settled on
doing Inheritance and implementing more managable physics with the hook swinging back and forth.
Other than that, I had a really fun time completing this assignment. I loved drawing my images and coding
the game. I told all my friends about this and how much fun I was having.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I tried to follow Mushroom of Doom as closely as I could in terms of maintaining the game state
and separation of functionality. I think one caveat to my game is that because of the fish png not
filling out the entirety of the box, some collisions look strange. As a result, I relied more so on the
visual aspect for collisions, rather than what a "true" collision was.

If I could, I would try to refactor fish, pendulum and pipe to share some sort of greater class
or interface. Fish doesn't really have that many unique methods. Additionally, I would try to
expand upon this game to create little coins that the fish could collect.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  None- I hand-drew the images!

