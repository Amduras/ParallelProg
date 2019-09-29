package pp_assignment3;

public class Controller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NodeInstance[] nodes = createTestTree();
		//nodes[0].wakeup(nodes[0]);
		nodes[0].start();
		
		//for(NodeInstance node: nodes)
		//	System.out.println(node.children.toString());
	}
	
	private static NodeInstance[] createTestTree() {
		NodeInstance[] nodes = new NodeInstance[8];
		nodes[0] = new NodeInstance("blue", true);
		nodes[1] = new NodeInstance("magenta", false);
		nodes[2] = new NodeInstance("green", false);
		nodes[3] = new NodeInstance("red", false);
		nodes[4] = new NodeInstance("orange", false);
		nodes[5] = new NodeInstance("yellow", false);
		nodes[6] = new NodeInstance("afrika", false);
		nodes[7] = new NodeInstance("europe", false);
		
		nodes[0].setupNeighbours(nodes[1],nodes[3]);
		nodes[1].setupNeighbours(nodes[0],nodes[2]);
		nodes[2].setupNeighbours(nodes[1]);
		nodes[3].setupNeighbours(nodes[0],nodes[4],nodes[5]);
		nodes[4].setupNeighbours(nodes[3],nodes[5],nodes[6]);
		nodes[5].setupNeighbours(nodes[3],nodes[4],nodes[7]);
		nodes[6].setupNeighbours(nodes[4],nodes[7],nodes[2]);
		nodes[7].setupNeighbours(nodes[5],nodes[6]);
		return nodes;
	}
}