import java.util.*; 
import java.lang.*; 
import java.io.*; 
  
class yes 
{ 
    // A function to find the vertex with minimum distance value, 
    // from the set of vertices not yet included in shortest path tree 
    Scanner sn = new Scanner(System.in);
    int tot;
    int minDistance(int dist[], Boolean sptSet[]) 
    { 
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index=-1; 
  
        for (int v = 0; v < tot; v++) 
            if (sptSet[v] == false && dist[v] <= min) 
            { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 
  
    // Prints the constructed distance array 
    void printSolution(int dist[], int n) 
    { 
        System.out.println("Vertex   Distance from Source"); 
        for (int i = 0; i < tot; i++) 
            System.out.println("Shortest path to router "+(i+1) + ": cost: "+dist[i]); 
    } 
  
    // Funtion that implements Dijkstra's algorithm using a matrix
    void dijkstra(int graph[][], int src) 
    { 
        int dist[] = new int[tot]; // The output array. dist[i] will hold 
                                 // the shortest distance from src to i 
  
        // sptSet[i] will true if vertex i is included in shortest 
        // path tree or shortest distance from src to i is finalized 
        Boolean sptSet[] = new Boolean[tot]; 
  
        // Initialize all distances as INFINITE and stpSet[] as false 
        for (int i = 0; i < tot; i++) 
        { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
        
        // Distance of source vertex from itself is always 0 
        dist[src] = 0; 
        
        // Find shortest path for all vertices 
        for (int count = 0; count < tot-1; count++) 
        { 
            // Pick the minimum distance vertex from the set of vertices 
            // not yet processed. u is always equal to src in first 
            // iteration. 
            int u = minDistance(dist, sptSet); 
  
            // Mark the picked vertex as processed 
            sptSet[u] = true; 
  
            // Update dist value of the adjacent vertices of the 
            // picked vertex. 
            for (int v = 0; v < tot; v++) 
  
                // Update dist[v] only if is not in sptSet, there is an 
                // edge from u to v, and total weight of path from src to 
                // v through u is smaller than current value of dist[v] 
                if (!sptSet[v] && graph[u][v]!=0 && 
                        dist[u] != Integer.MAX_VALUE && 
                        dist[u]+graph[u][v] < dist[v]) 
                    dist[v] = dist[u] + graph[u][v]; 
        }
        printSolution(dist, tot); 
    } 
    
    public static void main (String[] args) 
    { 
        yes t = new yes();
        t.tot = Integer.parseInt(t.sn.nextLine());
        int graph[][] = new int[t.tot][t.tot];
        for(int i = 0; i < t.tot; i++)
        {
            String temp2 = t.sn.nextLine();
            String[] temp3 = temp2.split(" ");
            int temp6 = Integer.parseInt(temp3[0]);
            for(int j = 1; j < temp3.length; j++)
            {
                MyPair temp7 = new MyPair(Integer.parseInt(temp3[j]), 
                        Integer.parseInt(temp3[j + 1]));
                graph[temp6 - 1][temp7.Router() - 1] = temp7.Cost();
                j++;
            }
        }
        t.dijkstra(graph, 0);
    }
}