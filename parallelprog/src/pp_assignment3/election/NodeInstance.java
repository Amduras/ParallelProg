package pp_assignment3.election;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class NodeInstance extends NodeAbstract implements Node {
    
    static AtomicLong check = new AtomicLong(); 
    private int messages = 0;
    private Node father = null;
    public List<NodeInstance> children = new CopyOnWriteArrayList<NodeInstance>();
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
    public synchronized void wakeup(Node neighbour, int mid) {
    	if(neighbour == null)				//starting wakeup
    		for(Node i: neighbours)
                    new Thread(()->i.wakeup(this,maxId)).start();
    	else{
    		if(maxId > mid)
    			new Thread(()->neighbour.wakeup(this,maxId)).start();
    		else if(maxId < mid) {
    			messages = 1;
    			father = neighbour;
    			initiator = false;
    			maxId = mid;    		
    			for(Node i: neighbours)
    				if(i != father)
    					new Thread(()->i.wakeup(this,maxId)).start();
    		}
    		else if (maxId == mid)
    			++messages;
    		else
    			System.out.println("Das darf nicht passieren.");
    	
    		if(father != null && messages == neighbours.size()) {
    			new Thread(()->father.echo(this, maxId)).start();
    		}
    	}
    }   	
    	/*Wenn meineID > mid
    		nachbar.wakeup(this,meineID)
    	esle Wenn meineID < mid
    		messages=1
    		father = nachbarn
    		meineId = mid
    		wakeup an alle nachbarn außer father
    	else Wenn meineID == mid
    		messages+1
    	
    	Wenn messages==nachbar.size && father != null
    		echo father.
    	*/
    	
    	
       /* int tmp = messages;
        //if(!this.initiator) {
        ++tmp;
        ++messages;
         if (mid > maxId) {
        	System.out.println("Override "+maxId+" to "+mid+", "+neighbour.toString()+" becoming father of "+toString());
        	father = neighbour;
            maxId =  mid;
            
            messages = 1;
        	if(initiator)
        		System.out.println(toString()+" auf false mit id: "+mid);
        	initiator = false;
        	
            if(!(tmp == this.neighbours.size())) {
                for(Node i: neighbours) {
                    if(i != father) {
                    	System.out.println(toString()+"("+tmp+"), exploring: "+i.toString());
                        new Thread(()->i.wakeup(this,maxId)).start();
                    }
                }
            }
            else if(tmp == this.neighbours.size())
            	new Thread(()->father.echo(this, maxId)).start();
        }
        else  if(father == null) {
         	System.out.println(neighbour.toString()+" becoming father of "+toString());
         	father = neighbour;
            maxId = maxId < mid ? mid : maxId;
             
             if(!(tmp == this.neighbours.size())) {
                 for(Node i: neighbours) {
                     if(i != father) {
                     	System.out.println(toString()+"("+tmp+"), exploring: "+i.toString());
                         new Thread(()->i.wakeup(this,maxId)).start();
                     }
                 }
             }
             else if(tmp == this.neighbours.size())
             	new Thread(()->father.echo(this, maxId)).start();
        }
        else if(tmp == this.neighbours.size())
        	new Thread(()->father.echo(this, maxId)).start();
        /*
        if(father == null || id > maxId) {            
            if(id > maxId) {
            	messages = 1;
            	if(initiator)
            		System.out.println(toString()+" auf false mit id: "+id);
            	initiator = false;
            }
                
            
            father = neighbour;
            maxId = id;
            System.out.println(this.toString()+" added "+neighbour.toString()+" as father");
            if(!(tmp == this.neighbours.size())) {
                for(Node i: neighbours) {
                    if(i != father) {
                    	System.out.println(toString()+"("+tmp+"), exploring: "+i.toString());
                        new Thread(()->i.wakeup(this,maxId)).start();
                    }
                }
            }
        }
        if(tmp == this.neighbours.size())
        	new Thread(()->father.echo(this, maxId)).start();
    }*/
    
    @Override
    public synchronized void echo(Node neighbour, Object data) {
    	if((int)data < maxId )
    		new Thread(()->neighbour.wakeup(this,maxId)).start();
        int tmp = ++messages;
        check.getAndIncrement();
        //System.out.println("Echo from: "+neighbour.toString()+" to: "+this.toString()+" "+tmp+" ini: "+initiator+ " size: "+neighbours.size());
        //++messages;
        if(!children.contains((NodeInstance)neighbour) && (NodeInstance)neighbour != this) {
        	children.add((NodeInstance) neighbour);
        	//System.out.println(this.toString()+": added "+neighbour.toString());
        }
        if(this.initiator && tmp == this.neighbours.size()) {    
            System.out.println("~fin~");
            Controller.printChildren();
            Controller.fin = false;
            Controller.running = false;
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
            new Thread(()->father.echo(this, maxId)).start();
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
    }
}