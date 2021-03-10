package com.company;

import java.util.Scanner;

public class Main {
    public static int[][] MK_matrix() {
        int[][] matrix = new int[3][3];
        int d = 1;
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                matrix[i][l] = d;
                d++;
            }
        }
        return matrix;
    }

    public static void Show(int[][] matrix) {
        System.out.println("  4 5 6");
        for (int i = 0; i < 3; i++) {
            System.out.printf("%d %d|%d|%d\n", i + 1, matrix[i][0], matrix[i][1], matrix[i][2]);
        }
    }

    public static int[][] MovX(int[][] matrix, int row, int direct) {
        row -= 1;
        if (direct >= 0) {
            int buffer = matrix[row][2];
            matrix[row][2] = matrix[row][1];
            matrix[row][1] = matrix[row][0];
            matrix[row][0] = buffer;
        } else {
            int buffer = matrix[row][0];
            matrix[row][0] = matrix[row][1];
            matrix[row][1] = matrix[row][2];
            matrix[row][2] = buffer;
        }
        return matrix;
    }

    public static int[][] MovY(int[][] matrix, int column, int direct) {
        column -= 1;
        if (direct >= 0) {
            int buffer = matrix[2][column];
            matrix[2][column] = matrix[1][column];
            matrix[1][column] = matrix[0][column];
            matrix[0][column] = buffer;
        } else {
            int buffer = matrix[0][column];
            matrix[0][column] = matrix[1][column];
            matrix[1][column] = matrix[2][column];
            matrix[2][column] = buffer;
        }
        return matrix;
    }

    static public int Start() {
        String banner = """         
                               ,,                    ,,          
                `7MN.   `7MF'  db                  `7MM          
                  MMN.    M                          MM          
                  M YMb   M  `7MM  M\"\"\"MMV M\"\"\"MMV   MM   .gP\"Ya
                  M  `MN. M    MM  '  AMV  '  AMV    MM  ,M'   Yb
                  M   `MM.M    MM    AMV     AMV     MM  8M\"\"\"\"\"\"
                  M     YMM    MM   AMV  ,  AMV  ,   MM  YM.    ,
                .JML.    YM  .JMML.AMMmmmM AMMmmmM .JMML. `Mbmmd'
                """;
        System.out.println(banner);
        Scanner in = new Scanner(System.in);
        System.out.println("Укажите уровень сложности\n(1 - Легкий, 2 - Средний, 3 - Сложный): ");
        int level;
        String input_level;
        while (true) {
            input_level = in.next();

            if ((input_level.equals("1")) || (input_level.equals("2")) || (input_level.equals("3"))) {
                level = Integer.parseInt(input_level);
                break;
            } else {
                System.out.println("(1 - Легкий, 2 - Средний, 3 - Сложный): ");
            }
        }
        return level;
    }

    public static int[][] Generation(int[][] matrix, int level) {
        level = (level + 1) * 2;
        while (level > 0) {
            int TF = Math.random() < 0.5 ? 0 : 1;
            int PM = Math.random() < 0.5 ? 0 : 1;
            int CM = Math.random() < 0.66 ? 3 : Math.random() < 0.33 ? 2 : 1;
            if (TF == 0) {
                MovX(matrix, CM, PM);
            } else {
                MovY(matrix, CM, PM);
            }
            level -= 1;
        }
        return matrix;
    }

    public static void Game(int level){
        int[][] answer = MK_matrix();
        int[][] matrix = Generation(MK_matrix(), level);
        Scanner in = new Scanner(System.in);
        long start_time = System.nanoTime();
        while (!EqualsDeepArrays(matrix, answer)){
            Cls();
            Show(matrix);
            System.out.println("Укажите номер поворота: ");
            String rotate_input = in.next();
            int PM;
            int RT = Integer.parseInt(rotate_input);
            while (true){
                if (RT < 0){
                    PM = 0;
                    RT = -RT;
                }
                else{
                    PM = 1;
                }
                if ((RT <= 2 * 3) && (RT > 0)){
                    break;
                }
                else{
                    System.out.println("Укажите корректный номер!: ");
                    rotate_input = in.next();
                    RT = Integer.parseInt(rotate_input);
                }
            }
            if (RT > 3){
                RT = RT - 3;
                MovY(matrix, RT, PM);
            }
            else{
                MovX(matrix, RT, PM);
            }
        }
        long end_time = System.nanoTime();
        Cls();
        Show(matrix);
        System.out.println("Поздравляем! Вы справились с головоломкой!");
        System.out.printf("Потраченное время: %d секунд", ((end_time - start_time)/1000000000));
    }

    public static boolean EqualsDeepArrays(int[][] matrix1, int[][] matrix2){
        for(int l = 0; l < 3; l++){
            for(int j = 0; j < 3; j++){
                if (matrix1[l][j] != matrix2[l][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void Cls(){
        for (int l = 0; l < 100; l++){
            System.out.println();
        }
        //in Java there is no normal cleaning of the console :(
    }

    public static void main(String[] args) {
        int level = Start();
        Game(level);
    }
}
