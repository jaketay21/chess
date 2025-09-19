package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMoveCalculator extends ChessMoveCalculator {
    private List<ChessMove> possibleMoves;
    public KingMoveCalculator() {
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
        ChessPosition up = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
        ChessPosition upL = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
        ChessPosition Left = new ChessPosition(startPosition.getRow() , startPosition.getColumn() -1);
        ChessPosition downL = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
        ChessPosition down = new ChessPosition(startPosition.getRow() -1, startPosition.getColumn());
        ChessPosition downR = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
        ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
        ChessPosition upR = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
        addToList(startPosition,up,board,color);
        addToList(startPosition,upL,board,color);
        addToList(startPosition,Left,board,color);
        addToList(startPosition,downL,board,color);
        addToList(startPosition,down,board,color);
        addToList(startPosition,downR,board,color);
        addToList(startPosition,Right,board,color);
        addToList(startPosition,upR,board,color);
        return possibleMoves;
    }

}
