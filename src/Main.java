import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean isFinished = false;
        int [][] Matrix = InitialGraph();
        while (!isFinished) {
            Scanner myScanner = new Scanner(System.in);
            DisplayMenu();
            String selection = myScanner.next();
            switch (selection) {
                case "1": {
                    Matrix = CreateMatrix();
                    break;
                }
                case "2":
                {
                    OutDegrees(Matrix);
                    InDegrees(Matrix);
                    IntegerAdjacencyMatrix(Matrix);
                }
                    case "3": {
                        isFinished = true;
                    }
                    default:System.out.println("Enter valid input");
            }
        }
    }
    public static int[][] InitialGraph() //clean this up dear god
    {
        boolean isFinished = false;
        while (!isFinished) {
            Scanner myScanner = new Scanner(System.in);
            DisplayMenu();
            String selection = myScanner.next();
            switch (selection) {
                case "1":
                {
                    int [][]  Matrix = CreateMatrix();
                    return Matrix;
                }
                case "3":
                {
                    System.exit(0);
                }
                default:
                    System.out.println("Please enter graph data!");
            }
        }
        return null;
    }


    public static int[][] CreateMatrix()
    {
        int matrixSize = 15;//arbitrary number
        Scanner myScanner = new Scanner(System.in);
        while (matrixSize > 6)
        {
            System.out.print("\nEnter Matrix Size: ");
            matrixSize = myScanner.nextInt();
        }
        int[][] Matrix = new int[matrixSize][matrixSize];
        for (int i = 0; i <matrixSize; i++)
        {
            for (int j = 0; j < matrixSize; j++)
            {
                System.out.printf("Enter A1[" +i + "] [" + j + "]: ");
                Matrix[i][j] = myScanner.nextInt();
            }
        }
        PrintMatrix(Matrix);
        return Matrix;
    }

    public static int[][] Warshall(int G[][])
    {//CHPT 30 Graphs Reachability Warshall Algorithm slide

        int i, j, k;
        int size = G.length;
        int[][] Result = new int[size][size];
        for (i = 0; i < G.length; i++)
            for (j = 0; j < G.length; j++)
                Result[i][j] = G[i][j];
        for (k = 0; k < size; k++)
            for (i = 0; i < size; i++)
                for (j = 0; j < size; j++)
                {
                    Result[i][j] = Result[i][j] | Result[i][k] & Result[k][j];
                }
        PrintMatrix(Result);
        return Result;
    }
    public static void DisplayMenu()
    {
        System.out.print("\n------MAIN MENU------\n" +
                "1. Enter graph data\n" +
                "2. Print outputs\n" +
                "3. Exit program\n" +
                "Enter option number:");
    }
    public static int[][] MatrixMultiplication(int [][] Matrix1, int [][]Matrix2)
    {
        int size = Matrix1.length;
        int [][]result = new int [size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                result[i][j] = 0;
                for (int k = 0; k < size; k++)
                {
                    result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        }
        PrintMatrix(result);
        return result;
    }
    public static int[][] MatrixAddition(int [][] Matrix1, int [][]Matrix2)
    {
        int size = Matrix1.length;
        int [][]result = Matrix1;
        for (int i = 0; i < Matrix1.length; i++)
        {
            for (int j = 0; j < Matrix1[0].length; j++) {
                result[i][j] = Matrix1[i][j] + Matrix2[i][j];
            }
        }
        PrintMatrix(result);
        return result;
    }
    //Print out the input matrix.
    public static void PrintMatrix(int[][] Matrix)
    {
        for (int i = 0; i < Matrix.length; i++)
        {
            System.out.print("[");
            for (int j = 0; j < Matrix.length; j++) {
                System.out.print(Matrix[i][j]+ " ");
            }
            System.out.print("\b]\n");
        }
    }
    public static void OutDegrees(int[][] Matrix)
    {
        System.out.println("Out-degrees:");
        int size = Matrix.length;
        for (int i = 0; i < size; i++)
        {
            int sum = 0;
                for (int j = 0; j < size; j++)
                {
                    sum = sum + Matrix[i][j];
                }
            System.out.println("Node " + (i+1) +" out-degree is " + sum);
        }
    }
    public static void InDegrees(int[][] Matrix)
    {
        System.out.println("In-degrees:");
        int size = Matrix.length;
        for (int i = 0; i < size; i++)
        {
            int sum = 0;
            for (int j = 0; j < size; j++)
            {
                sum = sum + Matrix[j][i];
            }
            System.out.println("Node " + (i+1) +" in-degree is " + sum);
        }
    }
    public static void IntegerAdjacencyMatrix(int[][] MatrixA1)
    {
        int size = MatrixA1.length, iterator = 0;
        int[][] MatrixA2 = new int[size][size],
                MatrixA3 = new int[size][size],
                MatrixA4 = new int[size][size],
                MatrixA5 = new int[size][size],
                IntegerAdjacencyMatrix = new int[size][size];

        if (iterator < size) {
            MatrixA2 = MatrixMultiplication(MatrixA1, MatrixA1);
            iterator++;
            IntegerAdjacencyMatrix = MatrixAddition(MatrixA1,MatrixA2);
        }
        if (iterator < size) {
            MatrixA3 = MatrixMultiplication(MatrixA2, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA3);
            iterator++;
        }
        if (iterator < size) {
            MatrixA4 = MatrixMultiplication(MatrixA3, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA4);
            iterator++;
        }
        if (iterator < size) {
            MatrixA5 = MatrixMultiplication(MatrixA4, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA5);
            iterator++;
        }
        System.out.println("Integer Adjacency:");
        PrintMatrix(IntegerAdjacencyMatrix);
    }

}
