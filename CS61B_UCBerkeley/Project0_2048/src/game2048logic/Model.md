# Model Class Structure

## Class: Model

----

### Fields

- `private final Board board;`
    - Represents the game board.

- `private int score;`
    - Represents the current score.

----

### Constants

- `public static final int MAX_PIECE = 2048;`
    - Represents the largest piece value.

----

### Constructors

- `public Model(int size)`
    - Initializes a new 2048 game on a board of specified size with no pieces and a score of 0.

- `public Model(int[][] rawValues, int score)`
    - Initializes a new 2048 game where `rawValues` contain the values of the tiles. Used for testing purposes.

----

### Methods

#### Public Methods

- `public Tile tile(int x, int y)`
    - Returns the current Tile at `(x, y)`, or `null` if there is no tile there.

- `public int size()`
    - Returns the number of squares on one side of the board.

- `public int score()`
    - Returns the current score.

- `public void clear()`
    - Clears the board to empty and resets the score.

- `public void addTile(Tile tile)`
    - Adds a tile to the board. There must be no tile currently at the same position.

- `public boolean gameOver()`
    - Returns `true` if the game is over (no moves or a tile with value 2048 exists).

- `public Board getBoard()`
    - Returns this Model's board.

- `public boolean emptySpaceExists()`
    - Returns `true` if at least one space on the board is empty (i.e., null).

- `public boolean maxTileExists()`
    - Returns `true` if any tile is equal to the maximum valid value (`MAX_PIECE`).

- `public boolean atLeastOneMoveExists()`
    - Returns `true` if there are any valid moves on the board (either an empty space or two adjacent tiles with the same value).

- `public void moveTileUpAsFarAsPossible(int x, int y)`
    - Moves the tile at position `(x, y)` as far up as possible.

- `public void tiltColumn(int x)`
    - Handles the movements of the tilt in column `x` by moving every tile in the column as far up as possible.

- `public void tilt(Side side)`
    - Tilts every column of the board toward `side`.

- `public void tiltWrapper(Side side)`
    - Resets merged status and tilts the board toward `side`.

#### Overridden Methods

- `@Override public String toString()`
    - Returns a string representation of the board state.

- `@Override public boolean equals(Object o)`
    - Checks if this Model is equal to another object.

- `@Override public int hashCode()`
    - Returns the hash code of this Model.
