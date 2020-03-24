package dataStructures.trees;

class Node<T extends Comparable> {
    T key;
    Node left, right, parent;

    Node(T key, Node left, Node right, Node parent) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public Node deleteClone() {
        return new Node(this.key, null, null, null);
    }

    public String toString() {
        return "Node.key = " + this.key;
    }
}

public class UnbalancedBinarySearchTree<T extends Comparable> {
    Node root = null;

    public void insert(T key) {
        insert(root, key, null);
    }

    private void insert(Node currNode, T key, Node parent) {
        if (currNode == null) {
            Node newNode = new Node(key, null, null, parent);
            if (parent == null) {
                root = newNode;
            } else {
                if (key.compareTo(parent.key) < 0)
                    parent.left = newNode;
                else
                    parent.right = newNode;
            }
            return;
        }

        if (key.compareTo(currNode.key) < 0)
            insert(currNode.left, key, currNode);
        else
            insert(currNode.right, key, currNode);
    }

    public Node search(T key) {
        return search(root, key);
    }

    private Node search(Node currNode, T key) {
        if (currNode == null)
            return null;

        if (key.compareTo(currNode.key) == 0)
            return currNode;

        if (key.compareTo(currNode.key) < 0)
            return search(currNode.left, key); // go left
        else
            return search(currNode.right, key); // go right
    }

    public Node maximum() {
        Node currNode = root;

        while (currNode.right != null)
            currNode = currNode.right;

        return currNode;
    }

    public Node minimum() {
        return minimum(root);
    }

    private Node minimum(Node currNode) {

        while (currNode.left != null)
            currNode = currNode.left;

        return currNode;
    }

    public Node delete(T key) {
        Node toBeDeleted = search(key);

        if (toBeDeleted.right == null && toBeDeleted.left == null) { // leaf node
            if (toBeDeleted.parent.left == toBeDeleted)
                toBeDeleted.parent.left = null;
            else
                toBeDeleted.parent.right = null;

            return toBeDeleted.deleteClone();
        }

        // deleted node has one child on the right
        if (toBeDeleted.left == null) {
            if (toBeDeleted.parent.left == toBeDeleted)
                toBeDeleted.parent.left = toBeDeleted.right;
            else
                toBeDeleted.parent.right = toBeDeleted.right;

            return toBeDeleted.deleteClone();
        }

        // deleted node has one child on the left
        if (toBeDeleted.right == null) {
            if (toBeDeleted.parent.left == toBeDeleted)
                toBeDeleted.parent.left = toBeDeleted.left;
            else
                toBeDeleted.parent.right = toBeDeleted.left;

            return toBeDeleted.deleteClone();
        }

        // deleted node has two child. Thus, get the minimum element in its subtree.
        Node leftMostNode = minimum(toBeDeleted.right);
        leftMostNode.parent.left = null; // remove left most node from tree
        // replace toBeDeleted with the leftMostNode
        leftMostNode.right = toBeDeleted.right;
        leftMostNode.left = toBeDeleted.left;
        if (toBeDeleted.parent.left == toBeDeleted)
            toBeDeleted.parent.left = leftMostNode;
        else
            toBeDeleted.parent.right = leftMostNode;

        return toBeDeleted.deleteClone();
    }

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node currNode) {
        if (currNode != null) {
            printTree(currNode.left);
            System.out.println(currNode.key);
            printTree(currNode.right);
        }
    }


    public static void main(String[] args) {
        UnbalancedBinarySearchTree<Integer> tree = new UnbalancedBinarySearchTree<Integer>();
        tree.insert(2);
        tree.insert(1);
        tree.insert(7);
        tree.insert(4);
        tree.insert(8);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);

        System.out.println(" =========== tree traverse Test =========== ");
        tree.printTree();

        System.out.println(" =========== Search Test =========== ");
        System.out.println(tree.search(3));
        System.out.println(tree.search(8));
        System.out.println(tree.search(10));

        System.out.println(" =========== Maximum Test =========== ");
        System.out.println(tree.maximum());

        System.out.println(" =========== Minimum Test =========== ");
        System.out.println(tree.minimum());

        System.out.println(" =========== Delete Test First case with no children =========== ");
        System.out.println(tree.delete(3));
        System.out.println(tree.search(3));
        tree.printTree();

        // construct tree again to preserve same insertion order because the tree is unblanced
        tree = new UnbalancedBinarySearchTree<Integer>();
        tree.insert(2);
        tree.insert(1);
        tree.insert(7);
        tree.insert(4);
        tree.insert(8);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);


        System.out.println(" =========== Delete Test First case with 1 child =========== ");
        System.out.println(tree.delete(6));
        System.out.println(tree.search(6));
        tree.printTree();

        // construct tree again to preserve same insertion order because the tree is unblanced
        tree = new UnbalancedBinarySearchTree<Integer>();
        tree.insert(2);
        tree.insert(1);
        tree.insert(7);
        tree.insert(4);
        tree.insert(8);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);

        System.out.println(" =========== Delete Test First case with two children =========== ");
        System.out.println(tree.delete(4));
        System.out.println(tree.search(4));
        tree.printTree();

        String s;
    }
}
