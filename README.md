SmartZombie

README Author: Bennett Schalich

SmartZombie is a 2D action platformer akin to Megaman.
The object of the game is to collect brains from enemies 
and reach the end of the level. Instructions on how to play are
in the game.

This game can be downloaded at https://drive.google.com/file/d/0Bz2wK0zv0MA5Q1FNODdvUFcwbDg/view?usp=sharing

This game was made in four weeks with myself and two friends as part of my Interactive 
Entertainment Engineering class. Our group won best game out of the eight other groups. 
The game is made in Greenfoot, software that allows developers to make relatively 
simple 2D games using Java in an Object Oriented environment. 

The source files are not organized in a src folder because that is not how Greenfoot
organizes them. Sorry if this causes any confusion


My contributions to the team were:

Creating the enemy AI:
	Human - Superclass used for art
		RangedAttacker - Enemy who is stationary but shoots bullets
		Bludgeoner - Melee enemy that aggressively attacks and follows player as
						well as other zombies
	DumbZombie - Other zombies that you can kill for weapons

Key Pieces of the World:
	Platform - Superclasss used for art and dimensions
		Ground - Class used to give specialized are to ground
		MovingPlatform - Class used to enable specialized movements for a Platform

Player:
	MCZombie - Player character. My particular contribution to the MCZombie class
		was the move function and every function below it. In the "move" function the 
		player character is checking its status to see if he is near a platform for 
		collision detection or in fire. I also wrote some functions for the "act" function 
		which updates every frame to check the direction and status of player is going 
		for animation purposes and to track the players health.

Projectile Movement and Orientation:
	Bullet - Projectile shot by RangedAttacker

Hazards
	Fire - a class that holds the artwork for fire and flips the image every flame giving
		the fire a flickering effect.

What I would change:

Unfortunately with the time allotted we never had time to refactor our code.
Looking the class hierarchy of this project, there are certainly things 
that could be changed. Because all humanoids in our game needed to react to the 
in the same way, the logic for that could have been in a "Humanoid" class and inherited by
all the relevant classes. Bullet is a weapon and should be under the "Weapon" class. 
One interesting design decision we made perhaps unconsciously during this project was to 
make the MCZombie class, aka the player class, a listener. The player searches around 
itself to check for collisions, but it should be an actor and the object around it should 
be listeners and notify the player when a collision has been detected. I think we fell 
into the player-listener design because it was a quick easy solution, but the proper 
design would be that the player should only be responsible for things pertaining to 
itself, like attacking, moving etc.  
