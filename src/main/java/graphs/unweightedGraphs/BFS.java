package graphs.unweightedGraphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private static void processEdge(int u, int v){
        System.out.println("Processed edge: " + u + "," + v);
    }

    // process vertex before processing its outgoing edges
    private static void processVertexEarly(int u){
        System.out.println("Processed vertex: " + u);
    }

    // process vertex after processing its outgoing edges
    private static void processVertexLate(int u){
    }

    public static void bfs(ArrayList<Integer>[] adjList, int source, boolean isDirected){
        final int UN_DISCOVERED = 0, DISCOVERED = 1, PROCCESSED = 2;
        Queue<Integer> queue = new LinkedList<Integer>();
        int[] state = new int[adjList.length];

        queue.add(source);
        state[source] = DISCOVERED;

        while(!queue.isEmpty()){
            int u = queue.remove();
            state[u] = PROCCESSED;

            processVertexEarly(u);

            for(int v : adjList[u]){
                // process the edge if it was not processed before
                // or the edge is directed (so its a new edge since u is being processed)
                if(state[v] != PROCCESSED || isDirected)
                    processEdge(u, v);

                if(state[v] == UN_DISCOVERED){
                    queue.add(v);
                    state[v] = DISCOVERED;
                }
            }

            processVertexLate(u);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer>[] adjList = new ArrayList[5];
        adjList[0] = new ArrayList<>();
        adjList[0].add(2);
        adjList[0].add(3);

        adjList[1] = new ArrayList<>();
        adjList[1].add(0);
        adjList[1].add(4);

        adjList[2] = new ArrayList<>();
        adjList[2].add(1);
        adjList[2].add(3);

        adjList[3] = new ArrayList<>();
        adjList[3].add(4);
        adjList[3].add(1);

        adjList[4] = new ArrayList<>();

        bfs(adjList, 0, true);
    }
}
