package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        // TODO: Task 1. Fill in this function.
        // TODO: This method should return true if any of the tiles on the board are null.

        for(int i = 0; i<size(); i++){
            for(int j = 0 ; j <size() ; j++){
                if(tile(i,j)==null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        // TODO: Task 2. Fill in this function.
        // TODO: This method should return true if any of the tiles on the board have the winning value (default 2048).
        for(int i = 0; i<size(); i++){
            for(int j = 0 ; j <size() ; j++){
                if(tile(i,j)!=null){
                    if(tile(i,j).value()==MAX_PIECE){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        // TODO: Task3. Fill in this function.
        // TODO: This method should return true if there are any valid moves.
        //  A valid move exists if there is a button (up, down, left, right) that
        //  the player can press that would cause at least one tile to move.

        // valid move 1: There is at least one empty space on the board.
        if (emptySpaceExists()) {
            return true;
        }

        // valid move 2: There are two adjacent (there can be empty space between them) tiles with the same value.
        // in valid judgement 1, it has already been judged,
        // therefore only need to find if any two tiles are the same

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (((i+1<size()) && tile(i, j).value() == tile(i + 1, j).value())
                                || ((j+1<size()) && tile(i, j).value() == tile(i, j+1).value())){
                    return true;
                }
            }
        }

        return false;
    }



    // TODO: Task4: Understand the rules of Tilts
    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */

    public void moveTileUpAsFarAsPossible(int x, int y){
        Tile currTile = board.tile(x, y);        // Tile t = board.tile(x0, y0);
        if (currTile == null) return; // 新增：检查当前Tile是否为空，避免空指针异常    //*******
        int myValue = currTile.value();
        int targetY = y;

        // TODO: Tasks 5, 6, and 10. Fill in this function.

        // TODO: Task5, Move Tile Up (Without Merging Logic, Up Direction Only)

        // TODO: Task6: Merging Tiles
        // using wasMerged() to determine whether to merge

        boolean merge = false;
        int i = y+1;
        while(i<size()){
            if(tile(x,i)==null){
                targetY = i;
            }
            else{
                if((tile(x,i).value()==myValue) && (!tile(x,i).wasMerged())){
                    merge = true;
                    targetY = i;
                }
                else{
                    targetY = i-1;
                }
                break;
            }
            i++;
        }

        // TODO: Task10 - Score
        if(merge&&(tile(x,y)!=null)&&(tile(x,targetY)!=null)){
            score += myValue*2;
        }

        // Avoid move to the place itself
        if(targetY!=y){
            board.move(x,targetY,tile(x,y));        // board.move(x, y, t);
        }
    }

    /** Handles the movements of the tilt in column x of the board
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        // TODO: Task 7. Fill in this function.
        // This method should tilt the given column at coordinate x up,
        // moving all of the tiles in that column into their rightful place,
        // and merging any tiles in that column that need to be merged.

        //moveTileUpAsFarAsPossible(int x, int y)
        //from up to down (the order matters)
        for(int i=size()-1; i>=0; i--){
            if(tile(x,i)!=null){
                moveTileUpAsFarAsPossible(x, i);
            }
        }


    }

    public void tilt(Side side) {
        // TODO: Tasks 8 and 9. Fill in this function.

        // TODO: Task 8: Tilt Up
        // This method should tilt the entire board up,
        // moving all tiles in all columns into their rightful place,
        // and merging any tiles that need to be merged.

        // TODO: Task 9: Tilt in Four Directions
        //Side s = Side.NORTH
        //setViewingPerspective(Side s)
        //board.setViewingPerspective(Side.NORTH), which will make the board behave as if NORTH was NORTH

        board.setViewingPerspective(side);
        for(int i=0;i<size();i++){
            tiltColumn(i);
        }
        board.setViewingPerspective(Side.NORTH);


    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
