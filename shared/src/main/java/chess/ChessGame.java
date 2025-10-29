package chess;

import javax.swing.text.html.HTMLDocument;
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
        HashSet<ChessMove> myMoves = transferMoves(piece.pieceMoves(squares,startPosition));
        System.out.println("Checking valid moves for: " + startPosition);
        for (ChessMove move : myMoves) {
            System.out.println("  Move: " + move);
        }
        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            HashSet<ChessMove> illegalMoves = new HashSet<>();
            for (ChessMove move : myMoves) {
                ChessBoard simulate = new ChessBoard(squares);
                ChessPiece copy = new ChessPiece(piece.getTeamColor(), piece.getPieceType());
                simulate.addPiece(move.getEndPosition(), copy);
                simulate.addPiece(startPosition, null);
                HashSet<ChessPosition> opEndPoints = (HashSet<ChessPosition>) getPossibleMoves(simulate, opColor);
                if (opEndPoints.contains(move.getEndPosition())) {
                    illegalMoves.add(move);
                }
            }
            myMoves.removeAll(illegalMoves);
            return myMoves;
        }
        HashSet<ChessMove> illegalMoves = new HashSet<>();
        for(ChessMove move: piece.pieceMoves(squares, startPosition)){
            ChessBoard simulate = new ChessBoard(squares);
            ChessPiece copy = new ChessPiece(piece.getTeamColor(),piece.getPieceType());
            simulate.addPiece(move.getEndPosition(),copy);
            simulate.addPiece(startPosition, null);
            HashSet<ChessPosition> opEndPoints = (HashSet<ChessPosition>) getPossibleMoves(simulate,opColor);
            if(opEndPoints.contains(findKing(simulate,piece.getTeamColor()))){
                illegalMoves.add(move);
            }
        }
        myMoves.removeAll(illegalMoves);
        return myMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        TeamColor turn = getTeamTurn();
        ChessPosition start = move.getStartPosition();
        ChessPiece piece = squares.getPiece(start);
        if(squares.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }
        if(piece.getTeamColor() != turn){
            throw new InvalidMoveException();
        }
        Collection<ChessMove> moves = validMoves(start);
        if (!moves.contains(move)) {
            throw new InvalidMoveException();
        }

        if(piece.getPieceType() == ChessPiece.PieceType.PAWN){
            if (turn == TeamColor.WHITE && start.getRow() == 7 ){
                squares.addPiece(move.getEndPosition(), new ChessPiece(turn,move.getPromotionPiece()));
            }else if (turn == TeamColor.BLACK && start.getRow() == 2 ){
                squares.addPiece(move.getEndPosition(), new ChessPiece(turn,move.getPromotionPiece()));

            }else{
                squares.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(),piece.getPieceType()));
            }
        }else {
            squares.addPiece(move.getEndPosition(), piece);
        }
        squares.addPiece(move.getStartPosition(), null);
        if(turn == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }else{
            setTeamTurn(TeamColor.WHITE);
        }
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
        if(isInCheck(teamColor)){
            System.out.println("king in Check");
            System.out.println(squares.toString());
            HashSet<ChessMove> possibleOuts = (HashSet<ChessMove>) getValidMoves(teamColor);
            System.out.println(possibleOuts.toString());
            if(possibleOuts.isEmpty()){
                System.out.println("Game over");
                System.out.println(getValidMoves(teamColor).toString());
                return true;
            }
        }
        return false;


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
        if(getValidMoves(teamColor).isEmpty()){
            return true;
        }

        return false;
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
        throw new IllegalStateException("King not found");
    }
    //quick check to see if that square is empty
    public boolean isEmpty(ChessBoard board, ChessPosition pos){
        if (board.getPiece(pos) == null) {
            return true;
        }
        return false;
    }

    public Collection<ChessPosition> getPossibleMoves(ChessBoard board, TeamColor teamColor) {
        Set<ChessMove> possibleMoves = collectMoves(board, teamColor);
        return extractEndPositions(possibleMoves);
    }

    private Set<ChessMove> collectMoves(ChessBoard board, TeamColor teamColor) {
        Set<ChessMove> moves = new HashSet<>();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                if (isEmpty(board, position)){
                    continue; // ✅ guard clause removes nesting
                }

                ChessPiece piece = board.getPiece(position);
                if (piece.getTeamColor() != teamColor){
                    continue; // ✅ guard clause again
                }

                moves.addAll(piece.pieceMoves(board, position));
            }
        }

        return moves;
    }

    private Set<ChessPosition> extractEndPositions(Set<ChessMove> moves) {
        Set<ChessPosition> positions = new HashSet<>();
        for (ChessMove move : moves) {
            positions.add(move.getEndPosition());
        }
        return positions;
    }


    public Collection<ChessMove> getValidMoves(TeamColor color){
        HashSet<ChessMove> validMoveSet = new HashSet<>();
        for (int i = 1; i < 9; i++){
            for(int j = 1; j< 9; j++){
                ChessPosition current = new ChessPosition(i,j);
                if(!isEmpty(squares, current)){
                    ChessPiece piece = squares.getPiece(current);
                    if(piece.getTeamColor() == color){
                        validMoveSet.addAll(validMoves(current));
                    }
                }
            }
        }
        return validMoveSet;
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


