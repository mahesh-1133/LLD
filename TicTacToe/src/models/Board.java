package models;

public class Board {
    private  int size;
    private  Cell[][] board;

    public Board(int size) {
        this.size = size;
        initializeBoard(size);
    }

    private void initializeBoard(int size) {
        board = new Cell[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void print() {
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (cell.isEmpty()) {
                    System.out.print("|  |");
                } else {
                    System.out.print("| " + cell.getPlayer().getSymbol() + " |");
                }
            }
            System.out.println();
        }
    }
}
