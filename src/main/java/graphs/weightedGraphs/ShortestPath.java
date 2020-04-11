package graphs.weightedGraphs;

import java.lang.reflect.Array;
import java.util.*;

public class ShortestPath {
    static class Edge{
        int u;
        int v; // destination node id
        int weight;

        public Edge(int u, int v, int weight){
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "u=" + u +
                    ", v=" + v +
                    ", weight=" + weight +
                    '}';
        }
    }

    // A utility function to print the constructed distance array
    private static void printSolution(int dist[]) {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < dist.length; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }


    private static String getPath(int[] parent, int v){
        if(parent[v] == -1)
            return v + "";

        return getPath(parent, parent[v]) + "->" + v;
    }

    /**
     * Dijkstra’s algorithm is the method of choice for finding shortest paths in an edge-
     * and/or vertex-weighted graph. Given a particular start vertex s, it finds the shortest
     * path from s to every other vertex in the graph, including your desired destination t.
     *
     * The basic idea is very similar to Prim’s algorithm. In each iteration, we add
     * exactly one vertex to the tree of vertices for which we know the shortest path from
     * s. As in Prim’s, we keep track of the best path seen to date for all vertices outside
     * the tree, and insert them in order of increasing cost.
     *
     * The algorithm  finds the shortest path from s to all other vertices. This defines a
     * shortest path spanning tree rooted in s.
     *
     * Time Complexity of the implementation is O(V^2). If the input graph is represented using
     * adjacency list, it can be reduced to O(E log V) with the help of binary heap.
     *
     * Space complexity is O(V).
     *
     * Dijkstra’s algorithm doesn’t work for graphs with negative weight edges. For graphs with
     * negative weight edges, Bellman–Ford algorithm can be used. Most applications do not feature
     * negative-weight edges, making this type of problems academic.
     */
    public static int[] dijkstra(LinkedList<Edge>[] adjList, int start){
        final int INF = (int)1e6;
        int[] distance = new int[adjList.length];
        int[] parent = new int[adjList.length]; // can be used to get paths
        boolean[] inTree = new boolean[adjList.length];

        for(int i=0; i<adjList.length; ++i){
            distance[i] = INF;
            parent[i] = -1;
        }

        distance[start] = 0;
        int u = start;

        while(!inTree[u]){
            inTree[u] = true;

            for(Edge e : adjList[u]){
                if(distance[e.v] > distance[u] + e.weight){
                    distance[e.v] = distance[u] + e.weight;
                    parent[e.v] = u;
                }
            }

            // we want to include the closest outside vertex (in shortest-path distance) to s.
            int dist = Integer.MAX_VALUE;
            for(int i=0; i<adjList.length; ++i) {
                if (!inTree[i] && dist > distance[i]) {
                    dist = distance[i];
                    u = i;
                }
            }
        }

        printSolution(distance);
        return parent;
    }

    private static class Node implements Comparable<Node>{
        int id, cost;
        public Node(int id, int cost){
            this.id = id;
            this.cost = cost;
        }

        public int compareTo(Node n){
            return this.cost - n.cost;
        }
    }

    /**
     * Time Complexity: E log E
     */
    private static int[] optimizedDijkstraALg(LinkedList<Edge>[] adjList, int start){
        int V = adjList.length;
        int[] distance = new int[V];
        int[] parent = new int[V];
        boolean[] inTree = new boolean[V];

        PriorityQueue<Node> pq = new PriorityQueue<>();

        final int INF = (int) 1e7;
        Arrays.fill(distance, INF);

        distance[start] = 0;
        parent[start] = -1;

        pq.add(new Node(start, 0));

        while(!pq.isEmpty()){
            Node u = pq.poll(); // poll node with lowest cost
            if(inTree[u.id]) // Node is visited, skip
                continue;

            inTree[u.id] = true;

            for(Edge e: adjList[u.id]){
                if(!inTree[e.v] && distance[e.v] > distance[e.u] + e.weight){
                    parent[e.v] = e.u;
                    distance[e.v] = distance[e.u] + e.weight;
                    pq.add(new Node(e.v, distance[e.v]));
               }
            }
        }

        printSolution(distance);
        return parent;
    }

    /**
     * Floyd’s algorithm computes the shortest path between all pairs of vertices in a given graph.
     * It works correctly unless there are negative cost cycles.
     *
     * Floyd’s algorithm is best employed on an adjacency matrix data structure, which is no
     * extravagance since we must store all n2 pairwise distances anyway.
     *
     * The Floyd all-pairs shortest path algorithm runs in O(n^3) time, which is asymptotically no
     * better than n calls to Dijkstra’s algorithm. However, the loops are so tight and the program
     * so short that it runs better in practice.
     *
     * It is notable as one of the rare graph algorithms that work better on adjacency matrices
     * than adjacency lists.
     *
     * Paths can be recovered if we retain a parent matrix P of our choice of the last intermediate
     * vertex used for each vertex pair (x, y). Say this value is k. The shortest path from
     * x to y is the concatenation of the shortest path from x to k with the shortest
     * path from k to y, which can be reconstructed recursively given the matrix P
     */
    private static void floydAlg(int[][] adjMatrix){
        final int INF = (int) 1e6;
        int V = adjMatrix.length;

        //  initialize each non-edge to MAXINT. This way we can both test whether it is present and
        // automatically ignore it in shortest-path computations, since only real edges will be used
        for(int i=0; i<V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (i != j && adjMatrix[i][j] == 0)
                    adjMatrix[i][j] = INF;
            }
        }

        int distThroughK;

        // At each iteration, we allow a richer set of possible shortest paths by adding a
        //new vertex as a possible intermediary. Allowing the kth vertex as a stop helps only
        //if there is a short path that goes through k.
        for(int k=0; k<V; ++k){
            for(int i=0; i<V; ++i){
                for(int j=0; j<V; ++j){
                    distThroughK = adjMatrix[i][k] + adjMatrix[k][j];
                    if(distThroughK < adjMatrix[i][j])
                        adjMatrix[i][j] = distThroughK;
                }
            }
        }

        for(int i=0; i<V; ++i) {
            System.out.println("Source vertex is " + i);
            printSolution(adjMatrix[i]);
        }
    }

    public static void main(String[] args){
        int adjMatrix[][] = new int[][] {   { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                                        { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                                        { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                                        { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                                        { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                                        { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                                        { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
                                        { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                                        { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };

        LinkedList<Edge>[] adjList = new LinkedList[adjMatrix.length];
        for(int i=0; i<adjList.length; ++i)
            adjList[i] = new LinkedList<Edge>();

        for(int i=0; i<adjMatrix.length; ++i)
            for(int j=0; j<adjMatrix[0].length; ++j)
                if(adjMatrix[i][j] > 0)
                    adjList[i].add(new Edge(i, j, adjMatrix[i][j]));

        System.out.println("\n =====  Dijskra's Algorithm ====== ");

        int[] parent = dijkstra(adjList, 0);
        System.out.println("\npath from 0 to 6: " + getPath(parent, 6));
        System.out.println("\npath from 0 to 8: " + getPath(parent, 8));

        System.out.println("\n ===== Optimized Dijskra's Algorithm ====== ");

        parent = optimizedDijkstraALg(adjList, 0);

        System.out.println("\npath from 0 to 6: " + getPath(parent, 6));
        System.out.println("\npath from 0 to 8: " + getPath(parent, 8));

        System.out.println("\n =====  Floyd's Algorithm ====== ");

//        floydAlg(adjMatrix);

    }

}
