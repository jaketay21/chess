package chess;

import java.util.*;

public class BishopMoveCalculator extends ChessMoveCalculator {
   private List<ChessMove> possibleMoves;
    private  boolean unblockedUpR= true;
    private  boolean unblockedUpL = true;
    private  boolean unblockedDownL = true;
    private  boolean unblockedDownR = true;

    public BishopMoveCalculator() {
        possibleMoves = new ArrayList<>();
    }

    public boolean addToList(ChessPosition start, ChessPosition nxt, ChessBoard board, ChessGame.TeamColor color){
        if (positionValid(nxt)){
            if(!empty(board,nxt)){
                ChessPiece blocker = board.getPiece(nxt);
                if(color != blocker.getTeamColor()){
                    possibleMoves.add(new ChessMove(start, nxt, null));
                }
                return false;
            }else {
                possibleMoves.add(new ChessMove(start, nxt, null));
                return true;
            }
        }
        return false;
    }
    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition) {

        //cover whole board because bishop can move from one corner to the other
        for(int i = 1; i < 8; i++){
            /*
            checks if path is unblocked
            if unblocked update next possible move
            check if next pos is occupied
            if occupied update unblocked to false, stops calculating for that direction
            if unoccupied add position to list
             */
            ChessPosition upLeft = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            ChessPosition upRight = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() + i);
            ChessPosition downRight = new ChessPosition (startPosition.getRow() -i, startPosition.getColumn() + i);
            ChessPosition downLeft = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() -i);

            if(unblockedUpL){
                unblockedUpL = addToList(startPosition, upLeft, board, color);
            }

            if(unblockedUpR){
                unblockedUpR = addToList(startPosition, upRight, board, color);
            }

            if(unblockedDownR){
                unblockedDownR = addToList(startPosition, downRight, board, color);
            }

            if(unblockedDownL){
                unblockedDownL = addToList(startPosition, downLeft, board, color);
            }

        }
        return possibleMoves;

    }


}
