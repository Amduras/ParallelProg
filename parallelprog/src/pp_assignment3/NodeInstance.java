package pp_assignment3;

import java.util.ArrayList;
import java.util.List;

public class NodeInstance extends NodeAbstract implements Node {

	private int messages = 0;
	private Node father = null;
	private List<NodeInstance> children = new ArrayList<NodeInstance>();

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
		if(this.neighbours.size() == 1 || messages == this.neighbours.size()) {
			father.echo(this, null);
		} else {
			for(Node i: neighbours) {
				i.wakeup(this);
			}
		}
	}

	@Override
	public void echo(Node neighbour, Object data) {
		++messages;
		children.add((NodeInstance) neighbour);
		System.out.println(this.toString()+": added "+neighbour.toString());
		if(this.initiator && messages == this.neighbours.size()) {
			System.out.println("~fin~");
			System.out.println(this.toString());
			for(NodeInstance child : children) {
				//child.print();
				//System.out.println();
			}
		}	
		else if(messages == this.neighbours.size())
			father.echo(this, data);
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
	
	public void print() {
		if(children.isEmpty())
			System.out.print(toString());
		else
			for(NodeInstance child : children) {
				System.out.print(child.toString()+" ");
				child.print();
			}
	}
}