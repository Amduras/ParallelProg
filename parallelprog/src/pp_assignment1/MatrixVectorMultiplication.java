package pp_assignment1;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MatrixVectorMultiplication extends RecursiveAction{

	private static final long serialVersionUID = 1L;
	
	final int[][] matrix;
	final int[] vector;
	int[] result;
	static int[] checkRes;
	final int startIndex;
	final int length;
	
	MatrixVectorMultiplication(final int[][] matrix, final int[] vector, final int[] result, final int startIndex, final int length){
		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.checkRes = result;
		this.startIndex = startIndex;
		this.length = length;
	}
		
	@Override
	protected void compute() {
		for (int i=0; i< length;++i) {
			result[startIndex] +=  matrix[startIndex][i] * vector[i];
			System.out.println("I: "+startIndex+"Res: "+result[startIndex]+" "+matrix[startIndex][i]+" "+vector[i]);
		}
	
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = ForkJoinPool.commonPool();
		int dim = 10;
		doit(pool,getTestMatrix(dim),getTestVector(dim),getTestResult(dim));
		
	}
	
	static void doit(ForkJoinPool pool, final int[][] matrix, final int[] vector, final int[] result) {
		for(int i=0;i<matrix.length;++i)
			pool.invoke(new MatrixVectorMultiplication(matrix,vector,result,i,matrix[i].length));
		checkRes = makeSeq(matrix, vector);
		//result[5] = 10;
		System.out.println(check(result, checkRes));
		print(result);
	}
	
	static int[] getTestVector(int dim) {
		int[] vector = new int[dim];
		for (int i=0;i<vector.length;++i)
			vector[i] = i;
		return vector;
	}
	
	static int[] getTestResult(int dim) {
		int[] result = new int[dim];
		return result;
	}
	
	static int[][] getTestMatrix(int dim){
		int[][] matrix = new int[dim][dim];
		for (int i=0;i<matrix.length;++i)
			for(int j=0;j<matrix[i].length;++j)
				matrix[i][j] = 1;//Math.random()*int.MAX_VALUE;
		return matrix;
	}
	static void print(int[] matrix) {
		for (int i=0;i<matrix.length;++i) {
			System.out.print(matrix[i]+"\t");
		}
		System.out.println();
	}
	
	static void print(int[][] matrix) {
		for (int i=0;i<matrix.length;++i) {
			for(int j=0;j<matrix[i].length;++j)
				System.out.print(matrix[i][j]+"\t");
			System.out.println();
		}
	}
	
	public static int[] makeSeq(int[][]mat, int[] vec) {
		int[] res = new int[vec.length];
		for(int i = 0; i < res.length; ++i) {
			for(int j = 0; j < mat[i].length; ++j) {
				res[i] += mat[i][j]*vec[j];
			}
		}
		return res;
	}
	
	public static String check(int[] res1, int[] res2) {
		for(int i = 0; i < res1.length; ++i) {
			if(res1[i] != res2[i])
				return "Fehler an position i: "+i;
		}
		return "Alles richtig";
	}
}
