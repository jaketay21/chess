package chess;

import java.util.*;

public class BishopMoveCalculator extends ChessMoveCalculator {
    public BishopMoveCalculator() {}

    private boolean positionValid(ChessPosition position){
        return position.getColumn() >= 1 && position.getColumn() <= 8 && position.getRow() >= 1 && position.getRow() <= 8;
    }
    // this function should calculate all possible moves and return a set of chessmoves
    @Override
    public Collection<ChessMove> CalculateMove(ChessPiece.PieceType promotionType, ChessPosition startPosition) {
        List<ChessMove> possibleMoves = new ArrayList<>() ;
        //all directions start at the current position

        //cover whole board because bishop can move from one corner to the other
        for(int i = 1; i < 8; i++){
            ChessPosition upLeft = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            ChessPosition upRight = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() + i);
            ChessPosition downRight = new ChessPosition (startPosition.getRow() -i, startPosition.getColumn() + i);
            ChessPosition downLeft = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() -i);

            //check if move is in valid position for all directions for current iteration
            if (positionValid(upLeft)){
                possibleMoves.add(new ChessMove(startPosition, upLeft, null));
            }
            if (positionValid(upRight)) {
                possibleMoves.add(new ChessMove(startPosition, upRight, null));
            }
            if(positionValid(downLeft)){
                possibleMoves.add(new ChessMove(startPosition, downLeft, null));
            }
            if(positionValid(downRight)){
                possibleMoves.add(new ChessMove(startPosition, downRight, null));
            }
        }

        return possibleMoves;

    }


}
