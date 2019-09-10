package pp_assignment3;

public class NodeInstance extends NodeAbstract implements Node {

	private boolean isAwake = false;
	
	public NodeInstance(String name, boolean initiator) {
		super(name, initiator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hello(Node neighbour) {
		if(!this.neighbours.contains(neighbour)) {
            this.neighbours.add(neighbour);
		}		
	}

	@Override
	public void wakeup(Node neighbour) {
		// TODO Auto-generated method stub
		
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