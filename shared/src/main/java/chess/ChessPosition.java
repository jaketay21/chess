package chess;


import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition implements Comparable<ChessPosition> {
    private  int row;
    private  int col;

    public ChessPosition(int row, int col) {

        this.row = row;
        this.col = col;

    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    public void setRow(int x){
        row = x;
    }

    public void setCol(int x){
        col = x;
    }
    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", row, col);
    }

    /*
    this creates a comparison among chess positions
     */
    @Override
    public int compareTo(ChessPosition o) {
        int rowCompare = Integer.compare(this.row, o.row);
        if(rowCompare != 0) return rowCompare;
        return Integer.compare(this.col, o.col);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
