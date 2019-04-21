package pp_assignment1;

import java.util.concurrent.ForkJoinPool;

public class MatrixVectorMultiplicationController {

	public static void main(String[] args) {
		ForkJoinPool pool = ForkJoinPool.commonPool();
		int dim = 100;
		
		doit(pool,getTestMatrix(dim),getTestVector(dim),getTestResult(dim),10);
	}
	
	static void doit(ForkJoinPool pool, final double[][] matrix, final double[] vector, final double[] result,final int workThreshold) {
		pool.invoke(new MatrixVectorMultiplication(matrix,vector,result,workThreshold,0,matrix[0].length));
		double[] checkRes = makeSeq(matrix, vector);
		System.out.println(check(result, checkRes));
		print(result);
	}	
	
	static double[] getTestVector(int dim) {
		double[] vector = new double[dim];
		for (int i=0;i<vector.length;++i)
			vector[i] = (Math.random()*100);
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
				matrix[i][j] = (Math.random()*100);
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
	
	public static double[] makeSeq(double[][]mat, double[] vec) {
		double[] res = new double[vec.length];
		for(int i = 0; i < res.length; ++i) {
			for(int j = 0; j < mat[i].length; ++j) {
				res[i] += mat[i][j]*vec[j];
			}
		}
		return res;
	}
	
	public static String check(double[] res1, double[] res2) {
		for(int i = 0; i < res1.length; ++i) {
			if(res1[i] != res2[i])
				return "Fehler an position i: "+i;
		}
		return "Alles richtig";
	}

}
