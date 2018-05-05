package network;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Node {
    int x;
    int y;
    boolean received;
    int ID;
    public double distance;
    public boolean chosen;
    public int last;
    
    public Node(double d, int source) {
         distance = d;
         last = source;
         chosen = false;
          }
    public Node(int x, int y,int ID) {
        this.x = x;
        this.y = y;
        this.ID=ID;
       }
   
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getID() {
        return ID;
    }
     public void setID(int ID) {
        this.ID=ID;
    }
     
    @Override
        public String toString(){
            return "["+"X of node :"+this.x+" ,"+ "Y of node :"+this.y+","+"ID: "+this.ID+"]";
        }
        
    
 public boolean isReceived() {
        return received;
    }

  public void setReceived(boolean received) {
        this.received = received;
    }
  
}
