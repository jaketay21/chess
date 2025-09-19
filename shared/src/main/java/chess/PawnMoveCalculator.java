package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.WHITE;

public class PawnMoveCalculator extends ChessMoveCalculator{
    private List<ChessMove> possibleMoves;
    private boolean unblockedF = true;
    public PawnMoveCalculator() {
        possibleMoves = new ArrayList<>();
    }

    private boolean addToList(ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(next)){
            if(!empty(board,next)){
                return false;
            }else{
                possibleMoves.add(new ChessMove(start, next, null));
                return true;
            }
        }
        return false;
    }

    public void attack(ChessPosition start, ChessPosition nxt, ChessBoard board, ChessGame.TeamColor color){
        if(positionValid(nxt)){
            if(!empty(board, nxt)){
                ChessPiece blocker = board.getPiece(nxt);
                if(blocker.getTeamColor() != color){
                    possibleMoves.add(new ChessMove(start,nxt,null));
                }
            }
        }
    }

    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition){
        if(color ==  WHITE){
            ChessPosition attackL = new ChessPosition(startPosition.getRow() + 1,startPosition.getColumn() - 1);
            ChessPosition attackR = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);

            if(startPosition.getRow() == 2){
                for(int i = 1; i < 3; i ++){
                    ChessPosition Forward = new ChessPosition(startPosition.getRow()+ i, startPosition.getColumn());
                    if(unblockedF){
                        unblockedF = addToList(startPosition, Forward, board,color);
                    }
                }
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);

            }else if(startPosition.getRow() == 7){
                ChessPosition Forward = new ChessPosition(startPosition.getRow()+ 1, startPosition.getColumn());
                unblockedF = addToList(startPosition, Forward, board,color);
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);


            }else{
                ChessPosition Forward = new ChessPosition(startPosition.getRow()+ 1, startPosition.getColumn());
                unblockedF = addToList(startPosition, Forward, board,color);
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);
            }
        }else{
            ChessPosition attackL = new ChessPosition(startPosition.getRow() - 1,startPosition.getColumn() - 1);
            ChessPosition attackR = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);

            if(startPosition.getRow() == 7){
                for(int i = 1; i < 3; i ++){
                    ChessPosition Forward = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
                    if(unblockedF){
                        unblockedF = addToList(startPosition, Forward, board,color);
                    }
                }
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);

            }else if(startPosition.getRow() == 2){
                ChessPosition Forward = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                unblockedF = addToList(startPosition, Forward, board,color);
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);


            }else{
                ChessPosition Forward = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                unblockedF = addToList(startPosition, Forward, board,color);
                attack(startPosition,attackR,board,color);
                attack(startPosition,attackL,board,color);

            }
        }



        return possibleMoves;
    }
}
