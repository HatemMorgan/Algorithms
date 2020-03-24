package graphs.weightedGraphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * A spanning tree of a graph G = (V, E) is a subset of edges from E forming a
 * tree connecting all vertices of V . For edge-weighted graphs, we are particularly
 * interested in the minimum spanning tree—the spanning tree whose sum of edge
 * weights is as small as possible.
 *
 * Minimum spanning trees are the answer whenever we need to connect a set
 * of points (representing cities, homes, junctions, or other locations) by the smallest
 * amount of roadway, wire, or pipe. Any tree is the smallest possible connected graph
 * in terms of number of edges, while the minimum spanning tree is the smallest
 * connected graph in terms of edge weight.
 */
public class MinimumSpanningTree {
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

    private static void printMST(LinkedList<Edge>[] adjList, int[]parent){
        System.out.println("Edge \tWeight");
        for (int i = 0; i < adjList.length; i++) {
            if(parent[i] == -1 || parent[i] == i) // root of the tree
                continue;

            int weight = 0;
            for(Edge e : adjList[parent[i]]) {
                if (e.v == i) {
                    weight = e.weight;
                    break;
                }
            }

            System.out.println(parent[i] + " - " + i + "\t" + weight);
        }
    }

    // Prim’s algorithm is also a Greedy algorithm. It starts with an empty spanning tree.
    // The idea is to maintain two sets of vertices. The first set contains the vertices already
    // included in the MST, the other set contains the vertices not yet included. At every step,
    // it considers all the edges that connect the two sets, and picks the minimum weight edge from
    // these edges. After picking the edge, it moves the other endpoint of the edge to the set
    // containing MST.
    //
    // Time Complexity of the above program is O(V^2). If the input graph is represented using adjacency
    // list, then the time complexity of Prim’s algorithm can be reduced to O(E log V) with the help
    // of binary heap.
    private static  void primAlg(LinkedList<Edge>[] adjList, int start){
        int[] distance = new int[adjList.length];
        int[] parent = new int[adjList.length];
        boolean[] inTree = new boolean[adjList.length];

        for(int i=0; i<adjList.length; ++i)
            distance[i] = Integer.MAX_VALUE;

        distance[start] = Integer.MIN_VALUE;
        parent[start] = -1;
        int u = start;

        while (!inTree[u]){
            inTree[u] = true; // add not to the tree

            // for each destination node update its distance entry to minimum ingoing edge to it from u
            // update parent of node v to the parent with the minimum weight.
            for (Edge e : adjList[u]) {
                if (!inTree[e.v] && distance[e.v] > e.weight) {
                    distance[e.v] = e.weight;
                    parent[e.v] = u;
                }
            }

            // find node with minimum ingoing edge from nodes in the tree to add it to the tree.
            int dist = Integer.MAX_VALUE;
            for(int i=0; i<adjList.length; ++i){
                if(!inTree[i] && distance[i] < dist){
                    dist = distance[i];
                    u = i;
                }
            }
        }

        // the tree edges can be identified using the parent array.
        printMST(adjList, parent);
    }

    private static class UnionFind{
        int[] root, size;

        public UnionFind(int V){
            root = new int[V];
            size = new int[V];

            for(int i=0; i<V; ++i){
                root[i] = i;
                size[i] = 1;
            }
        }

        // find parent of subtree containing v
        private int find(int v){
            if(root[v] == v)
                return v;

            return find(root[v]);
        }

        public boolean unionSets(int s1, int s2){
            int root1 = find(s1);
            int root2 = find(s2);

            if(root1 == root2)
                return false;


            // Merge step by making the smaller tree the subtree of the larger tree.
            if(size[root1] >= size[root2]){
                size[root1]+= size[root2];
                root[root2] = root1;
            }else{
                size[root2] += size[root1];
                root[root1] = root2;
            }

            return true;
        }
    }

