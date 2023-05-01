
import java.util.Scanner;
// Name:Sebastian Mielko
// Class:CS 3305/Section03
// Term:Spring 2023
// Instructor:Prof. Majeed
// Assignment:8

public class ReachabilityMatrix {
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
                    InDegrees(Matrix);
                    OutDegrees(Matrix);
                    ComputePathsandCycles(Matrix);
                    break;
                }
                    case "3": {
                        isFinished = true;
                    }
                    default:System.out.println("Enter valid input");
            }
        }
    }
    public static int[][] InitialGraph() //Forces first graph creation
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
        while (matrixSize > 6) //Requirements: Max graph size of 5
        {
            System.out.print("\nEnter Matrix Size: ");
            matrixSize = myScanner.nextInt();
        }
        int[][] Matrix = new int[matrixSize][matrixSize];
        for (int i = 0; i <matrixSize; i++) //data insertion and formatting
            for (int j = 0; j < matrixSize; j++)
            {
                System.out.printf("Enter A1[" +i + "] [" + j + "]: ");
                Matrix[i][j] = myScanner.nextInt();
            }
        return Matrix;
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
                for (int k = 0; k < size; k++) //math for multiplying 2 matrices together
                {
                    result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        return result;
    }
    public static int[][] MatrixAddition(int [][] Matrix1, int [][]Matrix2)
    {
        int [][] MatrixSum =new int[Matrix1.length][Matrix1.length];
        for (int i = 0; i < Matrix1.length; i++)//math for adding 2 matrices together
            for (int j = 0; j < Matrix1[0].length; j++) {
                MatrixSum[i][j] = Matrix1[i][j] + Matrix2[i][j];
            }
        return MatrixSum;
    }
    public static void PrintMatrix(int[][] Matrix) //prints adjacency matrix
    {
        for (int[] matrix : Matrix) {
            System.out.print("[");
            for (int j = 0; j < Matrix.length; j++)
                System.out.print(matrix[j] + " ");
            System.out.print("\b]\n");
        }
    }
    public static void OutDegrees(int[][] Matrix) //computes out degrees of each node
    {
        System.out.println("Out-degrees:");
        int size = Matrix.length;
        for (int i = 0; i < size; i++)
        {
            int sum = 0;
                for (int j = 0; j < size; j++)//sums all columns of a given row
                    sum = sum + Matrix[i][j];
            System.out.println("Node " + (i+1) +" out-degree is " + sum);
        }
    }
    public static void InDegrees(int[][] Matrix) //computes in degrees of each node
    {
        System.out.println("In-degrees:");
        int size = Matrix.length;
        for (int i = 0; i < size; i++)
        {
            int sum = 0;
            for (int[] matrix : Matrix) { //sums all rows of a given column
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
                IntegerAdjacencyMatrix = MatrixA1;//Sum
//false condition = the correct amount of computations has been performed for the given size of the graph
        if (iterator < size) {
            MatrixA2 = MatrixMultiplication(MatrixA1, MatrixA1); //A^2 creation
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix, MatrixA2);
        }
        ++iterator;
        if (iterator < size) {
            MatrixA3 = MatrixMultiplication(MatrixA2, MatrixA1); //A^3 creation
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix, MatrixA3);
        }
        ++iterator;
        if (iterator < size) {
            MatrixA4 = MatrixMultiplication(MatrixA3, MatrixA1); //A^4 creation
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA4);
        }
        ++iterator;
        if  (iterator < size) {
            int[][] MatrixA5 = MatrixMultiplication(MatrixA4, MatrixA1); //A^5 creation
            IntegerAdjacencyMatrix = MatrixAddition(IntegerAdjacencyMatrix,MatrixA5);
        }
        System.out.println("Reachability Matrix:");
        PrintMatrix(IntegerAdjacencyMatrix);
    }

    public static int Cycles(int[][] Matrix)
    {    //Sum of the major diagonal in A^n gives total number of cycles
        int size = Matrix.length;
        int sum= size;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (j == i && Matrix[i][j] == 0) //There is a disconnect in the graph
                    sum--;
            }
        return sum;
    }
    public static void ComputePathsandCycles(int[][] MatrixA1) //dear god forgive me
    {
        int size = MatrixA1.length; int iterator = 1; int sumOfPaths = 0; int sumOfCycles = 0;//total amount of cycles
        int pathsLengthOne = 0;
        int cyclesOfLength = Cycles(MatrixA1);;//amount of cycles of length 1 -> n
        int pathsOfLength = 1;//amount of paths of length 1 -> n
        int[][] MatrixA2 = new int[size][size],
                MatrixA3 = new int[size][size],
                MatrixA4 = new int[size][size],
                PathMatrix = MatrixA1;

        //Size 1:
        sumOfCycles+=cyclesOfLength;
        //Size 2->n:
        if (iterator < size) {//copied from the adjacency matrix
            MatrixA2 = MatrixMultiplication(MatrixA1, MatrixA1);
            PathMatrix = MatrixAddition(PathMatrix,MatrixA2);
                cyclesOfLength *= Cycles(MatrixA2);
                sumOfCycles+=cyclesOfLength;
                if(size==2) pathsOfLength = TwoDArraySum(MatrixA2);
        }//the rest of these are direct copies, just with different matrices
        ++iterator;
        if (iterator < size) {
            MatrixA3 = MatrixMultiplication(MatrixA2, MatrixA1);
            PathMatrix = MatrixAddition(PathMatrix,MatrixA3);
            cyclesOfLength *= Cycles(MatrixA3);
            sumOfCycles+=cyclesOfLength;
                if(size==3)pathsOfLength = TwoDArraySum(MatrixA3);
        }
        ++iterator;
        if (iterator < size) {
            MatrixA4 = MatrixMultiplication(MatrixA3, MatrixA1);
            PathMatrix = MatrixAddition(PathMatrix,MatrixA4);
            cyclesOfLength *= Cycles(MatrixA4);
            sumOfCycles+=cyclesOfLength;
                if(size==4)pathsOfLength = TwoDArraySum(MatrixA4);
        }
        ++iterator;
        if  (iterator < size) {
            int[][] MatrixA5 = MatrixMultiplication(MatrixA4, MatrixA1);
            PathMatrix = MatrixAddition(PathMatrix,MatrixA5);
            cyclesOfLength *= Cycles(MatrixA5);
            sumOfCycles+=cyclesOfLength;
                if(size==5) pathsOfLength  = TwoDArraySum(MatrixA5);
        }
        //Getting the results:
         sumOfPaths =TwoDArraySum(PathMatrix);
         pathsLengthOne =TwoDArraySum(MatrixA1);


        //Printing out the results:
        System.out.println("Total number of self loops " + Cycles(MatrixA1));
        System.out.println("Total number of cycles of length " + size + " edges: " +cyclesOfLength);
        System.out.println("Total number of paths of length 1 edge: " +  pathsLengthOne);
        System.out.println("Total number of paths of length " + size + " edges: " +pathsOfLength);
        System.out.println("Total number of paths of length 1 to " + size + " edges: " + sumOfPaths);
        System.out.println("Total number of cycles of length 1 to " + size + " edges: " + sumOfCycles);
    }

    public static int TwoDArraySum(int [][] Array)//helper
    {
        int sum = 0;
        int size = Array.length;
        for (int[] ints : Array)
            for (int j = 0; j < size; j++)
                sum += ints[j];
        return sum;
    }
}
