package graphs.unweightedGraphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that
 * for every directed edge uv, vertex u comes before v in the ordering. Topological Sorting for a
 * graph is not possible if the graph is not a DAG.
 *
 * The importance of topological sorting is that it gives us an ordering to process each vertex
 * before any of its successors.
 *
 * The top vertex on the stack always has no incoming edges from any vertex.
 */
public class TopologicalSorting {

    private static class DirectedGraph {
        List<Integer>[] adjList;
        int[] state, parent, entry, exit;
        int time;

        public DirectedGraph(int numVertecies) {
            this.adjList = new ArrayList[numVertecies];
            for (int i = 0; i < numVertecies; ++i)
                adjList[i] = new ArrayList<>();

            state = new int[adjList.length];
            parent = new int[adjList.length];
            entry = new int[adjList.length];
            exit = new int[adjList.length];
            time = 0;
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
        }
    }

    final int UN_DISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;

    Stack<Integer> stack = new Stack<Integer>();

    private void processVertexLate(int u) {
        stack.push(u);
    }

    private void processEdge(DirectedGraph graph, int u, int v) {
        if (graph.state[v] == DISCOVERED && graph.parent[u] != v) { // back edge
            throw new IllegalStateException("The graph is not DAG, cycle is found at edge ("
                    + u + ", " + v + ")");
        }
    }

    private void dfs(DirectedGraph graph, int u) {
        graph.state[u] = DISCOVERED;
        graph.entry[u] = ++graph.time;

        for (int v : graph.adjList[u]) {

            if (graph.state[v] == UN_DISCOVERED) {
                graph.parent[v] = u;
                dfs(graph, v);
            } else {
                processEdge(graph, u, v); // check for back edge
            }
        }

        processVertexLate(u);
        graph.exit[u] = ++graph.time;
        graph.state[u] = PROCCESSED;
    }

    public void run(DirectedGraph graph) {
        for (int i = 0; i < graph.adjList.length; ++i) {
            if (graph.state[i] == UN_DISCOVERED) {
                dfs(graph, i);
            }
        }

        System.out.println("Following is a Topological sort of the given graph");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        TopologicalSorting ts = new TopologicalSorting();

        DirectedGraph g = new DirectedGraph(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        ts.run(g);

        System.out.println("==========");

        // example in book
        DirectedGraph g2 = new DirectedGraph(7);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 3);
        g2.addEdge(1, 2);
        g2.addEdge(2, 4);
        g2.addEdge(2, 5);
        g2.addEdge(4, 3);
        g2.addEdge(5, 4);
        g2.addEdge(6, 5);
        g2.addEdge(6, 0);
        ts.run(g2);
    }
}
