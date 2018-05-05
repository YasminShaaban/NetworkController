
package network;


public class Dijkstra {
    
    final static double  MAXDOUBLE = 1000000000.0;
    public static Node[] Dijkstra(double [][] adj, int source) {
	         
		Node[] estimates = new Node[adj.length];
		
		// Set up our initial estimates.
		for (int i=0; i<estimates.length; i++)
	       estimates[i] = new Node(MAXDOUBLE , source);
			
		// This estimate is 0, now
		estimates[source].distance = 0;
		
		// Really, we can run this n-1 times, where n is the number of
		// vertices. The last iteration does NOT produce any different paths.
		for (int i=0; i<estimates.length-1; i++) {
			
			// Pick the minimal vertex to add into, S, our set of vertices
			// for which we have shortest distances.
			int vertex = 0;
			double bestseen = MAXDOUBLE ;
			
			// In order to be chosen here, you can not have been previously
			// chosen. Also, you have to be smaller than all other candidates.
			for (int j=0; j<estimates.length; j++) {
				if (estimates[j].chosen == false && 
				    estimates[j].distance < bestseen) {
				
					bestseen = estimates[j].distance;
					vertex = j;
				}
			}
			
			// Choose this vertex!
			estimates[vertex].chosen = true;
			
			// Update our estimates based on edges that leave from this vertex.
			for (int j = 0; j<estimates.length; j++) {
				
				// Do we get a shorter distance by traveling to vertex, and then
				// taking the edge from vertex to j? If so, make the update here.
				if (estimates[vertex].distance+adj[vertex][j] < 
				    estimates[j].distance) {
				    
				    // Our new estimate to get to j, going through vertex.
				    estimates[j].distance = estimates[vertex].distance + adj[vertex][j];	
				    
				    // This also means that vertex is the last vertex on the new
				    // shortest path to j, so we need to store this also.
				    estimates[j].last = vertex;
				}
			}
			
		}
		
		// We return these whole estimates array.
		return estimates;
	}
}