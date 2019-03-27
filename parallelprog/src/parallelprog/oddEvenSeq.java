package parallelprog;

import java.util.Random;

public class oddEvenSeq {
	
	static <K> void oddEven(K[] field, Number<K> n){
		int oddOrEven=1;
		for(int i=0;i<field.length;++i) {			
			for(int j=oddOrEven;j<field.length-1;j+=2) {
				if(n.less(field[j],field[j+1]))
					swap(field,j,j+1);
			}
			oddOrEven=oddOrEven^1;
		}
	}
	
	static <K> void swap(K[] field,K a,K b){
		K temp = field[(int)a];
		field[(int)a] = field[(int)b];
		field[(int)b] = temp;
	}
	
	static void fillArray(Integer[] field)
	{
		Random rand = new Random(42);
		for(int i=0;i<field.length;++i)
//			field[i] = (int)((Math.random()*60)-30);	
			field[i] = rand.nextInt(100);
	}
	
	public static void main(String[] args) {
		Integer[] test = new Integer[10];
		fillArray(test);
		for(int number : test)
			System.out.print(number+"\t");
		System.out.println();
		oddEven(test,(x,y) -> x<y);
		for(int number : test)
			System.out.print(number+"\t");
	}
}