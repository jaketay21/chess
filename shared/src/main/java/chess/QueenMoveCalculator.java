package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMoveCalculator extends ChessMoveCalculator {

    public QueenMoveCalculator() {

    }

    private boolean addToList(List<ChessMove> possibleMoves, ChessPosition start, ChessPosition next, ChessBoard board, ChessGame.TeamColor color){
        if (positionValid(next)) {
            if (!empty(board, next)) {
                ChessPiece blocker = board.getPiece(next);
                if (blocker.getTeamColor() != color) {
                    possibleMoves.add(new ChessMove(start, next, null));
                }
                return false;
            } else {
                possibleMoves.add(new ChessMove(start, next, null));
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ChessMove> CalculateMove(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType promotionType, ChessPosition startPosition) {
        List<ChessMove> possibleMoves = new ArrayList<>();
        boolean unblockedU = true;
        boolean unblockedUL = true;
        boolean unblockedUR = true;
        boolean unblockedL = true;
        boolean unblockedR = true;
        boolean unblockedDL = true;
        boolean unblockedDR = true;
        boolean unblockedD = true;

        for (int i = 1; i <= 8; i++) {
            ChessPosition up = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            ChessPosition upL = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            ChessPosition Left = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            ChessPosition downL = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            ChessPosition down = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            ChessPosition downR = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            ChessPosition Right = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            ChessPosition upR = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);

            if (unblockedU) {
                unblockedU = addToList(possibleMoves, startPosition, up, board, color);
            }
            if (unblockedUL) {
                unblockedUL = addToList(possibleMoves, startPosition, upL, board, color);
            }
            if (unblockedL) {
                unblockedL = addToList(possibleMoves, startPosition, Left, board, color);
            }
            if (unblockedDL) {
                unblockedDL = addToList(possibleMoves, startPosition, downL, board, color);
            }
            if (unblockedD) {
                unblockedD = addToList(possibleMoves, startPosition, down, board, color);
            }
            if (unblockedDR) {
                unblockedDR = addToList(possibleMoves, startPosition, downR, board, color);
            }
            if (unblockedR) {
                unblockedR = addToList(possibleMoves, startPosition, Right, board, color);
            }
            if (unblockedUR) {
                unblockedUR = addToList(possibleMoves, startPosition, upR, board, color);
            }
        }

        return possibleMoves;
    }
}