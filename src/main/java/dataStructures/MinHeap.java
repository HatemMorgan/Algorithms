package dataStructures;

public class MinHeap {

    private int[] arr;
    private int n;

    public MinHeap(int size){
        arr = new int[size];
        n = -1;
    }

    /**
     * Make use of the heapify procedure that was designed to handle, restoring the heap order of arbitrary
     * root element sitting on top of two sub-heaps.
     *
     * Heapify takes time proportional to the height of the heaps it is merging. Most of these heaps
     * are extremely small. In a full binary tree on n nodes, there are n/2 nodes that are leaves
     * (i.e. , height 0), n/4 nodes that are height 1, n/8 nodes that are height 2, and so on.
     *
     * Thus, the time complexity quickly converges to linear, we can construct heaps in
     * linear time instead of O(n log n). Proof in page 128 in the "Algorithm design manual" book
     * @param arr
     */
    public MinHeap(int[] arr){
        this.arr = arr;
        n = arr.length - 1;

        for(int i = n; i>=0; --i)
            heapify(i);
    }

    private void swap(int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Insetion takes O(logn) time complexity and O(1) space complexity
     */
    public void insert(int x){
        if(n >= arr.length - 1)
            throw new RuntimeException("Heap reches maximum capacity");

        n++;
        arr[n] = x;
        int i = n;

        // O(logn) time complexity
        while (i != 0){
            if(arr[i] < arr[i >> 1]) // check that ith child less than its parent
                swap(i, i >> 1);
            else
                break;

            i >>= 1;
        }
    }

    /**
     * O(logn) time complexity and O(1) space complexity
     */
    private void heapify(int parentIdx){
        int leftChildIdx = (parentIdx << 1) + 1;
        int rightChildIdx = (parentIdx << 1) + 2;

        int minIdx = (leftChildIdx <= n && arr[leftChildIdx] <= arr[parentIdx])? leftChildIdx : parentIdx;
        minIdx = (rightChildIdx <= n && arr[rightChildIdx] <= arr[minIdx])? rightChildIdx : minIdx;

        if(minIdx != parentIdx) {
            swap(parentIdx, minIdx);
            heapify(minIdx);
        }
    }

    /**
     * O(logn) time complexity and O(1) space complexity
     */
    public int getMin(){
        int res = arr[0];
        swap(0, n);
        n--;
        heapify(0); // O(logn) time complexity

        return res;
    }


    public static void main(String[] args){
        int[] arr = new int[] {1,5,2,1, 944, 24, 12, 221, 0, 20, 83, 7};
        MinHeap heap = new MinHeap(arr.length);

        for(int x : arr)
            heap.insert(x);

        for(int i=0; i<arr.length; ++i)
            System.out.println(heap.getMin());
    }

}
