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
                    System.out.println("Input Matrix:");
                    PrintMatrix(Matrix);
                    IntegerAdjacencyMatrix(Matrix);
                    OutDegrees(Matrix);
                    InDegrees(Matrix);
                    SelfLoops(Matrix);

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
                case "1" -> {return CreateMatrix();}
                case "3" -> {System.exit(0);}
                default -> System.out.println("Please enter graph data!");
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

    public static int[][] Warshall(int[][] G)
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
        System.out.print("""

                ------MAIN MENU------
                1. Enter graph data
                2. Print outputs
                3. Exit program
                Enter option number:""");
    }
    public static int[][] MatrixMultiplication(int [][] Matrix1, int [][]Matrix2)
    {
        int size = Matrix1.length;
        int [][]result = new int [size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                result[i][j] = 0;
                for (int k = 0; k < size; k++)
                {
                    result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        return result;
    }
    public static int[][] MatrixAddition(int [][] Matrix1, int [][]Matrix2)
    {
        int [][] MatrixSum =new int[Matrix1.length][Matrix1.length];
        for (int i = 0; i < Matrix1.length; i++)
            for (int j = 0; j < Matrix1[0].length; j++) {
                MatrixSum[i][j] = Matrix1[i][j] + Matrix2[i][j];
            }
        return MatrixSum;
    }
    public static void PrintMatrix(int[][] Matrix)
    {
        for (int[] matrix : Matrix) {
            System.out.print("[");
            for (int j = 0; j < Matrix.length; j++)
                System.out.print(matrix[j] + " ");
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
                    sum = sum + Matrix[i][j];
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
            for (int[] matrix : Matrix) {
                sum = sum + matrix[i];
            }
            System.out.println("Node " + (i+1) +" in-degree is " + sum);
        }
    }
    public static void IntegerAdjacencyMatrix(int[][] MatrixA1)
    {
        int size = MatrixA1.length; int iterator = 1;
        int[][] MatrixA2 = new int[size][size],
                MatrixA3 = new int[size][size],
                MatrixA4 = new int[size][size],
                IntegerAdjacencyMatrix = MatrixA1;

        if (iterator < size) {
            MatrixA2 = MatrixMultiplication(MatrixA1, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix, MatrixA2);
        }
        ++iterator;
        if (iterator < size) {
            MatrixA3 = MatrixMultiplication(MatrixA2, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix, MatrixA3);
        }
        ++iterator;
        if (iterator < size) {
            MatrixA4 = MatrixMultiplication(MatrixA3, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA4);
        }
        ++iterator;
        if  (iterator < size) {
            int[][] MatrixA5 = MatrixMultiplication(MatrixA4, MatrixA1);
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA5);
        }
        System.out.println("Reachability Matrix:");
        PrintMatrix(IntegerAdjacencyMatrix);
    }
    //Sum of the major diagonal in A1 gives total number of self-loops.
    public static void SelfLoops(int[][] Matrix)
    {
        System.out.println();
        int size = Matrix.length;
        int sum= size;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (j == i && Matrix[i][j] == 0)
                    sum--;
            }
        System.out.println("Total number of self-loops:" + sum);
    }
    public static void ComputePaths(int[][] Matrix)
    {

    }
}
