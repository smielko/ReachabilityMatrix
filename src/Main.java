import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        boolean isFinished = false;
                while (!isFinished) {
                    Scanner myScanner = new Scanner(System.in);
                    DisplayMenu();
                    String selection = myScanner.next();
                    switch (selection) {
                        case "1":
                        {
                            int Matrix[][] = CreateMatrix();
                            MatrixAddition(Matrix,Matrix);
                            Warshall(Matrix);
                            break;
                        }
                        case "2":
                        {
                            DisplayMenu();
                        }
                        case "3":
                        {
                            isFinished=true;
                        }
                        default:System.out.println("Enter valid input");
                    }
                }
    }
    public static int[][] CreateMatrix()
    {
        int matrixSize;
        Scanner myScanner = new Scanner(System.in);
        System.out.print("\nEnter Matrix Size: ");
        matrixSize = myScanner.nextInt();

        int[][] Matrix = new int[matrixSize][matrixSize];
        for (int i = 0; i <matrixSize; i++)
        {
            for (int j = 0; j < matrixSize; j++)
            {
                System.out.printf("Enter [" +i + "] [" + j + "]: ");
                Matrix[i][j] = myScanner.nextInt();
            }
        }
        PrintMatrix(Matrix);
        return Matrix;
    }

    public static void Warshall(int G[][])
    {//CHPT 30 Graphs Reachability Warshall Algorithm slide

        int i, j, k;
        int size = G.length;
        int[][] Result = new int[size][size];
        for (k = 0; k < size; k++)
            for (i = 0; i < size; i++)
                for (j = 0; j < size; j++)
                    if (G[i][k] + G[k][j] < G[i][j])
                        Result[i][j] = G[i][k] + G[k][j];
        PrintMatrix(Result);
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
                for (int k = 0; k < size; k++)
                {
                    result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        }

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
    //Compute and print out the graph reachability matrix.
}
