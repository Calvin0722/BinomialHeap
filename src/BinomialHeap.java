import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BinomialHeap {
    private BinomialHeapNode head;
    private int maxIncentive = 0;

    BinomialHeap(BinomialHeapNode head) {
        this.head = head;
        BinomialHeapNode curr = head;
        while (curr != null) {
            if (curr.cent > maxIncentive) {
                maxIncentive = curr.cent;
            }
            curr = curr.sibling;
        }
    }

    public void union(BinomialHeap otherHeap) {
        if (otherHeap.maxIncentive > maxIncentive) {
            maxIncentive = otherHeap.maxIncentive;
        }

        this.head = this.merge(otherHeap);

        BinomialHeapNode prev_x = null;
        BinomialHeapNode x = this.head;
        BinomialHeapNode next_x = this.head.sibling;
        while (next_x != null) {
            if (x.degree == next_x.degree) {
                if (next_x.sibling != null && next_x.sibling.degree == next_x.degree) {
                    prev_x = x;
                    x = next_x;
                    next_x = next_x.sibling;
                } else {
                    if (x.cent > next_x.cent) {
                        x.sibling = next_x.sibling;
                        next_x.sibling = x.leftChild;
                        x.leftChild = next_x;
                        next_x.parent = x;
                    } else {
                        if (prev_x == null) {
                            this.head = next_x;
                        } else {
                            prev_x.sibling = next_x;
                        }
                        x.sibling = next_x.leftChild;
                        next_x.leftChild = x;
                        x.parent = next_x;
                    }
                }
            } else {
                prev_x = x;
                x = next_x;
                next_x = next_x.sibling;
            }
        }
    }

    private BinomialHeapNode merge (BinomialHeap otherHeap) {
        BinomialHeapNode initialNode, curr;
        BinomialHeapNode thisHeapNode = this.head;
        BinomialHeapNode otherHeapNode = otherHeap.head;

        if (thisHeapNode.degree > otherHeapNode.degree) {
            initialNode = thisHeapNode;
            curr = thisHeapNode;
            thisHeapNode = thisHeapNode.sibling;
        } else {
            initialNode = otherHeapNode;
            curr = otherHeapNode;
            otherHeapNode = otherHeapNode.sibling;
        }

        while (thisHeapNode != null && otherHeapNode != null) {
            if (thisHeapNode.degree > otherHeapNode.degree) {
                curr.sibling = thisHeapNode;
                thisHeapNode = thisHeapNode.sibling;
            } else {
                curr.sibling = otherHeapNode;
                otherHeapNode = otherHeapNode.sibling;
            }
        }
        if (thisHeapNode == null) {
            curr.sibling = otherHeapNode;
        } else {
            curr.sibling = thisHeapNode;
        }
        return initialNode;
    }

    public void insert(BinomialHeapNode node) {
        this.union(new BinomialHeap(node));
    }

    public int maxIncentive() {
        return maxIncentive;
    }

    @NotNull
    public BinomialHeapNode extractMax() {
        BinomialHeapNode curr = this.head;
        BinomialHeapNode maxNode;

        if (curr.cent == maxIncentive) {
            maxNode = curr;
            this.head = curr.sibling;
        } else {
            while (curr.sibling.cent != maxIncentive) {
                curr = curr.sibling;
            }
            maxNode = curr.sibling;
            curr.sibling = curr.sibling.sibling;
        }

        BinomialHeapNode child_previous = null;
        BinomialHeapNode child_curr = maxNode.leftChild;
        BinomialHeapNode child_next = child_curr.sibling;

        while (child_next != null) {
            child_curr.sibling = child_previous;
            child_next.sibling = child_curr;

            child_previous = child_curr;
            child_curr = child_next;
            child_next = child_next.sibling;
        }

        BinomialHeap otherHeap = new BinomialHeap(child_curr);

        this.union(otherHeap);

        return maxNode;
    }
}
