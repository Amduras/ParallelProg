package pp_assignment3.election;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class NodeInstance extends NodeAbstract implements Node {
    
    private int messages = 0;
    private int messagesEcho = 0;
    private Node father = null;
    private Node fatherEcho = null;
    List<NodeInstance> children = new CopyOnWriteArrayList<NodeInstance>();
    public int maxId = 0;
    
    public NodeInstance(String name, boolean initiator, int id) {
        super(name, initiator, id);
    }

    @Override
    public void hello(Node neighbour) {
        if(!this.neighbours.contains(neighbour) && neighbour != this) {
            this.neighbours.add(neighbour);
        }        
    }    
    
    @Override
    public synchronized void wakeup(Node neighbour, int mId) {
    	if(neighbour == null)				//starting wakeup
    		for(Node i: neighbours)
                    new Thread(()->i.wakeup(this,maxId)).start();
    	else{
    		if(maxId > mId)
    			new Thread(()->neighbour.wakeup(this,maxId)).start();
    		else if(maxId < mId) {
    			messages = 1;
    			father = neighbour;
    			initiator = false;
    			maxId = mId;    		
    			for(Node i: neighbours)
    				if(i != father)
    					new Thread(()->i.wakeup(this,maxId)).start();
    		}
    		else if (maxId == mId)
    			++messages;
    		else
    			System.out.println("Das darf nicht passieren.");
    	
    		if(father != null && messages == neighbours.size()) {
    			new Thread(()->father.echo(this, maxId)).start();
    		}
    	}
    }
    
    @Override
    public synchronized void echo(Node neighbour, Object data) {
    	if((int)data < maxId )
    		new Thread(()->neighbour.wakeup(this,maxId)).start();
        int tmp = ++messages;
        if(this.initiator && tmp == this.neighbours.size()) {                
            Controller.printNodeMaxId();
            Controller.fin = false;
            
            //starting echo algorythm
            new Thread(()->this.wakeupEcho(this)).start();
        }    
        else if(tmp == neighbours.size())
            new Thread(()->father.echo(this, maxId)).start();
    }
    
    @Override
    public synchronized void wakeupEcho(Node neighbour) {
        int tmp = messagesEcho;        
        if(!this.initiator) {
            ++tmp;
            ++messagesEcho;
        }
        if(fatherEcho == null) {            
            fatherEcho = neighbour;
            maxId = 0;
            if(!(tmp == neighbours.size())) {
                for(Node i: neighbours) {
                    if(i != father) {
                        new Thread(()->i.wakeupEcho(this)).start();
                    }
                }
            }
        }                
        if(tmp == neighbours.size())
            new Thread(()->father.echoEcho(this, null)).start();
    }
    @Override
	public synchronized void echoEcho(Node neighbour, Object data) {
    	 int tmp = ++messagesEcho;
         children.add((NodeInstance) neighbour);
         if(this.initiator && tmp == neighbours.size()) {
        	 initiator = false;
             System.out.println("\n"+toString());
             for(NodeInstance child : children) {
                 System.out.print(child.toString()+" ");
                 child.print();
                 System.out.println();
                 Controller.running = false;
             }
         }    
         else if(tmp == this.neighbours.size())
             new Thread(()->father.echoEcho(this, null)).start();
		
	}

    @Override
    public void setupNeighbours(Node... neighbours) {
        for(Node i: neighbours) {
            if(!this.neighbours.contains(i) && i != this) {
                this.neighbours.add(i);                
            }
            i.hello(this);
        }
    }
    
    public void setInitiator(int id) {
    	initiator = true;
    	maxId = id;
    }

    public void print() {
        if(!children.isEmpty()) {            
            for(NodeInstance child : children) {
                System.out.print(child.toString()+" ");
                child.print();
            }
        }
        clear();
    }
    
    private void clear() {
    	messages = 0;
        messagesEcho = 0;
        father = null;
        fatherEcho = null;
        children.clear();
        maxId = 0;
    }
}