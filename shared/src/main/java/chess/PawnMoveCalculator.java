package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.WHITE;

public class PawnMoveCalculator extends ChessMoveCalculator{


    public PawnMoveCalculator() {

    }

    private boolean addToList(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(next)){
            if(next.getRow() != 1 && next.getRow() != 8 ) {
                if (!empty(board, next)) {
                    return false;
                } else {
                    possibleMoves.add(new ChessMove(start, next, null));
                    return true;
                }
            }else{
                if (!empty(board, next)) {
                    return false;
                } else {
                    possibleMoves.add(new ChessMove(start, next, ChessPiece.PieceType.QUEEN));
                    possibleMoves.add(new ChessMove(start, next, ChessPiece.PieceType.KNIGHT));
                    possibleMoves.add(new ChessMove(start, next, ChessPiece.PieceType.ROOK));
                    possibleMoves.add(new ChessMove(start, next, ChessPiece.PieceType.BISHOP));

                    return true;
                }
            }

        }
        return false;
    }

    public void attack(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition nxt, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(nxt)){
            if(nxt.getRow() != 1 && nxt.getRow() != 8) {
                if (!empty(board, nxt)) {
                    ChessPiece blocker = board.getPiece(nxt);
                    if (blocker.getTeamColor() != color) {
                        possibleMoves.add(new ChessMove(start, nxt, null));
                    }
                }
            }else{
                if (!empty(board, nxt)) {
                    ChessPiece blocker = board.getPiece(nxt);
                    if (blocker.getTeamColor() != color) {
                        possibleMoves.add(new ChessMove(start, nxt,  ChessPiece.PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(start, nxt,  ChessPiece.PieceType.ROOK));
                        possibleMoves.add(new ChessMove(start, nxt,  ChessPiece.PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(start, nxt,  ChessPiece.PieceType.BISHOP));

                    }
                }
            }

        }
    }

    @Override
    public Collection<ChessMove> calculateMove(ChessBoard board, ChessGame.TeamColor color,
                                               ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();
        boolean unblockedF = true;
        if(color ==  WHITE){
            ChessPosition attackL = new ChessPosition(startPosition.getRow() + 1,startPosition.getColumn() - 1);
            ChessPosition attackR = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);

            if(startPosition.getRow() == 2){
                for(int i = 1; i < 3; i ++){
                    ChessPosition forward = new ChessPosition(startPosition.getRow()+ i, startPosition.getColumn());
                    if(unblockedF){
                        unblockedF = addToList(possibleMoves, startPosition, forward, board,color);
                    }
                }
                attack(possibleMoves,startPosition,attackR,board,color);
                attack(possibleMoves,startPosition,attackL,board,color);

            }else{
                ChessPosition forward = new ChessPosition(startPosition.getRow()+ 1, startPosition.getColumn());
                unblockedF = addToList(possibleMoves,startPosition, forward, board,color);
                attack(possibleMoves,startPosition,attackR,board,color);
                attack(possibleMoves,startPosition,attackL,board,color);


            }

        }else{
            ChessPosition attackL = new ChessPosition(startPosition.getRow() - 1,startPosition.getColumn() - 1);
            ChessPosition attackR = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);

            if(startPosition.getRow() == 7){
                for(int i = 1; i < 3; i++) {
                    ChessPosition forward = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
                    if (unblockedF) {
                        unblockedF = addToList(possibleMoves, startPosition, forward, board, color);
                    }
                }

                attack(possibleMoves,startPosition,attackR,board,color);
                attack(possibleMoves,startPosition,attackL,board,color);

            }else{
                ChessPosition forward = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                unblockedF = addToList(possibleMoves,startPosition, forward, board, color);

                attack(possibleMoves,startPosition,attackR,board,color);
                attack(possibleMoves,startPosition,attackL,board,color);

            }
        }



        return possibleMoves;
    }
}
