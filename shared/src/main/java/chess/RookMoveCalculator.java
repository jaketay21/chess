package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMoveCalculator extends ChessMoveCalculator{
    private List<ChessMove> possibleMoves;
    private boolean unblockedU = true;
    private boolean unblockedL = true;
    private boolean unblockedR = true;
    private boolean unblockedD = true;

    public RookMoveCalculator() {
        possibleMoves = new ArrayList<>();
    }

    private boolean addToList(ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
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
        for (int i = 1; i <=8; i++){
            ChessPosition up = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            ChessPosition Left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -i);
            ChessPosition down = new ChessPosition(startPosition.getRow() -i, startPosition.getColumn());
            ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            if(unblockedU) {
                unblockedU = addToList(startPosition, up, board, color);
            }
            if(unblockedL) {
                unblockedL = addToList(startPosition, Left, board, color);
            }

            if(unblockedD) {
                unblockedD = addToList(startPosition, down, board, color);
            }

            if(unblockedR) {
                unblockedR = addToList(startPosition, Right, board, color);
            }

        }


        return possibleMoves;
    }
}
