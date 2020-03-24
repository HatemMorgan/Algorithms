package graphs.unweightedGraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * In this algorithm we perform DFS two times to partition the directed graph into a set of strongly
 * connected components.
 *
 * In the book, there is another more complicated implementation that perform a single dfs to the graph
 * and it doesn't need to reverse the graph. It keeps track of the furthest ancestor reachable from a
 * vertex by a back edge.
 */
public class SCCTwoPath {

    private static class DirectedGraph {
        List<Integer>[] adjList;
        int[] state, entry, low;
        int time = 0;

        public DirectedGraph(int numVertecies) {
            this.adjList = new ArrayList[numVertecies];
            for (int i = 0; i < numVertecies; ++i)
                adjList[i] = new ArrayList<>();

            state = new int[adjList.length];
            entry = new int[adjList.length];
            low = new int[adjList.length];
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
        }

        public DirectedGraph getTranspose(){
            DirectedGraph graph = new DirectedGraph(this.adjList.length);

            for(int u = 0; u<this.adjList.length; ++u){
                for(int v : this.adjList[u])
                    graph.adjList[v].add(u);
            }

            return graph;
        }
    }

    final int UN_DISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;
    Stack<Integer> stack = new Stack<Integer>();
    int currSCCNum = 0;

    private void firstDFSPath(DirectedGraph graph, int u){
        graph.state[u] = DISCOVERED;

        for(int v: graph.adjList[u]){
            if(graph.state[v] == UN_DISCOVERED)
                firstDFSPath(graph, v);
        }

        graph.state[u] = PROCCESSED;
        stack.push(u);
    }


    private void secondDFSPath(DirectedGraph graph, int u, int[] scc){
        graph.state[u] = DISCOVERED;
        scc[u] = currSCCNum;

        for(int v: graph.adjList[u]){
            if(graph.state[v] == UN_DISCOVERED)
                secondDFSPath(graph, v, scc);
        }
    }

    /**
     * The graph is strongly connected iff all vertices in G can
     *      (1) reach v and
     *      (2) are reachable from v.
     * @param graph
     * @return
     */
    public int[] run(DirectedGraph graph){

        // We find all vertices reachable from u
        for(int u = 0; u < graph.adjList.length; ++u){
            if(graph.state[u] == UN_DISCOVERED)
                firstDFSPath(graph, u);
        }

        // By doing a DFS from u in reversed graph, we find all vertices with paths to u in G.
        DirectedGraph transposedGraph = graph.getTranspose();
        int[] scc = new int[graph.adjList.length];

        while (!stack.isEmpty()){
            int u = stack.pop();
            if(transposedGraph.state[u] == UN_DISCOVERED) {
                currSCCNum++;
                secondDFSPath(transposedGraph, u, scc);
            }
        }

        return scc;
    }


    public static void main(String[] args) {
        SCCTwoPath sccTwoPath = new SCCTwoPath();

        DirectedGraph g = new DirectedGraph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        System.out.println("The strongly connected components of each node in the given graph ");
        int[] res = sccTwoPath.run(g);
        System.out.println(Arrays.toString(res));

        // example in the book

        DirectedGraph g2 = new DirectedGraph(8);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(1, 3);
        g2.addEdge(1, 4);
        g2.addEdge(2, 0);
        g2.addEdge(3, 0);
        g2.addEdge(3, 5);
        g2.addEdge(3, 7);
        g2.addEdge(4, 5);
        g2.addEdge(5, 6);
        g2.addEdge(6, 4);
        g2.addEdge(7, 5);

        System.out.println(" =====   ");
        System.out.println("The strongly connected components of each node in the given graph ");
        sccTwoPath.currSCCNum = 0;
        res = sccTwoPath.run(g2);
        System.out.println(Arrays.toString(res));

    }
}
