package chess;

import java.util.Collection;

public class ChessMoveCalculator {



    public ChessMoveCalculator() {
    }

   public Collection<ChessMove> calculateMove(ChessBoard board, ChessGame.TeamColor color,
                                              ChessPiece.PieceType promotionType, ChessPosition startPosition){
       return null;
   }

   public boolean positionValid(ChessPosition pos){
       return pos.getColumn() >= 1 && pos.getColumn() <= 8 && pos.getRow() >= 1 && pos.getRow() <= 8;

   }

    public boolean empty(ChessBoard board, ChessPosition position){
        if(board.getPiece(position) != null){
            return false;
        }
        return true;
    }
}
