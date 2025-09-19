package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMoveCalculator extends ChessMoveCalculator{
    private final List<ChessMove> possibleMoves;
    public KnightMoveCalculator() {
        possibleMoves = new ArrayList<>();
    }

    private void addToList(ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
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
        ChessPosition upR = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
        ChessPosition upL = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
        ChessPosition leftU = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
        ChessPosition leftD = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
        ChessPosition downL = new ChessPosition(startPosition.getRow() -2, startPosition.getColumn() -1 );
        ChessPosition downR = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
        ChessPosition rightU = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
        ChessPosition rightD = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
        addToList(startPosition,upR,board,color);
        addToList(startPosition,upL,board,color);
        addToList(startPosition,leftU,board,color);
        addToList(startPosition,leftD,board,color);
        addToList(startPosition,downL,board,color);
        addToList(startPosition,downR,board,color);
        addToList(startPosition,rightU,board,color);
        addToList(startPosition,rightD,board,color);
        return possibleMoves;
    }
}
