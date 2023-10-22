public class Board {
    private String[][] field_array;

    public Board(int gamsize){
        createBoard(gamsize);
    }

    private void createBoard(int gamesize) {
        int n = 0;
        field_array = new String[gamesize][gamesize];
        for (int i = 0; i< field_array.length; i++){
            for (int j = 0; j < field_array[0].length; j++) {
                field_array[i][j] = Integer.toString(n);
                n++;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i< field_array.length; i++){
            for (int j = 0; j < field_array[0].length; j++) {
                if(j == field_array[j].length-1){
                    System.out.print("|" + field_array[i][j] + "|");
                }else {
                    System.out.print("|" + field_array[i][j]);
                }
            }
            System.out.println();
            if(i< field_array.length-1){
                System.out.println("-------");
            }
        }
    }

    public String[][] getField_array() {
        return field_array;
    }

    public void setField_array(String[][] field_array) {
        this.field_array = field_array;
    }
}
