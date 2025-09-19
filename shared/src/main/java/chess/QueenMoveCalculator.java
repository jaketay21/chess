package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMoveCalculator extends ChessMoveCalculator{
    private List<ChessMove> possibleMoves;
    private boolean unblockedU = true;
    private boolean unblockedUL = true;
    private boolean unblockedUR = true;
    private boolean unblockedL = true;
    private boolean unblockedR = true;
    private boolean unblockedDL = true;
    private boolean unblockedDR = true;
    private boolean unblockedD = true;

    public QueenMoveCalculator () {
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
            ChessPosition upL = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            ChessPosition Left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -i);
            ChessPosition downL = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            ChessPosition down = new ChessPosition(startPosition.getRow() -i, startPosition.getColumn());
            ChessPosition downR = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            ChessPosition upR = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            if(unblockedU) {
                unblockedU = addToList(startPosition, up, board, color);
            }
            if(unblockedUL) {
                unblockedUL = addToList(startPosition, upL, board, color);
            }
            if(unblockedL) {
                unblockedL = addToList(startPosition, Left, board, color);
            }
            if(unblockedDL) {
                unblockedDL = addToList(startPosition, downL, board, color);
            }
            if(unblockedD) {
                unblockedD = addToList(startPosition, down, board, color);
            }
            if(unblockedDR) {
                unblockedDR = addToList(startPosition, downR, board, color);
            }
            if(unblockedR) {
                unblockedR = addToList(startPosition, Right, board, color);
            }
            if(unblockedUR) {
                unblockedUR = addToList(startPosition, upR, board, color);
            }
        }
        return possibleMoves;
    }
}
