import java.util.LinkedList;
import java.util.Scanner;
public class Gauss{
	public static final double EPSILON = 1e-7;
    public final int num;
    public static double[][] newNum;
	public static Scanner read;
	
	//-----------------------------------------
    public static void main(String[] args) {
    	read = new Scanner(System.in);
    	System.out.println("Please enter the number of equations: ");
    	int numEq = read.nextInt();//number of equations
    	System.out.println("Please enter the number of variables: ");
    	int numVar = read.nextInt();
        System.out.println("Please enter the coefficients of each variable of each equation: ");
        double[][] matrix = new double[numEq][numVar+1];//new matrix 
        double[] con = new double[numEq];//constants array
        for(int i=0; i<numEq; i++){
            for(int j=0; j<numVar; j++){
                matrix[i][j] = read.nextDouble(); //get the matrix
            }
            con[i] = read.nextDouble();//get the coefficients
        }
        test(con,matrix,numVar,numEq);//test for feasibility and answers!          
    }
    
  //-----------------------------------------
    public void swap(int first, int second){
        double[] temp = newNum[first];
        newNum[first] = newNum[second];
        newNum[second] = temp;
    }
    
  //-----------------------------------------
    public boolean hasSolution() { 
    	if(base()!=null){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
  //-----------------------------------------
    public Gauss(double[][] matrix, double[] con) {
    	num = con.length;
    	newNum = new double[num][num*2+1];
        for (int i=0; i<num; i++){
            for (int j=0; j<num; j++){
            	newNum[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < num; i++){
        	newNum[i][num+i] = 1.0;
        }
        for (int i = 0; i < num; i++){ 
        	newNum[i][2*num] = con[i];
        }
        eliminate();
        assert check(con, matrix); //check on the runtime
    }
    
  //-----------------------------------------
    public void eliminate(){
    	for (int i=0; i<num; i++) {
            int maximum=i;
            for (int j=i+1; j<num; j++){
                if (Math.abs(newNum[j][i]) > Math.abs(newNum[maximum][i])){
                    maximum=j;
                }
            }
            swap(i, maximum);
            if (Math.abs(newNum[i][i])<=EPSILON){
                continue;
            }
            pivot(i,i);
        }
    }
    
  //-----------------------------------------
    public void pivot(int a, int b) {   
        for (int i=0; i<num; i++) {
            double x = newNum[i][b]/newNum[a][b];
            for (int j=0; j<=2*num; j++){ 
                if (i!=a&&j!=b){
                	newNum[i][j]=newNum[i][j]-x*newNum[a][j];
                }
            }

        }
        for (int i=0; i<num; i++){
            if (i!=b){
            	newNum[i][b]=0.0;
            }
        }
        for (int j=0; j<=2*num; j++){
            if (j != b) newNum[a][j] /= newNum[a][b];
        }
        newNum[a][b]=1.0;
    }
    
  //-----------------------------------------
    public double[] base(){
        double[] a=new double[num];
        for (int i=0; i<num; i++) {
            if (Math.abs(newNum[i][i])>EPSILON){
                a[i] = newNum[i][2*num] / newNum[i][i];
            }
            else if(Math.abs(newNum[i][2*num])>EPSILON){
                return null;
            }
        }
        return a;
    }
    
  //-----------------------------------------
    public double[] twoWay(){
        double[] a=new double[num];
        for (int i=0; i<num; i++){
            if ((Math.abs(newNum[i][2*num])>EPSILON)&&(Math.abs(newNum[i][i])<=EPSILON)){
                for (int j=0; j<num; j++){
                    a[j]=newNum[i][num+j];
                }
                return a;
            }
        }
        return null;
    }
    
  //-----------------------------------------
    public static void test(double[] con,double[][] matrix,int numVar,int numEq) {
        Gauss gauss = new Gauss(matrix, con);
        if (gauss.hasSolution()){ // is it feasible ?? 
        	double[] a = gauss.base();
            int n = a.length;
            String s="";
        	LinkedList<Integer> inf = new LinkedList<Integer>();
        	inf=isInfinite(con,numVar,numEq);
        	if(!inf.isEmpty()){
        		System.out.println("infinite solution");
        		for(int i=0;i<inf.size();i++){
        			LinkedList <Integer> column = new LinkedList<Integer>();
        			LinkedList <Double> values = new LinkedList<Double>();
        			for(int j=0;j<numVar;j++){
        				if(newNum[inf.get(i)][j]!=0){
        					column.add(j+1);
        					values.add(newNum[inf.get(i)][j]);
        				}
        			}
        			for(int z=1;z<column.size();z++){
        				for(int y=0;y<column.size()-1;y++){
        					s=s+" - %fx%d";
        				}
        				s=s+"\n";
        				if(s!=""){
        					System.out.printf("x%d = %f".concat(s),column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        				}
        				else{
        					System.out.printf("x%d = %f - %fx%d"+s,column.get(0),a[inf.get(i)],values.get(z),column.get(z));
        				}
        				
        			}
        			
        			System.out.println("Arbitrary values is/are: ");
        			for(int j=1;j<column.size();j++){
        				System.out.printf("x%d\n",column.get(j));
        			}
        			for(int z=0;z<a.length;z++){
        				if(!inf.contains(z)){
        					for(int y=0;y<numVar;y++){
        					if(newNum[z][y]!=0){
        						System.out.printf("x%d = %f\n",y+1,a[z]);
        						}
        					}
        				}
        			}
        		}
        		
        	}
        	else{
	        	System.out.println("Solution to the matrix is: ");
	            for (int i=0; i<n; i++) {
	            	System.out.println(a[i]); //print out the solution
	            }
        	}
        }
        else {
        	System.out.println("The matrix does not have solution! "); // the matrix does not have solution
        }
    }
    
  //-----------------------------------------
    public static LinkedList<Integer> isInfinite(double[] con,int numVar,int numEq){
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
    public boolean check(double[] con, double[][] matrix) {
        if (hasSolution()) {
            double[] a = base();
            for (int i=0; i<num; i++){
                double result=0.0;
                for (int j=0; j<num; j++){
                     result=result+a[j]*matrix[i][j];
                }
                if(Math.abs(result-con[i])>EPSILON){
                   return false;
                }
            }
            return true;
        }
        else{
            double[] b=twoWay();
            for (int i=0; i<num; i++){
                double result=0.0;
                for (int j=0; j<num; j++) {
                     result=result+con[j]*matrix[j][i];
                }
                if (Math.abs(result)>EPSILON){
                    return false;
                }
            }
            double result=0.0;
            for (int i=0; i<num; i++){
                result=result+b[i]*con[i];

            }
            if (Math.abs(result)<EPSILON){
                return false;
            }
            return true;
        }
    }
    
}