    /**
     * Kruskal’s algorithm builds up connected components of vertices, culminating in
     * the minimum spanning tree. Initially, each vertex forms its own separate component
     * in the tree-to-be. The algorithm repeatedly considers the lightest remaining edge
     * and tests whether its two endpoints lie within the same connected component. If
     * so, this edge will be discarded, because adding it would create a cycle in the tree-
     * to-be. If the endpoints are in different components, we insert the edge and merge
     * the two components into one. Since each connected component is always a tree, we
     * need never explicitly test for cycles.
     *
     * With Union-Find data structure, Kruskal’s algorithm runs in O(max( ElogE, ElogV)) time, which is
     * faster than Prim’s for sparse graphs.
     */
    public static void kruskalMSTAlg(LinkedList<Edge>[] adjList){
        UnionFind unionFindDS = new UnionFind(adjList.length);
        ArrayList<Edge> res = new ArrayList<>();// stores tree edges

        Comparator<Edge> cmp = new Comparator<Edge>(){
          public int compare(Edge e1, Edge e2){
              return e1.weight - e2.weight;
          }
        };

        ArrayList<Edge> edges = new ArrayList<>();
        for(int i=0; i<adjList.length; ++i)
            for(Edge e: adjList[i])
                edges.add(e);

        // Time complexity O(E log E) and space complexity O(log E) (quick sort)
        Collections.sort(edges, cmp);

        // Time complexity O(E log V) and space complexity O(V)
        for(Edge e: edges) {
            boolean didUnion = unionFindDS.unionSets(e.u, e.v);
            if(didUnion)
                res.add(e);
        }

        System.out.println("Edge \tWeight");
        for(Edge e : res)
            System.out.println(e.u + " - " + e.v + "\t" + e.weight);
    }

    public static void main(String[] args) {
        /* The graph

            (0)--(1)--(2)
            | / \ |
            6| 8/ \5 |7
            | /     \ |
            (3)-------(4)

         */

        LinkedList<Edge>[] adjList = new LinkedList[5];
        for(int i=0; i<adjList.length; ++i)
            adjList[i] = new LinkedList<Edge>();

        adjList[0].add(new Edge(0,1, 2));
        adjList[0].add(new Edge(0,3, 6));
        adjList[1].add(new Edge(1,0, 2));
        adjList[1].add(new Edge(1,2, 3));
        adjList[1].add(new Edge(1,3, 8));
        adjList[1].add(new Edge(1,4, 5));
        adjList[2].add(new Edge(2,1, 3));
        adjList[2].add(new Edge(2,4, 7));
        adjList[3].add(new Edge(3,0, 6));
        adjList[3].add(new Edge(3,1, 8));
        adjList[3].add(new Edge(3,4, 9));
        adjList[4].add(new Edge(4,1, 5));
        adjList[4].add(new Edge(4,2, 7));
        adjList[4].add(new Edge(4,3, 9));

        System.out.println("Prim's algorithm running");
        primAlg(adjList, 0);

        System.out.println("\n =============== \n ");
        System.out.println("Kruskal's algorithm running");
        kruskalMSTAlg(adjList);


        /* Example 2:
                 10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
                4        */

        LinkedList<Edge>[] adjList2 = new LinkedList[4];
        for(int i=0; i<adjList2.length; ++i)
            adjList2[i] = new LinkedList<Edge>();

        // add edge 0-1
        adjList2[0].add(new Edge(0, 1, 10));
        adjList2[1].add(new Edge(1, 0, 10));

        // add edge 0-2
        adjList2[0].add(new Edge(0, 2, 6));
        adjList2[2].add(new Edge(2, 0, 6));

        // add edge 0-3
        adjList2[0].add(new Edge(0, 3, 5));
        adjList2[3].add(new Edge(3, 0, 5));

        // add edge 1-3
        adjList2[1].add(new Edge(1, 3, 15));
        adjList2[3].add(new Edge(3, 1, 15));

        // add edge 2-3
        adjList2[2].add(new Edge(2, 3, 4));
        adjList2[3].add(new Edge(3, 2, 4));

        System.out.println("\n ===== GRAPH 2 =====  \n");
        System.out.println("Prim's algorithm running");
        primAlg(adjList2, 0);

        System.out.println("\n =============== \n ");
        System.out.println("Kruskal's algorithm running");
        kruskalMSTAlg(adjList2);

    }
}
