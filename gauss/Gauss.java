package gauss;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;
public class Gauss{
	
    //-----------------------------------------
	public static final double tol = 1e-8; //tolerance to consider something = 0
	public static  int numEq; //number of Equations
	public static  int numVar; //number of variables
    public static double[][] newNum;
	public static Scanner read;
	public static String answer;
	
	//-----------------------------------------
   
	public static void main(String[] args) {
    	read = new Scanner(System.in);
    	System.out.println("Please enter the number of equations: ");
    	numEq = read.nextInt();//number of equations
    	System.out.println("Please enter the number of variables: ");
    	numVar = read.nextInt();
        System.out.println("Please enter the coefficients of each variable of each equation: ");
        double[][] matrix = new double[numEq][numVar+1];//new matrix 
        double[] con = new double[numEq];//constants array
        for(int i=0; i<numEq; i++){
            for(int j=0; j<numVar; j++){
                matrix[i][j] = read.nextDouble(); //get the matrix
            }
            con[i] = read.nextDouble();//get the coefficients
        }
        test(con,matrix);//test to see if there's a solution or not + give solutions if they exist         
    }
    
  //-----------------------------------------
    public void swap(int first, int second){ //swap rows
        double[] temp = newNum[first];
        newNum[first] = newNum[second];
        newNum[second] = temp;
    }
    
