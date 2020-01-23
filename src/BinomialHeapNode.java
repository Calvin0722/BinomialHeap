public class BinomialHeapNode {
    BinomialHeapNode leftChild;
    BinomialHeapNode sibling;
    BinomialHeapNode parent;
    int degree;
    int cent;

    BinomialHeapNode(int cent) {
        this.cent = cent;
    }
}
