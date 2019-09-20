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
		if(father != null) return;
		
		father = neighbour;
		if(!this.initiator)
			++messages;
		System.out.println(toString()+", aufgeweckt von: "+ neighbour.toString() + " messages: "+messages);
		for(Node i: neighbours) {
			if(i != father)
				i.wakeup(this);
		}		
		if(messages == neighbours.size()) {
			System.out.println(toString()+", sende echo an "+ father.toString() + " messages: "+messages);
			father.echo(this, null);	
		}			
	}
	
	@Override
	public void echo(Node neighbour, Object data) {
		//++messages;
		
		
		++messages;
		children.add((NodeInstance) neighbour);
		System.out.println(this.toString()+": added "+neighbour.toString());
		if(initiator && messages == neighbours.size()) {
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