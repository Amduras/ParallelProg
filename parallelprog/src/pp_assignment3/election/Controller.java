package pp_assignment3.election;

import java.util.Arrays;


public class Controller {
	static int BLUE = 0, MAGENTA = 1, GREEN = 2, RED = 3,
			ORANGE = 4, YELLOW = 5, BLACK = 6, WHITE = 7,
			BROWN = 8;
	static int Min = 1, Max = 20;
	static NodeInstance[] nodes;
	static boolean fin = true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int initiator = ORANGE;
		int length = 9;
		Integer[] id = createId(length);
		nodes = createTestTree(length, id);
		System.out.println("Red: "+id[RED]+" Brown: "+id[BROWN]);
		nodes[RED].setInitiator(id[RED]);
		nodes[BROWN].setInitiator(id[BROWN]);
		new Thread(()->nodes[RED].wakeup(nodes[RED],id[RED])).start();
		new Thread(()->nodes[BROWN].wakeup(nodes[BROWN],id[BROWN])).start();
		//for(NodeInstance node: nodes)
		//	System.out.println(node.children.toString());
	}
	private static Integer[] createId(int length) {
		Integer[] id  = new Integer[length];
		int i = 0;
		while(i != id.length) {
			Integer x = Min + (int)(Math.random() * ((Max - Min) + 1));
			if(!Arrays.asList(id).contains(x)) {
				id[i++] = x;
			}
		}
		return id;

	}
	private static NodeInstance[] createTestTree(int length, Integer[] id) {
		NodeInstance[] nodes = new NodeInstance[length];
		nodes[BLUE] = new NodeInstance("blue", false, id[0]);
		nodes[MAGENTA] = new NodeInstance("magenta", false, id[1]);
		nodes[GREEN] = new NodeInstance("green", false, id[2]);
		nodes[RED] = new NodeInstance("red", false, id[3]);
		nodes[ORANGE] = new NodeInstance("orange", false, id[4]);
		nodes[YELLOW] = new NodeInstance("yellow", false, id[5]);
		nodes[BLACK] = new NodeInstance("black", false, id[6]);
		nodes[WHITE] = new NodeInstance("white", false, id[7]);
		nodes[BROWN] = new NodeInstance("brown", false, id[8]);
		
		nodes[BLUE].setupNeighbours(nodes[MAGENTA],nodes[RED]);
		nodes[MAGENTA].setupNeighbours(nodes[BLUE],nodes[GREEN]);
		nodes[GREEN].setupNeighbours(nodes[MAGENTA],nodes[BROWN]);
		nodes[RED].setupNeighbours(nodes[BLUE],nodes[YELLOW],nodes[ORANGE],nodes[RED]);
		nodes[ORANGE].setupNeighbours(nodes[RED],nodes[BLACK],nodes[BROWN]);
		nodes[YELLOW].setupNeighbours(nodes[RED],nodes[BLACK],nodes[WHITE]);
		nodes[BLACK].setupNeighbours(nodes[ORANGE],nodes[YELLOW]);
		nodes[WHITE].setupNeighbours(nodes[YELLOW],nodes[WHITE]);
		nodes[BROWN].setupNeighbours(nodes[ORANGE],nodes[GREEN]);
		
		return nodes;
	}
	
	static void printChildren() {
		if(fin) {
			for(NodeInstance i: nodes) {
				System.out.print("Children from "+i.toString()+": ");
				for(NodeInstance x: i.children) {
					System.out.print(x.toString()+" ");
				}
				System.out.println();
			}
		}
	}
}