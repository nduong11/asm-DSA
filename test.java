
public class MaxHeap {

    private List<Integer> heap = new ArrayList<>();

    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    public int extractMax() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        int max = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return max;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index) <= heap.get(parent)) {
                break;
            }
            Collections.swap(heap, index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = index * 2 + 1, right = index * 2 + 2, largest = index;
            if (left < size && heap.get(left) > heap.get(largest)) {
                largest = left;
            }
            if (right < size && heap.get(right) > heap.get(largest)) {
                largest = right;
            }
            if (largest == index) {
                break;
            }
            Collections.swap(heap, index, largest);
            index = largest;
        }
    }
}
