package sample;

import java.util.Arrays;
import java.util.Random;

public class MapGenerator {

    public static int[] randMinesGen(int cells, int numOfMines) {
        Random random = new Random();
        int[] mines = new int[numOfMines];
        for (int i = 0; i < numOfMines; i++) {
            mines[i] = random.nextInt(cells);
            for (int j = 0; j < i; j++) {
                if (mines[i] == mines[j]) {
                    i--;
                    break;
                }
            }
        }
        Arrays.sort(mines);
        System.out.println(Arrays.toString(mines));
        return mines;
    }

    public static int[][] MapGen(int numOfRows, int numOfColumns, int[] mines) {
        int[][] map = new int[numOfRows][numOfColumns];
        int mineCount = 0;
        int count = 0;
        //find mines and label neighbours
        generateMap:
        {
            for (int i = 0; i < numOfRows; i++) {
                for (int j = 0; j < numOfColumns; j++) {
                    if (mines[mineCount] == count) {
                        map[i][j] = 9;
                        mineCount++;
                        labelNeighbours(numOfRows, numOfColumns, map, i, j);
                        if (mineCount == mines.length) {
                            break generateMap;
                        }
                    }
                    count++;
                }
            }
        }
        for (int[] line : map) {
            System.out.println(Arrays.toString(line));
        }
        return map;
    }

    private static void labelNeighbours(int numOfRows, int numOfColumns, int[][] map, int i, int j) {
        for (int y = -1; y < 2; y++) {//Position in column
            for (int x = -1; x < 2; x++) {//Position in row
                if (i + y >= 0 && i + y < numOfRows && j + x >= 0 && j + x < numOfColumns && map[i + y][j + x] != 9) {
                    map[i + y][j + x]++;

                }
            }
        }
    }

}