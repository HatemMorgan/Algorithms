package graphs.weightedGraphs;

import java.util.LinkedList;
import java.util.Queue;


/**
 * A flow network G = (V, E) is a directed graph in which each edge (u, v) in E has a
 * nonnegative capacity c(u,v) >= 0.
 *
 * This implementation requires that if E contains an edge (u, v), then there is no edge (v, u)
 * in the reverse direction. When having anti-parallel edges (u,v) and (v, u), we will choose
 * one of the two antiparallel edges, in this case (u, v), and split it by adding a new
 * vertex k and replacing edge (u, v) with the pair of edges (u, k) and (k, v).
 * We also set the capacity of both new edges to the capacity of the original edge.
 * The resulting network satisfies the property that if an edge is in the network, the
 * reverse edge is not.
 *
 * The graph is connected and each vertex other than sink has at least one entering edge.
 *
 * The capacity constraint simply says that the flow from one vertex to another must be
 * non-negative and must not exceed the given capacity. The flow-conservation property says
 * that the total flow into a vertex other than the source or sink must equal the total flow
 * out of that vertex—informally, “flow in equals flow out.”
 *
 * The following implementation of Ford-Fulkerson method is Edmonds-Karp algorithm. the shortest
 * path between source and sink found by BFS increases monotonically in the
 * residual graph, which bounds the length of one iteration of Edmonds-Karp to O(E). An edge is
 * defined as critical on an augmenting path if and only if the residual capacity of the edge equals
 * the residual capacity of the path (minimum capacity edge). In other words, the critical edge's
 * capacity will be filled by this augmenting path. Once we have augmented this path,
 * the edge in question will disappear from the residual network. Each of the |E| edges can
 * becomes critical at most |V|/2 times. Thus, Edmonds-Karp runs each iteration in O(|E|) time
 * and that there are at most |V|*|E| iterations, Edmonds-Karp is bounded by O(|V| * |E^2|).
 *
 * Reference: https://brilliant.org/wiki/edmonds-karp-algorithm/
 *
 */
public class MaxFlow {

    private class ResidualEdge{
        // flow in edge = capacity - residual
        int v, capacity, residual;

        public ResidualEdge(int v, int capacity, int residual){
            this.v = v;
            this.capacity = capacity;
            this.residual = residual;
        }
    }

    private LinkedList<ResidualEdge>[] buildResidualGraph(int[][] adjMatrix){
        int V = adjMatrix.length;
        LinkedList<ResidualEdge>[] resdAdjList = new LinkedList[V];

        for(int u=0; u<V; ++u)
            resdAdjList[u] = new LinkedList<ResidualEdge>();

        for(int u=0; u<V; ++u){
            for(int v=0; v<V; ++v){
                if(adjMatrix[u][v] > 0){
                    // forward (flow) edge. residual = capacity
                    resdAdjList[u].add(new ResidualEdge(v, adjMatrix[u][v], adjMatrix[u][v]));
                    // backward (reverse) edge. residual = 0
                    resdAdjList[v].add(new ResidualEdge(u, 0, 0));
                }
            }
        }

        return resdAdjList;
    }

    /**
     * breadth-first search is used to find the shortest paths from source to sink
     * during the intermediate stages of the program.
     */
    private int[] bfs(LinkedList<ResidualEdge>[] adjList, int s, int t){
        int V = adjList.length;
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];

        Queue<Integer> queue = new LinkedList<Integer>();

        queue.add(s);
        parent[s] = -1;
        visited[s] = true;
        while(!queue.isEmpty() && !visited[t]){
            int u = queue.poll();

            for(ResidualEdge edge: adjList[u]){
                if(!visited[edge.v] && edge.residual > 0){
                    queue.add(edge.v);
                    parent[edge.v] = u;
                    visited[edge.v] = true;
                }
            }
        }