  //-----------------------------------------
    public boolean hasSolution() { //check to see if there's a solution or not
    	if(base()!=null){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
  //-----------------------------------------
    public Gauss(double[][] matrix, double[] con,int Eq,int Var) {//compute GaussJordan on given system
    	numEq = Eq;
    	numVar = Var;
    	newNum = new double[numEq][numVar*2+1];
        for (int i=0; i<numEq; i++){
            for (int j=0; j<numVar; j++){
            	newNum[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < numEq; i++){
        	newNum[i][numVar+i] = 1.0;
        }
        for (int i = 0; i < numEq; i++){ 
        	newNum[i][2*numVar] = con[i];
        }
        eliminate();
       // assert check(con, matrix); //check on the runtime
    }
    
  //-----------------------------------------
    public void eliminate(){ //top method for GaussJordan 
    	for (int i=0; i<numEq; i++) {
            int maximum = i;
    		if(leftmost(newNum,i)!=-1){
            for (int y=i+1; y<numEq; y++){
                if (Math.abs(newNum[y][i]) > Math.abs(newNum[maximum][i])){
                    maximum=y;
                }
            }
            swap(i, maximum);
            if (Math.abs(newNum[i][i])<=tol){
                continue;
            }
            pivot(i,i);
            }
            }
    }
    
  //-----------------------------------------
    public void pivot(int a, int b) {   //pivoting method
        for (int i=0; i<numEq; i++) {
            double x = newNum[i][b]/newNum[a][b];
            for (int j=0; j<=2*numVar; j++){ 
                if (i!=a&&j!=b){
                	newNum[i][j]=newNum[i][j]-x*newNum[a][j];
                }
            }

        }
        for (int i=0; i<numEq; i++){
            if (i!=b){
            	newNum[i][b]=0.0;
            }
        }
        for (int j=0; j<=2*numVar; j++){
            if (j != b) newNum[a][j] /= newNum[a][b];
        }
        newNum[a][b]=1.0;
    }
    
  //-----------------------------------------
    public double[] base(){ // give an array with the constant results (may need variables part if infinitely many)
        double[] a=new double[numEq];
        for (int i=0; i<numEq; i++) {
            if (leftmost(newNum,i)!=-1){
                a[i] = newNum[i][2*numVar] / newNum[i][leftmost(newNum,i)];
            }
            else if(Math.abs(newNum[i][2*numVar])>tol){
                return null;
            }
        }
        return a;
    }
    
  //-----------------------------------------
    public static String test(double[] con,double[][] matrix) { //method to check for solutions + give them
        Gauss gauss = new Gauss(matrix, con,numEq,numVar);
        if (gauss.hasSolution()){ // checking for solution 
        	double[] a = gauss.base();
        	LinkedList<Integer> inf = new LinkedList<Integer>();
        	inf=isInfinite(con);
        	if(!inf.isEmpty()) { // checking for infinitely many solutions
        		Formatter fmt = new Formatter();
        		String s="";
        		String ss="";
        		//INFINETLY MANY
        		String solution;
        		//System.out.println("Infinitely many solutions");
        		solution = "Infinitely many solutions";
        		for(int i=0;i<inf.size();i++){
        			LinkedList <Integer> column = new LinkedList<Integer>(); //columns
        			LinkedList <Double> values = new LinkedList<Double>(); //coefficients
        			for(int j=0;j<numVar;j++){
        				if(newNum[inf.get(i)][j]!=0){
        					column.add(j+1);
        					values.add(newNum[inf.get(i)][j]); 
        				}
        			}
        			for(int z=1;z<column.size();z++){
        				for(int y=0;y<column.size()-1;y++){
        					s=s+" - %fx%d";
        					//coefficient x which column
        				}
        				
        				if(s!=""){
        					//args1 = "x%d = %f".concat(s);
        					//args1 = String.format(args1,column.get(0),a[inf.get(i)],values.get(z),column.get(z)).concat("\n");
        					//System.out.printf("x%d = %f".concat(s),column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        					ss="x%d = %f"+s;
        					fmt.format(ss+"\n",column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        					//System.out.println(fmt);
        				}
        				else{
        					//args2 = "x%d = %f - %fx%d".concat(s);
        					//args2 = String.format(args2, column.get(0),a[inf.get(i)],values.get(z),column.get(z)).concat("\n");
        					//System.out.printf("x%d = %f - %fx%d"+s,column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        					//args1.concat(args2);
        					ss="x%d = %f - %fx%d"+s;
        					fmt.format(ss+"\n",column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        					//System.out.println(fmt);
        				}
        				
        			}
        			String n;
        			for(int z=0;z<a.length;z++){
        				if(!inf.contains(z)){
        					for(int y=0;y<numVar;y++){
        					if(newNum[z][y]!=0){
        						//n = "x%d = %f\n";
        						//n = String.format(n, y+1,a[z]);
        						//System.out.printf("x%d = %f\n",y+1,a[z]);
        						fmt.format("x%d=%f\n", y+1,a[z]);
        						}
        					}
        				}
        			}
        			//System.out.println(fmt);

        			//arbitrary = new String[column.size()-1];
        			//System.out.println("--------------");
        			//answer = solution.concat("\n"+args1).concat(n);
        			//System.out.print(answer);
        			//System.out.println("----------------");
        			for(int j=1;j<column.size();j++){
        				//System.out.println(fmt.format(ss+"The arbitrary variable(s) is(are): x%d\n",column.get(j)));
        				//System.out.printf("The arbitrary variable(s) is(are): x%d\n",column.get(j));
        				//arbitrary[j-1]="The arbitrary variable(s) is(are): x%d\n";
        				//arbitrary[j-1]=String.format(arbitrary[j-1], column.get(j));
        				fmt.format("The Arbintrary variables(s) is(are): x%d\n",column.get(j));
        			}
        			System.out.println(fmt);
        			solution=solution+fmt;
        			return solution;
        		}
        		
        	}
        	else{
        		/*Formatter fmt = new Formatter();
				
				s = s + fmt.format("x%d = %f".concat(s),column.get(0),a[inf.get(i)],values.get(z),column.get(z));
				*/
        		//UNIQUE SOLUTION
        		Formatter fmt = new Formatter();
        		String sol="";
        		//String sol="Unique Solution\n";
	        	//System.out.println("Unique solution");
	            for (int i=0; i<numEq; i++) {
	            	sol=sol+fmt.format("x%d = %f \n", i+1,a[i]);
	            }
	            System.out.println("----->>>>>"+fmt);
	            
	            System.out.println("Unique Solution "+fmt);
	            return "Unique Solution \n"+fmt;
        	}
        }
    		Formatter fmt = new Formatter();
        	String n="";
        	n=n+fmt.format("No Solution %d",0); 
        	//NO SOLUTION
        	System.out.println(n);
        	return n;
        	// the matrix does not have solution
        
    }
    
  //-----------------------------------------
    public static LinkedList<Integer> isInfinite(double[] con){ // method that produces a list with the rows that have infinitely many solutions
    	int[] cnt = new int[numEq];
    	int counter;
    	for(int i=0;i<numEq;i++){
    		counter=0;
    		for(int j=0;j<numVar;j++){
    			if(newNum[i][j]!=0){
    				counter++;
    				cnt[i]=counter;
    			}
    		}
    	}
    	LinkedList<Integer> rows = new LinkedList<Integer>();
    	for(int i=0;i<cnt.length;i++){
    		if(cnt[i]>1){
    			rows.add(i);
    		}
    	}
    	return rows;
    }
    
  //-----------------------------------------
    public static int leftmost(double[][] matrix, int i){ // method that give the column of the leftmost variable in the equation that isn't 0 
    	for(int j=0; j<numVar; j++){
    		if(matrix[i][j]!=0){
    			return j;
    		}
    	}
    	return -1;
    }
  
}
