package pp_assignment3.echo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class NodeInstance extends NodeAbstract implements Node {
	
	static AtomicLong check = new AtomicLong(); 
	private int messages = 0;
	private Node father = null;
	List<NodeInstance> children = new CopyOnWriteArrayList<NodeInstance>();
	
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
		int tmp = messages;
		if(!this.initiator) {
			++tmp;
			++messages;
		}
        if(father == null) {            
            father = neighbour;
//            System.out.println(this.toString()+" added "+neighbour.toString()+" as father");
            if(!(tmp == this.neighbours.size())) {
                for(Node i: neighbours) {
                    if(i != father) {
//                        System.out.println(toString()+", exploring: "+i.toString());
                        new Thread(()->i.wakeup(this)).start();
                    }
                }
            }
        }                
        if(tmp == this.neighbours.size())
        	new Thread(()->father.echo(this, null)).start();
    }
	
	@Override
	public void echo(Node neighbour, Object data) {
		int tmp = ++messages;
		check.getAndIncrement();
//		System.out.println("Echo from: "+neighbour.toString()+" to: "+this.toString());
		//++messages;
		children.add((NodeInstance) neighbour);
//		System.out.println(this.toString()+": added "+neighbour.toString());
		if(this.initiator && tmp == this.neighbours.size()) {	
			System.out.println("~fin~");
			System.out.println(this.toString());
			for(NodeInstance child : children) {
				System.out.print(child.toString()+" ");
				child.print();
				System.out.println();
			}
			System.out.println("Echos: "+check.get());
			check.set(0);
		}	
		else if(tmp == this.neighbours.size())
			new Thread(()->father.echo(this, null)).start();
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
		if(!children.isEmpty()) {			
			for(NodeInstance child : children) {
				System.out.print(child.toString()+" ");
				child.print();
			}
		}
	}
}