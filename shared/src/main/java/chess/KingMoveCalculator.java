package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMoveCalculator extends ChessMoveCalculator {

    public KingMoveCalculator() {}

    private void addToList(List<ChessMove> allPossibleMoves, ChessPosition startPosition, ChessPosition next, ChessBoard chessBoard, ChessGame.TeamColor color){
        if(positionValid(next)){
            if(!empty(chessBoard,next)){
                ChessPiece blocker = chessBoard.getPiece(next);
                if(blocker.getTeamColor() != color){
                    allPossibleMoves.add(new ChessMove(startPosition, next, null));
                }
            }else{
                allPossibleMoves.add(new ChessMove(startPosition, next, null));
            }
        }
    }

    @Override
    public Collection<ChessMove> calculateMove(ChessBoard board, ChessGame.TeamColor color,
                                               ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition up = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
        ChessPosition upL = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
        ChessPosition left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -1);
        ChessPosition downL = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
        ChessPosition down = new ChessPosition(startPosition.getRow() -1, startPosition.getColumn());
        ChessPosition downR = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
        ChessPosition right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
        ChessPosition upR = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
        addToList(possibleMoves, startPosition,up,board,color);
        addToList(possibleMoves, startPosition,upL,board,color);
        addToList(possibleMoves, startPosition,left,board,color);
        addToList(possibleMoves, startPosition,downL,board,color);
        addToList(possibleMoves, startPosition,down,board,color);
        addToList(possibleMoves, startPosition,downR,board,color);
        addToList(possibleMoves, startPosition,right,board,color);
        addToList(possibleMoves, startPosition,upR,board,color);
        return possibleMoves;
    }

}
