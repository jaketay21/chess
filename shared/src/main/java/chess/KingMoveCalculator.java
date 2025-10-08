package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMoveCalculator extends ChessMoveCalculator {

    public KingMoveCalculator() {}

    private void addToList(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(next)){
            if(!empty(board,next)){
                ChessPiece blocker = board.getPiece(next);
                if(blocker.getTeamColor() != color){
                    possibleMoves.add(new ChessMove(start, next, null));
                }
            }else{
                possibleMoves.add(new ChessMove(start, next, null));
            }
        }
    }

    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition up = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
        ChessPosition upL = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
        ChessPosition Left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -1);
        ChessPosition downL = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
        ChessPosition down = new ChessPosition(startPosition.getRow() -1, startPosition.getColumn());
        ChessPosition downR = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
        ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
        ChessPosition upR = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
        addToList(possibleMoves, startPosition,up,board,color);
        addToList(possibleMoves, startPosition,upL,board,color);
        addToList(possibleMoves, startPosition,Left,board,color);
        addToList(possibleMoves, startPosition,downL,board,color);
        addToList(possibleMoves, startPosition,down,board,color);
        addToList(possibleMoves, startPosition,downR,board,color);
        addToList(possibleMoves, startPosition,Right,board,color);
        addToList(possibleMoves, startPosition,upR,board,color);
        return possibleMoves;
    }

}
