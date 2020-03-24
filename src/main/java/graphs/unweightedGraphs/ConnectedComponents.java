package graphs.unweightedGraphs;

import java.util.*;

public class ConnectedComponents {

    private static class UndirectedGraph{
        List<Integer>[] adjList;
        int[] state;

        public UndirectedGraph(int numVertecies){
            this.adjList = new ArrayList[numVertecies];
            for(int i=0; i<numVertecies; ++i)
                adjList[i] = new ArrayList<>();

            state = new int[adjList.length];
        }

        public void addEdge(int u, int v){
            adjList[u].add(v);
            adjList[v].add(u);
        }
    }

    final int UNDISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;
    int currComp = 0;
    int[] cc;

    private void processVertexEarly(int u){
        cc[u] = currComp;
    }

    private void bfs(UndirectedGraph graph, int start){
        Queue<Integer> queue = new LinkedList<>();
        graph.state[start] = DISCOVERED;
        queue.add(start);

        while(!queue.isEmpty()){
            int u = queue.poll();
            processVertexEarly(u);

            for(int v : graph.adjList[u]){
                if(graph.state[v] == UNDISCOVERED){
                    graph.state[v] = DISCOVERED;
                    queue.add(v);
                }
            }

            graph.state[u] = PROCCESSED;
        }
    }

    /**
     * We start from the first vertex. Anything we discover during
     * this search must be part of the same connected component. We then repeat the
     * search from any undiscovered vertex (if one exists) to define the next component,
     * and so on until all vertices have been discovered.
     *
     * @param graph
     * @return
     */
    public  int[] solve(UndirectedGraph graph){
        cc = new int[graph.adjList.length];

        for(int i=0; i<graph.adjList.length; ++i){
            if(graph.state[i] == UNDISCOVERED){
                currComp++;
                bfs(graph, i);
            }
        }

        return cc;
    }

    public static void main(String[] args){
        ConnectedComponents connectedComponents = new ConnectedComponents();

        UndirectedGraph g = new UndirectedGraph(5); // 5 vertices numbered from 0 to 4
        g.addEdge(1, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        int[] cc = connectedComponents.solve(g);

        System.out.println(Arrays.toString(cc));
    }
}
