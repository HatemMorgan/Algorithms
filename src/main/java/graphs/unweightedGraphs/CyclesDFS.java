package graphs.unweightedGraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CyclesDFS {

    private static class UndirectedGraph{
        List<Integer>[] adjList;
        int [] state, parent, entry, exit;
        int time;
        public UndirectedGraph(int numVertecies){
            this.adjList = new ArrayList[numVertecies];
            for(int i=0; i<numVertecies; ++i)
                adjList[i] = new ArrayList<>();

            state = new int[adjList.length];
            parent = new int[adjList.length];
            entry = new int[adjList.length];
            exit = new int[adjList.length];
            time = 0;
        }

        public void addEdge(int u, int v){
            adjList[u].add(v);
            adjList[v].add(u);
        }
    }

    private static void processEdge(UndirectedGraph g, int u, int v){
        if(g.parent[u] != v) { // found back edge
            System.out.println("Cycle detected  from " + u + " to " + v);
        }
    }

    // process vertex before processing its outgoing edges
    private static void processVertexEarly(int u){
        System.out.println("Processed vertex: " + u);
    }

    // process vertex after processing its outgoing edges
    private static void processVertexLate(int u){
    }

    final int UN_DISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;

    public void dfs (UndirectedGraph graph, int u){
        graph.state[u] = DISCOVERED;
        graph.time++;
        graph.entry[u] = graph.time;

        processVertexEarly(u);

        for(int v: graph.adjList[u]){
            // process the edge if it was not processed before
            // or the edge is directed (so its a new edge since u is being processed)
            if(graph.state[v] == UN_DISCOVERED){
                graph.parent[v] = u;
                dfs(graph, v);
            }else{
                // process edges where the destination was discovered but not fully processed yet
                if(graph.state[v] != PROCCESSED)
                    processEdge(graph, u, v); // back edge pointing to an ancestor of u
            }
        }

        processVertexLate(u);
        graph.time++;
        graph.exit[u] = graph.time;
        graph.state[u] = PROCCESSED;
    }

    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);


        CyclesDFS d = new CyclesDFS();

        g.parent[0] = -1;
        d.dfs(g, 0);
        System.out.println(Arrays.toString(g.state));
        System.out.println(Arrays.toString(g.parent));
        System.out.println(Arrays.toString(g.entry));
        System.out.println(Arrays.toString(g.exit));

        System.out.println("================================");
        System.out.println("");

        UndirectedGraph g2 = new UndirectedGraph(3);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);

        g2.parent[0] = -1;
        d.dfs(g2, 0);
        System.out.println(Arrays.toString(g2.state));
        System.out.println(Arrays.toString(g2.parent));
        System.out.println(Arrays.toString(g2.entry));
        System.out.println(Arrays.toString(g2.exit));

    }
}
