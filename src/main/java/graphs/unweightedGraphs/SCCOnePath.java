package graphs.unweightedGraphs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class SCCOnePath {

    static LinkedList<Integer> [] adjList;
    static Stack<Integer> stack = new Stack<Integer>();
    static int[] entry, low, scc;
    static int time = 0, sccIdx = 0;

    private static void dfs(int u){
        // initially set furthest reachable ancestor of a node to itself
        entry[u] = low[u] = ++time;
        stack.push(u);

        Iterator<Integer> iter = adjList[u].iterator();
        while (iter.hasNext()){
            int v = iter.next();
            if(entry[v] == 0) // v not discovered yet
                dfs(v);

            // if v not assigned yet to component, then update the furthest reachable ancestor of its
            // parent u with the furthest ancestor reachable from its descendant.
            if(scc[v] == 0)
                low[u] = Math.min(low[u], low[v]);
        }

        // if u is a furthest reachable ancestor to itself, then it is a root of a strongly connected
        // component. The other nodes in its scc will be above it in the stack including itself.
        if(entry[u] == low[u]){
            sccIdx++;
            while (!stack.isEmpty()){
                int v = stack.pop();
                scc[v] = sccIdx;
                if(v == u)
                    break;
            }
        }
    }

    public static void computeSCC(){
        for(int u = 0; u < adjList.length; ++u){
            if(entry[u] == 0) // undiscovered
                dfs(u);
        }

        System.out.println("The strongly connected components of each node in the given graph ");
        System.out.println(Arrays.toString(scc));
    }


    public static void main(String[] args) {
        // Graph 1
        int V = 5;
        adjList = new LinkedList[V];
        scc = new int[V];
        entry = new int[V];
        low = new int[V];
        for(int i=0; i<adjList.length; ++i)
            adjList[i] = new LinkedList<Integer>();

        adjList[1].add(0);
        adjList[0].add(2);
        adjList[2].add(1);
        adjList[0].add(3);
        adjList[3].add(4);

        computeSCC();

        System.out.println("\n =========================== \n");

        // Graph 2 (example in the book)
        V = 8;
        adjList = new LinkedList[V];
        scc = new int[V];
        entry = new int[V];
        low = new int[V];
        time = sccIdx = 0;
        for(int i=0; i<adjList.length; ++i)
            adjList[i] = new LinkedList<Integer>();

        adjList[0].add(1);
        adjList[1].add(2);
        adjList[1].add(3);
        adjList[1].add(4);
        adjList[2].add(0);
        adjList[3].add(0);
        adjList[3].add(5);
        adjList[3].add(7);
        adjList[4].add(5);
        adjList[5].add(6);
        adjList[6].add(4);
        adjList[7].add(5);

        computeSCC();
    }
}
