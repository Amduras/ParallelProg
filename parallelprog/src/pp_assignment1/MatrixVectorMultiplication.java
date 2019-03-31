package pp_assignment1;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class MatrixVectorMultiplication extends RecursiveAction{

	private static final long serialVersionUID = 1L;
	
	final double[][] matrix;
	final double[] vector;
	double[] result;
	final int startIndex;
	final int length;
	
	MatrixVectorMultiplication(final double[][] matrix, final double[] vector, final double[] result, final int startIndex, final int length){
		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.startIndex = startIndex;
		this.length = length;
	}
		
	@Override
	protected void compute() {
		if(kleingenug) {
			for (int i=0; i< length;++i)
				result[startIndex] +=  matrix[startIndex][i] * vector[i];
		}
		else {
			int mid = length/2;
			MatrixVectorMultiplication upper = new MatrixVectorMultiplication(matrix,vector,result,0,mid);
			MatrixVectorMultiplication lower = new MatrixVectorMultiplication(matrix,vector,result,mid,length);
		}
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = ForkJoinPool.commonPool();
		int dim = 10;
		doit(pool,getTestMatrix(dim),getTestVector(dim),getTestResult(dim));		
	}
	
	static void doit(ForkJoinPool pool, final double[][] matrix, final double[] vector, final double[] result) {
		for(int i=0;i<matrix.length;++i)
			pool.invoke(new MatrixVectorMultiplication(matrix,vector,result,i,matrix[i].length));
		print(result);
	}
	
	static double[] getTestVector(int dim) {
		double[] vector = new double[dim];
		return vector;
	}
	
	static double[] getTestResult(int dim) {
		double[] result = new double[dim];
		return result;
	}
	
	static double[][] getTestMatrix(int dim){
		double[][] matrix = new double[dim][dim];
		for (int i=0;i<matrix.length;++i)
			for(int j=0;j<matrix[i].length;++j)
				matrix[i][j] = Math.random()*Double.MAX_VALUE;
		return matrix;
	}
	static void print(double[] matrix) {
		for (int i=0;i<matrix.length;++i) {
			System.out.print(matrix[i]+"\t");
		}
		System.out.println();
	}
	
	static void print(double[][] matrix) {
		for (int i=0;i<matrix.length;++i) {
			for(int j=0;j<matrix[i].length;++j)
				System.out.print(matrix[i][j]+"\t");
			System.out.println();
		}
	}
}
