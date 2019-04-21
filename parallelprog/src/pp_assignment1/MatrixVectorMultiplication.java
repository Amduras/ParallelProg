package pp_assignment1;
import java.util.concurrent.RecursiveAction;

public class MatrixVectorMultiplication extends RecursiveAction{

	private static final long serialVersionUID = 1L;
	
	final double[][] matrix;
	final double[] vector;
	double[] result;
	final int workThreshold;
	final int startIndex;
	final int taskLength;
	
	MatrixVectorMultiplication(final double[][] matrix, final double[] vector, final double[] result,final int workThreshold, final int startIndex,final int taskLength){
		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.workThreshold = workThreshold;
		this.startIndex = startIndex;
		this.taskLength= taskLength;
	}
		
	@Override
	protected void compute() {
		if(taskLength <= workThreshold) {
			for (int p=0; p<taskLength; ++p) {
				for(int i=0;i<matrix[0].length;++i)
				{
					result[startIndex+p] +=  matrix[startIndex+p][i] * vector[i];
					
				}
			}
		}
		else {
			if(taskLength%2==0) {
				int mid = taskLength/2;
				System.out.println("E "+mid);
				MatrixVectorMultiplication left = new MatrixVectorMultiplication(matrix,vector,result,workThreshold,startIndex,mid);
				MatrixVectorMultiplication right = new MatrixVectorMultiplication(matrix,vector,result,workThreshold,startIndex+mid,mid);
				left.fork();
				right.compute();
				left.join();
			}
			else {
				int mid = taskLength/2;
				System.out.println("UE "+mid);
				MatrixVectorMultiplication left = new MatrixVectorMultiplication(matrix,vector,result,workThreshold,startIndex,mid);
				MatrixVectorMultiplication right = new MatrixVectorMultiplication(matrix,vector,result,workThreshold,startIndex+mid,mid+1);
				left.fork();
				right.compute();
				left.join();
			}
		}	
	}
}
