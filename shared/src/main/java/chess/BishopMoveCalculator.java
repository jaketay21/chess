package chess;

import java.util.*;

public class BishopMoveCalculator extends ChessMoveCalculator {
    public BishopMoveCalculator() {}
    // checks if position in within bounds
    private boolean positionValid(ChessPosition position){
        return position.getColumn() >= 1 && position.getColumn() <= 8 && position.getRow() >= 1 && position.getRow() <= 8;
    }

    private boolean empty(ChessBoard board, ChessPosition position){
        if(board.getPiece(position) != null) return false;
        return true;
    }

    private void addToList(ChessPosition pos, List<ChessMove> posMoves, ChessPosition start){
        if (positionValid(pos)){
            posMoves.add(new ChessMove(start, pos, null));
        }
    }

    // this function should calculate all possible moves and return an array of chessmoves
    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition) {
        List<ChessMove> possibleMoves = new ArrayList<>() ;
        //set booleans for each direction of possible movements as unblocked
        boolean unblockedUpR= true;
        boolean unblockedUpL = true;
        boolean unblockedDownL = true;
        boolean unblockedDownR = true;
        //cover whole board because bishop can move from one corner to the other
        for(int i = 1; i < 8; i++){
            /*
            checks if path is unblocked
            if unblocked update next possible move
            check if next pos is occupied
            if occupied update unblocked to false, stops calculating for that direction
            if unoccupied add position to list
             */
            if(unblockedUpL){
                ChessPosition upLeft = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
                if (positionValid(upLeft)){
                    if(!empty(board,upLeft)){
                    ChessPiece blocker = board.getPiece(upLeft);
                    unblockedUpL = false;
                        if(color != blocker.getTeamColor()){
                            possibleMoves.add(new ChessMove(startPosition, upLeft, null));
                        }
                    }else {
                        possibleMoves.add(new ChessMove(startPosition, upLeft, null));
                    }
                }
            }

            if(unblockedUpR){
                ChessPosition upRight = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() + i);
                if (positionValid(upRight)) {
                    if(!empty(board,upRight)){
                        ChessPiece blocker = board.getPiece(upRight);
                        unblockedUpR = false;
                        if(color != blocker.getTeamColor()){
                            possibleMoves.add(new ChessMove(startPosition, upRight, null));
                        }
                    }else {
                        possibleMoves.add(new ChessMove(startPosition, upRight, null));
                    }
                }
            }

            if(unblockedDownR){
                ChessPosition downRight = new ChessPosition (startPosition.getRow() -i, startPosition.getColumn() + i);
                if(positionValid(downRight)){
                    if(!empty(board,downRight)){
                        ChessPiece blocker = board.getPiece(downRight);
                        unblockedDownR = false;
                        if(color != blocker.getTeamColor()){
                            possibleMoves.add(new ChessMove(startPosition, downRight, null));
                        }
                    }else {
                        possibleMoves.add(new ChessMove(startPosition, downRight, null));
                    }
                }
            }

            if(unblockedDownL){
                ChessPosition downLeft = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() -i);
                if(positionValid(downLeft)){
                    if(!empty(board,downLeft)){
                        ChessPiece blocker = board.getPiece(downLeft);
                        unblockedDownL = false;
                        if(color != blocker.getTeamColor()){
                            possibleMoves.add(new ChessMove(startPosition, downLeft, null));
                        }
                    }else {
                        possibleMoves.add(new ChessMove(startPosition, downLeft, null));
                    }
                }
            }

        }


        return possibleMoves;

    }


}
