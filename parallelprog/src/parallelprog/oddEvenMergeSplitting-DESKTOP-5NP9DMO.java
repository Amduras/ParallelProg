package parallelprog;

public class oddEvenMergeSplitting {
	
	static void oddEven(int[][] field){
		int oddOrEven=1;
		for(int i=0;i<field.length;++i) {
			for(int j=oddOrEven;j<field.length-1;j+=2) {				
				//if(j< (field.length-1)) {
					int[] temp = new int[field[j].length*2];
					for(int k=0;k<field[j].length;++k) {
						temp[k] = field[j][k];
						temp[k+temp.length/2] = field[j+1][k];
					}
					sortArray(temp);
					for(int k=0;k<field[j].length;++k) {
						field[j][k] = temp[k];
						field[j+1][k] = temp[k+temp.length/2];
					}
				//}	
			}
			oddOrEven=oddOrEven^1;
		}
	}
	
	static void initArray(int[][] field) {
		for(int i=0;i<field.length;++i) {
			for(int j=0;j<field[i].length;++j) {
				field[i][j] = (int)(Math.random()*100);
			}
		}
	}
	
	static void sortArray(int[] field) {
		for(int i=0;i<field.length;++i)
			for(int j=0;j<field.length-1;++j) {
				if(field[j]>field[j+1]) {
					int temp = field[j];
					field[j] = field[j+1];
					field[j+1] = temp;
				}
			}
	}
	
	static void sortArray(int[][] field) {
		for(int i=0;i<field.length;++i) {
			for(int j=0;j<field[i].length;++j) {
				for(int k=0;k<field[i].length-1;++k) {
					if(field[i][k]>field[i][k+1]) {
						int temp = field[i][k];
						field[i][k] = field[i][k+1];
						field[i][k+1] = temp;
					}
				}
			}
		}
	}
	
	static void printArray(int[][] field) {
		for(int i=0;i<field.length;++i) {
			for(int j=0;j<field[i].length;++j) {
				System.out.print(field[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		int[][] test = new int[(int)(Math.random()*30)][((int)(Math.random()*7))+3];
		initArray(test);
		sortArray(test);
		printArray(test);
		oddEven(test);
		printArray(test);
	}
}