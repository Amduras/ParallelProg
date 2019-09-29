package pp_assignment3.echo;

public class Controller {
	static int BLUE = 0, MAGENTA = 1, GREEN = 2, RED = 3,
			ORANGE = 4, YELLOW = 5, BLACK = 6, WHITE = 7,
			BROWN = 8;
	static NodeInstance[] nodes;
	static boolean fin = true;
	
	public static void main(String[] args) {
		for(int i = 0; i < 100; ++i) {
			System.out.print("Durchgang: "+(i+1)+" ");
			int initiator = ORANGE;
			nodes = createCompleteTree();
			new Thread(()->nodes[initiator].wakeup(nodes[initiator])).start();
			nodes[initiator].wakeup(nodes[initiator]);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static NodeInstance[] createTestTree() {
		NodeInstance[] nodes = new NodeInstance[9];
		nodes[BLUE] = new NodeInstance("blue", false);
		nodes[MAGENTA] = new NodeInstance("magenta", false);
		nodes[GREEN] = new NodeInstance("green", false);
		nodes[RED] = new NodeInstance("red", false);
		nodes[ORANGE] = new NodeInstance("orange", true);
		nodes[YELLOW] = new NodeInstance("yellow", false);
		nodes[BLACK] = new NodeInstance("black", false);
		nodes[WHITE] = new NodeInstance("white", false);
		nodes[BROWN] = new NodeInstance("brown", false);
		
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
	
	private static NodeInstance[] createCompleteTree() {
		NodeInstance[] nodes = new NodeInstance[5];
		nodes[BLUE] = new NodeInstance("blue", false);
		nodes[MAGENTA] = new NodeInstance("magenta", false);
		nodes[GREEN] = new NodeInstance("green", false);
		nodes[RED] = new NodeInstance("red", false);
		nodes[ORANGE] = new NodeInstance("orange", true);

		
		nodes[BLUE].setupNeighbours(nodes[MAGENTA],nodes[GREEN],nodes[RED],nodes[ORANGE]);
		nodes[MAGENTA].setupNeighbours(nodes[BLUE],nodes[GREEN],nodes[RED],nodes[ORANGE]);
		nodes[GREEN].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[RED],nodes[ORANGE]);
		nodes[RED].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[GREEN],nodes[ORANGE]);
		nodes[ORANGE].setupNeighbours(nodes[BLUE],nodes[MAGENTA],nodes[GREEN],nodes[RED]);
		
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