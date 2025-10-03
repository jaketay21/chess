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
        if(isEmpty(squares, startPosition)){
            return Collections.emptySet();
        }
        ChessPiece piece = squares.getPiece(startPosition);
        TeamColor opColor = (piece.getTeamColor() == TeamColor.WHITE) ? TeamColor.BLACK: TeamColor.WHITE;
        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            HashSet<ChessMove> myMoves = transferMoves(piece.pieceMoves(squares,startPosition));
            HashSet<ChessPosition> opEndPoints = (HashSet<ChessPosition>) getPossibleMoves(squares,opColor);
            for (ChessMove move: piece.pieceMoves(squares,startPosition)){
                if(opEndPoints.contains(move.getEndPosition())){
                    myMoves.remove(move);
                }
            }
            return myMoves;
        }
        return Collections.emptySet();

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
        TeamColor opponentColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        HashSet<ChessPosition> oppenentMoves= (HashSet<ChessPosition>) getPossibleMoves(squares, opponentColor);;
        if(oppenentMoves.contains(findKing(squares,teamColor))){
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
        if(isInCheck(teamColor)){
            return false;
        }
        return true;
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
    public ChessPosition findKing( ChessBoard board, TeamColor color){
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition current = new ChessPosition(i,j);
                if(!isEmpty(board,current)){
                    ChessPiece piece = board.getPiece(current);
                    if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == color){
                        return current;
                    }
                }

            }
        }
        return null;
    }
    //quick check to see if that square is empty
    public boolean isEmpty(ChessBoard board, ChessPosition pos){
        if(board.getPiece(pos) == null) return true;
        return false;
    }

    public Collection<ChessPosition> getPossibleMoves( ChessBoard board, TeamColor teamColor){
        HashSet<ChessMove> possiblemMoves = new HashSet<>();
        HashSet<ChessPosition> endPositions = new HashSet<>();
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition current = new ChessPosition(i,j);
                if(!isEmpty(board,current)){
                    ChessPiece piece = board.getPiece(current);
                    if(piece.getTeamColor() == teamColor){
                        for(ChessMove move: piece.pieceMoves(board,current)){
                            possiblemMoves.add(move);
                        }
                    }

                }
            }
        }
        for(ChessMove move: possiblemMoves){
            endPositions.add(move.getEndPosition());
        }

        return endPositions;
    }
    public HashSet<ChessMove> transferMoves(Collection<ChessMove> pieceMoves){
        HashSet<ChessMove> nextSet = new HashSet<>();
        for(ChessMove move: pieceMoves){
            nextSet.add(move);
        }
        return nextSet;
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


