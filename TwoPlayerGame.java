public interface TwoPlayerGame {

    /**
    * Returns true if the specification of a move describes a legal move
    * given the rules of the game. Note: this does not check whether the
    * move is a *good* move, only whether the move is legal.
    * The move specified is checked against all game rules.
    *
    * A move is described by:
    *   - a resource (e.g., position, gamepiece, pile of matches, etc.)
    *   - a description of the move (e.g., how many matches to remove, how
    *                                many spaces to move, etc.)
    * @param resource describes the game element
    * @param units describes the move
    * @return true if the move is valid
    */
    public boolean isValidMove(int resource, int units);

    /**
    * At the conclusion of this method, the game state is changed to represent
    * the completion of the described move. If the described move is not valid,
    * the behavior of this method is undefined.
    *
    *
    * A move is described by:
    *   - a resource (e.g., position, gamepiece, pile of matches, etc.)
    *   - a description of the move (e.g., how many matches to remove, how
    *                                many spaces to move, etc.).
    *
    * @param resource describes the game element
    * @param units describes the move
    */
    public void makeMove(int resource, int units);

    /**
    * Returns true if the game is over.
    * @return True if the game is over, false otherwise.
    */
    public boolean isGameOver();
}