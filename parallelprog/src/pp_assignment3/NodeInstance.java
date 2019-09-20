package pp_assignment3;

public class NodeInstance extends NodeAbstract implements Node {

	private boolean isAwake = false;
	private int messages = 0;
	private Node father = null;

	public NodeInstance(String name, boolean initiator) {
		super(name, initiator);
	}

	@Override
	public void hello(Node neighbour) {
		if(!this.neighbours.contains(neighbour)) {
			this.neighbours.add(neighbour);
		}		
	}

	@Override
	public void wakeup(Node neighbour) {
		if(father == null) {
			father = neighbour;
			++messages;
		}
		if(this.neighbours.size() == 1) {
			father.echo(this, null);
		} else {
			for(Node i: neighbours) {
				i.wakeup(this);
			}
		}
	}

	@Override
	public void echo(Node neighbour, Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupNeighbours(Node... neighbours) {
		for(Node i: neighbours) {
			if(!this.neighbours.contains(i)) {
				this.neighbours.add(i);                
			}
			i.hello(this);
		}
	}
}