import java.util.*; 

class yes 
{
    Scanner sn = new Scanner(System.in);
    int tot;
    String[] path;
    String pathtemp = "";
    static int src = 0;
    /*
    A function to find the vertex with the smallest distance, from the set of 
    vertices that the shortest distance hasn't been found yet
    */
    int minDistance(int dist[], Boolean sptSet[]) 
    {  
        pathtemp = "";
        int min = Integer.MAX_VALUE, min_index=-1; 
        for (int v = 0; v < tot; v++) 
            if (sptSet[v] == false && dist[v] <= min) 
            { 
                min = dist[v];
                min_index = v;
                
                path[min_index] += v;
                
            }
        
        return min_index;
    } 
   
    /*
    Prints the shortest path messages
    */
    void print(int dist[], int n, gui gui) 
    { 
        String temp = "";
        gui.clearOutput();
        for (int i = 0; i < tot; i++) 
        {
            temp = ("Shortest path to router " + 
                    (i+1) + ": " + path[i] + ": cost: " + dist[i]);
            gui.printOutput(temp);
        }
    } 
    
    /*
    Funtion that implements Dijkstra's algorithm using a matrix
    */
    void dijkstra(int graph[][], int src, gui gui) 
    { 
        int dist[] = new int[tot]; 
        Boolean sptSet[] = new Boolean[tot]; 
        for (int i = 0; i < tot; i++) 
        { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        }  
        dist[src] = 0;
        path[src] = "1 ";
        for (int count = 0; count < tot-1; count++) 
        { 
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
            path[u] = path[src];
            for (int v = 0; v < tot; v++)
            {
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE
                        && dist[u]+graph[u][v] < dist[v]) 
                {
                    dist[v] = dist[u] + graph[u][v];
                    if(u != 0)
                    {
                        path[u] +=  + 1;
                    }
                }
            }
        }
        print(dist, tot, gui);
    } 
    /*
    This is the main function when using the gui.
    */
    public static void gui(String txt, gui gui)
    {
        int h = 1;
        String[] temp = txt.split("\n");
        yes t = new yes();
        t.tot = Integer.parseInt(temp[0]);
        t.path = new String[t.tot];
        int graph[][] = new int[t.tot][t.tot];
        for(int i = 0; i < t.tot; i++)
        {
            String temp2 = temp[h++];
            String[] temp3 = temp2.split(" ");
            int temp6 = Integer.parseInt(temp3[0]);
            for(int j = 1; j < temp3.length; j++)
            {
                graph[temp6 - 1][(Integer.parseInt(temp3[j]) - 1)] = 
                        Integer.parseInt(temp3[j + 1]);
                j++;
            }
        }
        t.dijkstra(graph, src, gui);
    }
    
    /*
    Main function. This doesn't work with the gui. 
    */
    public static void main (String[] args) 
    { 
        yes t = new yes();
        t.tot = Integer.parseInt(t.sn.nextLine());
        t.path = new String[t.tot];
        int graph[][] = new int[t.tot][t.tot];
        for(int i = 0; i < t.tot; i++)
        {
            String temp2 = t.sn.nextLine();
            String[] temp3 = temp2.split(" ");
            int temp6 = Integer.parseInt(temp3[0]);
            for(int j = 1; j < temp3.length; j++)
            {
                graph[temp6 - 1][(Integer.parseInt(temp3[j]) - 1)] = 
                        Integer.parseInt(temp3[j + 1]);
                j++;
            }
        }
        //t.dijkstra(graph, src);
    }
}