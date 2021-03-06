
import java.util.Scanner;
public class gaussJordan {
	public static void main(String[] args){
		Scanner read = new Scanner(System.in);
		System.out.println("Please enter the number of variables");
		int var = read.nextInt();
		System.out.println("Please enter the number of equations");
		int numEq = read.nextInt();
		int column = var+1;
		int row = numEq;
		double[][] matrix = new double[row][column];
		//Getting the matrix in
		System.out.printf("You have a matrix with %d rows and %d columns\n",row,column);
		System.out.println("Please enter coefficients and constant of all equations");
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				matrix[i][j] = read.nextDouble();
			}
		}
		for(int i=0;i<row;i++){
			if(matrix[i][0]==0){
				matrix = substitute(i,0,matrix,column,row);
			}
			pivot (i,0,matrix,column,row);
		}
		
	}
	
	public static int pivot(int i,int j, double[][] matrix,int column, int row){
		matrix=multinv(i,j,column,matrix);
		//clean
		return 0;
	}
	
	public static double[][] clean(int i, int j, double[][] matrix, int column, int row){
		
		return matrix;
	}
	
	public static double[][] add(int i,int j, int row, int column, double[][] matrix,int newRow){
		double[][] newMatrix = new double[row][column];
		double cof = matrix[newRow][j];
		for(int a=0;a<column;a++){
			newMatrix[i][a]=matrix[i][a]*(-cof);
			matrix[newRow][a]=matrix[newRow][a]+newMatrix[i][a];
		}
		return matrix;
	}
	
	public static int find(int i,int j,double[][] matrix,int row){
		for(int a=0;a<row;a++){
			if(matrix[a][j]!=0){
				//if{}
				return a; //We found the one thats not zero and are going to swap it
			}
		}
		return -1; //all of the elements in row+1 and the same column are 0 we do not need to swap it.
	}
	
	public static double[][] multinv(int i,int j, int column,double[][] matrix){
		double cof = matrix[i][j];//coefficient
		double inv = 1/cof;//inverse
		for(int a=0;a<column;a++){
			matrix[i][a]=matrix[i][a]*inv;
		}
		return matrix;
	}
	
	public static double[][] substitute(int i, int j, double[][] matrix,int column,int row){
		int result = search(i,j,matrix,row);
		matrix = swap(result,matrix,i,j,column,row);
		return matrix;
	}
	
	public static int search(int i,int j,double[][] matrix,int row){
		for(int a=i+1;a<row;a++){
			if(matrix[a][j]!=0){
				return a; //We found the one thats not zero and are going to swap it
			}
		}
		return -1; //all of the elements in row+1 and the same column are 0 we do not need to swap it.
	}
	
	public static double[][] swap(int result, double[][] matrix, int i, int j, int column, int row){
		/*
		 * int temp = a;
		 * a=b;
		 * b=temp;
		 * */
		//int [][] newMatrix = new int[row][column];
		double[] temprow = new double[column];
		//int[] temprow = matrix[i][j]
		for(int a=0;a<column;a++){
			temprow[a]= matrix[i][a];
			matrix[i][a]=matrix[result][a];
			matrix[result][a]=temprow[a];
		}
		return matrix;
	} 
	
}
