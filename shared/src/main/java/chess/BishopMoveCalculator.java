package chess;

import java.util.*;

public class BishopMoveCalculator extends ChessMoveCalculator {

    public BishopMoveCalculator() {}

    public boolean addToList(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition nxt, ChessBoard board, ChessGame.TeamColor color){
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
        List<ChessMove> possibleMoves = new ArrayList<>();
        boolean unblockedUpR= true;
        boolean unblockedUpL = true;
        boolean unblockedDownL = true;
        boolean unblockedDownR = true;
        for(int i = 1; i < 8; i++){

            ChessPosition upLeft = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            ChessPosition upRight = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() + i);
            ChessPosition downRight = new ChessPosition (startPosition.getRow() -i, startPosition.getColumn() + i);
            ChessPosition downLeft = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() -i);

            if(unblockedUpL){
                unblockedUpL = addToList(possibleMoves,startPosition, upLeft, board, color);
            }

            if(unblockedUpR){
                unblockedUpR = addToList(possibleMoves,startPosition, upRight, board, color);
            }

            if(unblockedDownR){
                unblockedDownR = addToList(possibleMoves,startPosition, downRight, board, color);
            }

            if(unblockedDownL){
                unblockedDownL = addToList(possibleMoves,startPosition, downLeft, board, color);
            }

        }
        return possibleMoves;

    }


}
