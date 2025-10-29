package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMoveCalculator extends ChessMoveCalculator{

    public KnightMoveCalculator() {

    }

    private void addToList(List<ChessMove> possibleMoves,ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
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
    public Collection<ChessMove> calculateMove(ChessBoard board, ChessGame.TeamColor color,
                                               ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition upR = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
        ChessPosition upL = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
        ChessPosition leftU = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
        ChessPosition leftD = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
        ChessPosition downL = new ChessPosition(startPosition.getRow() -2, startPosition.getColumn() -1 );
        ChessPosition downR = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
        ChessPosition rightU = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
        ChessPosition rightD = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
        addToList(possibleMoves,startPosition,upR,board,color);
        addToList(possibleMoves,startPosition,upL,board,color);
        addToList(possibleMoves,startPosition,leftU,board,color);
        addToList(possibleMoves,startPosition,leftD,board,color);
        addToList(possibleMoves,startPosition,downL,board,color);
        addToList(possibleMoves,startPosition,downR,board,color);
        addToList(possibleMoves,startPosition,rightU,board,color);
        addToList(possibleMoves,startPosition,rightD,board,color);
        return possibleMoves;
    }
}
