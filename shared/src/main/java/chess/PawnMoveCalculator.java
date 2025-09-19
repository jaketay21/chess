package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMoveCalculator extends ChessMoveCalculator{
    public PawnMoveCalculator() {}

    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition){
        List<ChessMove> possibleMoves = new ArrayList<>();



        return possibleMoves;
    }
}
