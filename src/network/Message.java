
package network;

import java.util.LinkedList;

public class Message {
    
    LinkedList<Node> path;
    int power;

    public Message() {
        this.path = new LinkedList<>();
    }

    public Message(LinkedList<Node> path, int power) {
        this.path = path;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    public void addNodeToPath(Node n){
        this.path.add(n);
    }
    
}
