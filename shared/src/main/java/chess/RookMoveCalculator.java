package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMoveCalculator extends ChessMoveCalculator{


    public RookMoveCalculator() {

    }

    private boolean addToList(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(next)){
            if(!empty(board,next)){
                ChessPiece blocker = board.getPiece(next);
                if(blocker.getTeamColor() != color){
                    possibleMoves.add(new ChessMove(start, next, null));
                }
                return false;
            }else{
                possibleMoves.add(new ChessMove(start, next, null));
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();
        boolean unblockedU = true;
        boolean unblockedL = true;
        boolean unblockedR = true;
        boolean unblockedD = true;

        for (int i = 1; i <=8; i++){

            ChessPosition up = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            ChessPosition Left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -i);
            ChessPosition down = new ChessPosition(startPosition.getRow() -i, startPosition.getColumn());
            ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            if(unblockedU) {
                unblockedU = addToList(possibleMoves,startPosition, up, board, color);
            }
            if(unblockedL) {
                unblockedL = addToList(possibleMoves,startPosition, Left, board, color);
            }

            if(unblockedD) {
                unblockedD = addToList(possibleMoves,startPosition, down, board, color);
            }

            if(unblockedR) {
                unblockedR = addToList(possibleMoves,startPosition, Right, board, color);
            }

        }


        return possibleMoves;
    }
}
