package graphs.unweightedGraphs;

import java.util.ArrayList;
import java.util.List;

public class ArticulationVertices {

    private static class UndirectedGraph{
        List<Integer>[] adjList;
        int [] state, parent, entry, exit;
        int[] reachableAncestor; // earliest (the farthest) reachable ancestor
        int[] outDegree;
        int time;
        public UndirectedGraph(int numVertecies){
            this.adjList = new ArrayList[numVertecies];
            for(int i=0; i<numVertecies; ++i)
                adjList[i] = new ArrayList<>();

            state = new int[adjList.length];
            parent = new int[adjList.length];
            entry = new int[adjList.length];
            exit = new int[adjList.length];
            reachableAncestor = new int[adjList.length];
            outDegree = new int[adjList.length];
            time = 0;

        }

        public void addEdge(int u, int v){
            adjList[u].add(v);
            adjList[v].add(u);
        }
    }

    final int UN_DISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;
    boolean[] isArticulationVertex;

    private int classifyEdge(UndirectedGraph g, int u, int v){
        final int TREE_EDGE = 0, BACK_EDGE = 1, FORWARD_EDGE = 2, CROSS_EDGE = 3;

        if(g.state[v] == UN_DISCOVERED) return TREE_EDGE;
        if(g.state[v] == DISCOVERED && g.state[v] != PROCCESSED) return BACK_EDGE;
        if(g.state[v] == PROCCESSED && g.entry[v] > g.entry[u]) return FORWARD_EDGE;
        if(g.state[v] == PROCCESSED && g.entry[v] < g.entry[u]) return CROSS_EDGE;

        throw new IllegalArgumentException("Unclassified Edge (" + u  + ", " + v + ")");
    }


    private void dfs(UndirectedGraph g, int u){
        g.state[u] = DISCOVERED;
        g.time++;
        g.entry[u] = g.time;
        g.reachableAncestor[u] = u;

        for(int v : g.adjList[u]){
            if(g.state[v] == UN_DISCOVERED){ // tree edge
                g.parent[v] = u;
                g.outDegree[u]++;
                dfs(g, v); // traverse subtree rooted at v

                // Check if the subtree rooted with v has a connection to one of the ancestors of u
                if(g.entry[g.reachableAncestor[v]] < g.entry[g.reachableAncestor[u]])
                    g.reachableAncestor[u] = g.reachableAncestor[v];

                // u is an articulation point in following cases

                // 1) u is the DFS root and has two or more children
                if(g.parent[u] == -1 && g.outDegree[u] > 1)
                    isArticulationVertex[u] = true;

                // 2) u is not a root and reachable ancestor of its child v is:
                //        1- a descendant of parent[u] or
                //        2- parent[u] itself (equality in the condition)
                if(g.parent[u] != -1 && g.entry[u] <= g.entry[g.reachableAncestor[v]])
                    isArticulationVertex[u] = true;

            }else{
                if(g.state[v] != PROCCESSED && g.parent[u] != v) { // back edge
                    // update furthest ancestor of u if v is a further ancestor.
                    if(g.entry[v] < g.entry[g.reachableAncestor[u]])
                        g.reachableAncestor[u] = v;
                }
            }
        }

        g.time++;
        g.exit[u] = g.time;
        g.state[u] = PROCCESSED;
    }

    public void solve(UndirectedGraph g){
        isArticulationVertex = new boolean[g.adjList.length];

        for(int i=0; i<g.adjList.length; ++i){
            if(g.state[i] == UN_DISCOVERED) {
                g.parent[i] = -1;
                dfs(g, i);
            }
        }

        for(int i=0; i<isArticulationVertex.length; ++i)
            if(isArticulationVertex[i])
                System.out.print(i + " ");
    }

    public static void main(String[] args) {
        ArticulationVertices av = new ArticulationVertices();

        System.out.println("Articulation points in first graph ");
        UndirectedGraph g1 = new UndirectedGraph(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        av.solve(g1);
        System.out.println();

        System.out.println("Articulation points in Second graph");
        UndirectedGraph g2 = new UndirectedGraph(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        av.solve(g2);
        System.out.println();

        System.out.println("Articulation points in Third graph ");
        UndirectedGraph g3 = new UndirectedGraph(7);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        av.solve(g3);
    }
}
