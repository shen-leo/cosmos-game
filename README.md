# Cosmos

Cosmos is a tile-based strategy game where the player battles the peculiar monstrosities of deep space. You play as a
Cosmic Knight, a warrior sent to explore the deep Cosmos in order to vanquish the deadly creatures that threaten planet 
Earth. Collect the Souls found on the various planets of space in order to gain energy to travel deeper into the Cosmos. Be ready to face the strange array of monsters that await you in the Cosmos.

# Gameplay

Use WASD or the directional keys to navigate the Cosmic Knight around the space dungeon tilemap. Coming into contact with an
enemy will defeat them at the expense of losing one of your three lives. Be careful! The enemies will follow your 
movement!

You can collect a shield item to prevent life loss when fighting an enemy. Collecting a Red Potion will restore one life
to the Cosmic Knight.

The goal of the game is to collect the required amount of Souls in order to progress to the next level. As you go higher
in level through the dungeons, you will be required to collect a greater number of souls in order to progress.

Challenge yourself by collecting as many Souls as possible within the time limit!

# MySQL User Authentication

For the user authentication and stat tracking system to work, the local instance of the game must have the local user's 
MySQL credentials in the LoginForm (line 77), RegistrationForm (line 115), and User (line 105) classes. This will
facilitate the connection between MySQL and Java. The database schema is called comp2522-game-cosmos.sql. Please ensure 
to run the code in the SQL file to create the database schema on your local machine.

The default test account is **Username: test**, **Password: test1234**. The password will be stored in the MySQL
database in its encrypted form.

# Citations
All the assets and sound used are royalty free, and under the public domain copyright.

### Enemy Ai
Our enemy AI is a modified version of the Dijkstra's search algorithm. Original code can be found [here](https://gamedev.stackexchange.com/questions/197165/java-simple-2d-grid-pathfinding).

### Password Encryption

Swing UI registration form created using BoostMyTool's [video guide](https://www.youtube.com/watch?v=nIQatIpL_GE&ab_channel=BoostMyTool).

Swing UI login form created using BoostMyTool's [video guide](https://www.youtube.com/watch?v=bandCz619c0&ab_channel=BoostMyTool).

Password hashing created through Lokesh Gupta's [guide](https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/).

### Character Sprites

Player sprite by [DeepDiveGameStudio](https://deepdivegamestudio.itch.io/undead-asset-pack).

Enemy sprites by [DeepDiveGameStudio](https://deepdivegamestudio.itch.io/undead-asset-pack).

### Visual Effects

Game backdrops by [Wenrexa](https://wenrexa.itch.io/spacebq2).

Enemy explosion by [pimen](https://pimen.itch.io/fire-spell-effect-02).

Game board created using [blackdragon1727's](https://blackdragon1727.itch.io/pixel-tilemap-platformer) tilemap assets.

Soul sprite by [pimen](https://codemanu.itch.io/pixelart-effect-pack).

Potion sprite by [Flip](https://flippurgatory.itch.io/animated-potion-assets-pack-free).

Shield sprite by [Free Game Assets](https://free-game-assets.itch.io/free-shield-and-amulet-rpg-icons).

### Sound effects
[Coin sound effect](https://www.fesliyanstudios.com/royalty-free-sound-effects-download/coin-272)

[Sword sound effect](https://www.youtube.com/watch?v=SwJNDq8CQSk&ab_channel=PoppiHolla)

[Heart sound effect](https://pixabay.com/sound-effects/search/pickup/)

[Enemy death sound effect](https://www.fesliyanstudios.com/royalty-free-sound-effects-download/knife-stabbing-75)

### Music 
Background music made by Holizna can be found on [freemusicarchive](https://freemusicarchive.org/genre/Ambient).

