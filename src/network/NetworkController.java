
package network;


import java.util.LinkedList;
import java.util.Random;
import network.Node;
import network.Dijkstra;

public class NetworkController {
   public static boolean  received;
   public static double POWER =0.0;
   public static double Variablepower=0.0;
   public static LinkedList<Node> path = new LinkedList<Node>();
   public static boolean ans = false;
   
   public static void main(String[] args) {
       
        int [] noofnodes= new int[]{5,10,15,20};
        Random rnd = new Random();
        double dist=0.0;
        boolean x;
        int s, r;
        
       // setting number of nodes
     for (int start=0;start<noofnodes.length;start++){
          double averagePower=0.0;
          double averageVariablepower=0.0;
         LinkedList<Node>[] nodes = (LinkedList<Node>[]) new LinkedList[noofnodes[start]];
         double  [][] distances=new double [noofnodes[start]][noofnodes[start]];//adjaceny matrix
        //Intialization of nodes "connected" and making for every node arraylist to store its neighbours
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new LinkedList<>();
            Node n=new Node(rnd.nextInt(100), rnd.nextInt(100), i);
            nodes[i].add(n);
        }
        
        //Intialization of each node's neighbours "connected" by calculating the distance between each two nodes
        for (int i = 0; i < nodes.length - 1; i++) {
            int x1 = nodes[i].get(0).getX();
            int y1 = nodes[i].get(0).getY();
            for (int j = i + 1; j < nodes.length; j++) {
                int x2 = nodes[j].get(0).getX();
                int y2 = nodes[j].get(0).getY();
                //distance between each two nodes
                dist = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
                if (dist <= 20) {
                    nodes[i].add(nodes[j].get(0));
                    nodes[j].add(nodes[i].get(0));
                    distances[i][j]=dist;
                    distances[j][i]=dist;
                    
                   
                }
                 else if (dist>20){
                    distances[i][j]=1000000000;
                    distances[j][i]=1000000000;
                    
                }
            }
        }
        
       //printing number of nodes
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println("Number of Nodes:"+ noofnodes[start]);
        System.out.println("------------------------------------");
        System.out.println();
        
        // printing the nodes and their neighbours "connected"
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].size(); j++) {
                System.out.print("node "+nodes[i].get(j).getID() + "      ");
            }
            if(nodes[i].size()==1){
               System.out.println("not connected to any other node"); 
              }
            else
            System.out.println("are connected");
        }
        System.out.println();
        // this must be repeated 1000 times 
         for (int i = 0; i < 1000; i++) {
            POWER = 0;
            path = new LinkedList<>();
            ans = false;
            //generating sender and receiver randomly
            s = rnd.nextInt(noofnodes[start]);
            r = rnd.nextInt(noofnodes[start]);
            while (s == r) {
                s = rnd.nextInt(noofnodes[start]);
            }
            //printing sender and receiver 
            System.out.println("SenderID:"+" "+s);
            System.out.println("ReceiverID:"+" "+r);
            System.out.println("SenderNode:"+""+nodes[s].get(0));
            System.out.println("ReceiverNode:"+""+nodes[r].get(0));
            
         //-------------------Dijkstra  Algorithm----------------------------------
	  // Run Dijkstra's and store the return array of shortest distances.
           Node[] answers =Dijkstra.Dijkstra(distances, s);
            // First part of the info we want.
            if (answers[r].distance !=1000000000){
	      System.out.println("Shortest distance from sender to receiver"+" is "+answers[r].distance+" meters.");
		                
	    // Set up variables to reconstruct the desired path.
	       String path2 = "";
	       int end = r;//receiver
	       int source = s;//sender
	       boolean firstTime = true;
               int counter=0;
		// We build the path up from the end, so when the end is the
		// source, we can stop.
		while (end != source) {
		
		// here we prepend the proper vertex to our path, working
		// backwards. The first time through, we don't add a -.
		  if (firstTime) 
	           path2= end + path2;
		  else
		   path2= (end + "-") + path2;
						
		 // Now that we've added end to our path, our new end is the
		// vertex that we would go to BEFORE our old end.
		end = answers[end].last;
	        // If we get here, we're done with our first iteration.
	        firstTime = false;	
		counter++;
	                         
                } // end while for building path.

                 path2 = source + "-" + path2;
	         // Now we can print out the path
	         System.out.println("Shortest Path between sender and receiver is "+path2+".");
                 Variablepower=answers[r].distance *0.05;
                 System.out.println("Total power of transmitting message from sender to receiver where power is variable with distance of shortest path: " + Variablepower+" mw.");
                 System.out.println("Total power of transmitting message from sender to receiver  where power is constant with distance of shortest path :"+ counter+" mw.");
                 System.out.println();
                 averagePower=averagePower+counter;
                 averageVariablepower=averageVariablepower+Variablepower;
                 
                }
                               
              else {
                   System.out.println("No possible path between sender and receiver");
                   System.out.println();
                   // another counter 
                                
                   }
	//calling sending message function
         x = send(nodes, s, r, new Message());
         for (int j = 0; j < nodes.length; j++) {
           nodes[j].get(0).setReceived(false);
            }
        }
         //Printing Average Power per message 
         System.out.println();
         System.out.println("******************************************************************************************");
         System.out.println("Average Power per message where power is constant with distance:"+averagePower/(1000)+" mw.");
         System.out.println("Average Power per message where power is variable with distance:"+averageVariablepower/(1000)+" mw.");
         System.out.println("******************************************************************************************");
         
        }
    }
   // sending message 
    public static boolean send(LinkedList<Node>[] nodes, int Sender, int Reciever, Message mes) {
        
        nodes[Sender].get(0).setReceived(true);//set received variable of node sender to true
        mes.addNodeToPath(nodes[Sender].get(0)); // add node sender to path
        mes.power++;
        for (int i = 1; i < nodes[Sender].size(); i++) {
          //if receiver is in the arraylist of sender
            if (nodes[Sender].get(i).getID() == Reciever) {
                //add node receiver to path
                mes.addNodeToPath(nodes[Sender].get(i));
                if (POWER == 0 || mes.power<POWER) {
                    POWER = mes.power;
                    path = mes.path;
                }
                //return true, message is sent to receiver
                return true;
            } 
        //if receiver is not in arraylist of sender ,check received variable of i's node of sender arraylist
        // it should be false at first
            else if (!nodes[Sender].get(i).isReceived()) {
             //ans is equal to false at first
            // call  send  function but this time let the sender be another node recursively 
        ans = ans || send(nodes, nodes[Sender].get(i).getID(), Reciever, new Message(new LinkedList<>(mes.path), mes.power));
            }
        }
        return ans;
    }
    
  }
 