        // return parent array if there exist a path from source s to sink t.
        return (visited[t])? parent: null;
    }

    private ResidualEdge getEdge(LinkedList<ResidualEdge>[] adjList, int u, int v){
        for(ResidualEdge edge : adjList[u])
            if(edge.v == v)
                return edge;

        return null;
    }

    /**
     * Worst case complexity is O(E), as we will visit all edges in the graph in the worst case
     */
    private int updateResidualGraph(LinkedList<ResidualEdge>[] adjList, int s, int t, int[] parent){
        // find minimum flow in the path
        int u = t;
        int volume = Integer.MAX_VALUE;
        while(parent[u] != -1){ // traverse path from sink t to source s
            ResidualEdge edge = getEdge(adjList, parent[u], u);
            volume = Math.min(volume, edge.residual);
            u = parent[u];
        }

        // update Residual graph with new pathedFlow
        u = t;
        while(parent[u] != -1){
            // forward (initial) edge
            ResidualEdge forwardEdge = getEdge(adjList, parent[u], u);
            forwardEdge.residual -= volume;

            // backward (reverse) edge
            ResidualEdge backwardEdge = getEdge(adjList, u, parent[u]);
            backwardEdge.residual += volume;


            u = parent[u];
        }

        return volume;
    }

    /**
     * In a flow network, an s-t cut is a cut that requires the source s and the sink t to be in
     * different subsets, and it consists of edges going from the source’s side to the sink’s side.
     * The capacity of an s-t cut is defined by the sum of the capacity of each edge in the cut-set
     * which is equal to maximum possible flow from source to sink.
     *
     * We perform a BFS traversal of the graph from the source vertex to find vertices reachable from s.
     * The cut edges will be the forward edges with that has zero residual.
     */
    private void minCut(LinkedList<ResidualEdge>[] resdAdjList, int s){
        boolean[] visited = new boolean[resdAdjList.length];

        Queue<Integer> queue = new LinkedList<>();
        visited[s] = true;
        queue.add(s);
        while(!queue.isEmpty()){
            int u = queue.poll();

            for(ResidualEdge e : resdAdjList[u]){
                if(!visited[e.v] && e.residual > 0){
                    visited[e.v] = true;
                    queue.add(e.v);
                }
            }
        }
        // Print all edges that are from a reachable vertex (in s subset) to non-reachable vertex
        // (t subset) in the original graph

        System.out.println("Min cut edge are: ");
        for(int u=0; u<resdAdjList.length; ++u){
            for(int v=0; v<resdAdjList.length; ++v){
                if(visited[u] && !visited[v]){
                    ResidualEdge edge = getEdge(resdAdjList, u, v);
                    if(edge != null && edge.capacity > 0) {
                        System.out.println(u + " -> " + v + " flow = " + edge.capacity);
                    }
                }
            }
        }
    }

    /**
     * Ford Fulkerson Algorithm Protocol for flow networks
     *
     * Ford-Fulkerson Algorithm
     * The following is simple idea of Ford-Fulkerson algorithm:
     * 1) Start with initial flow as 0.
     * 2) While there is a augmenting path from source to sink.
     *            Add this path-flow to flow and update residual graph.
     * 3) Return flow.
     */
    public int compute( int[][] adjMatrix, int s, int t){
        LinkedList<ResidualEdge>[] resdAdjList = buildResidualGraph(adjMatrix);
        int maxFlow = 0; // not flow initially
        int volume;
        do {
            // find the shortest augmenting path from source s to sink t. O(E)
            int[] parent = bfs(resdAdjList, s, t);
            // if there exist a path then update residual graph and get new added flow volume. O(E)
            volume = (parent == null)? 0 : updateResidualGraph(resdAdjList, s, t, parent);
            maxFlow += volume;

        }while (volume > 0);

        minCut(resdAdjList, s);

        return maxFlow;
    }

    public static void main(String[] args) {
        // Let us create a graph shown in the above example
        int adjMatrix[][] = new int[][]{{0, 16, 13, 0, 0, 0},
                                    {0, 0, 10, 12, 0, 0},
                                    {0, 4, 0, 0, 14, 0},
                                    {0, 0, 9, 0, 0, 20},
                                    {0, 0, 0, 7, 0, 4},
                                    {0, 0, 0, 0, 0, 0}};

        MaxFlow maxFlow = new MaxFlow();
        int flow = maxFlow.compute(adjMatrix, 0, 5);
        System.out.println("The maximum possible flow is " + flow);

        System.out.println("\n=========Second Graph==============");

        adjMatrix = new int[][]{{0, 2, 1, 0},
                                {0, 0, 3, 1},
                                {0, 0, 0, 2},
                                {0, 0, 0, 0}};

        System.out.println("The maximum possible flow is " + maxFlow.compute(adjMatrix, 0, 3));

    }
}
