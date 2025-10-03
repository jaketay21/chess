package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard squares;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        squares = new ChessBoard();
        squares.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = squares.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        return piece.pieceMoves(squares,startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        HashSet<ChessMove> opponentsMoves = new HashSet<>();
        HashSet<ChessPosition> endPoints = new HashSet<>();
        for(int i = 1; i < 9; i++){
            for(int j = 1; i < 9; i++){
                ChessPosition current = new ChessPosition(i,j);
                ChessPiece piece = squares.getPiece(current);
                if(piece != null && piece.getTeamColor() != teamColor){
                    for(ChessMove move: piece.pieceMoves(squares, current)){
                        opponentsMoves.add(move);
                    }
                }

            }
        }
        for(ChessMove moves: opponentsMoves){
            endPoints.add(moves.getEndPosition());
        }
        if(endPoints.contains(findKing(teamColor))){
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        squares = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return squares;
    }

    /**
    returns the current position of the king with desired color;
     */
    public ChessPosition findKing(TeamColor color){
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition current = new ChessPosition(i,j);
                ChessPiece piece = getBoard().getPiece(current);
                if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == color){
                    return current;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(squares, chessGame.squares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, squares);
    }
}


