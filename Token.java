import java.util.ArrayList;
import java.util.List;

public class Token {
    String name = "A";
    int [][] shape;
    List<int[][]> rotations;
    int GRID_SIZE = 7;

    public Token(String name, int [][] shape){
        this.name = name;
        this.shape = shape;

        // for future optimization, test each token in every orientation individually
        rotations = new ArrayList<>();
        rotations.add(getShape());
        rotations.add(rotate(90));
        rotations.add(rotate(180));
        rotations.add(rotate(270));
    }

    public String getName(){
        return name;
    }

    public int[][] getShape(){
        return shape;
    }

    public int[][] rotate(int rotation) {
        int[][] rotated = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            int x = shape[i][0];
            int y = shape[i][1];
            switch (rotation) {
                case 90:
                    rotated[i][0] = -y;
                    rotated[i][1] = x;
                    break;
                case 180:
                    rotated[i][0] = -x;
                    rotated[i][1] = -y;
                    break;
                case 270:
                    rotated[i][0] = y;
                    rotated[i][1] = -x;
                    break;
                default: // 0 degrees
                    rotated[i][0] = x;
                    rotated[i][1] = y;
                    break;
            }
        }
        return rotated;
    }

    @Override
    public String toString() {
        return name;
    }
}
