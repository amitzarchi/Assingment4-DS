import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private List<Pair<K,V>>[] arr = null;
    private int counter;
    private int k;

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.k = k;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.counter = 0;
        this.arr = (List<Pair<K,V>>[])new LinkedList<?>[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = new LinkedList<Pair<K, V>>();
        }
    }
    public V search(K key) {
        int index = getPlace(key);
        for (Pair<K,V> item: arr[index]) {
            if (key == item.first()){
                return item.second();
            }
        }
        return null;
    }

    public void insert(K key, V value) {
        this.counter += 1;
        this.checkRehash();
        Pair<K,V> item = new Pair(key, value);
        this.arr[getPlace(key)].add(item);
    }

    public boolean delete(K key) {
        int index = getPlace(key);
        for (Pair<K,V> item: arr[index]) {
            if (key == item.first()){
                arr[index].remove(item);
                this.counter -= 1;
                return true;
            }
        }
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    private int getPlace(K key){
        return this.hashFunc.hash(key);
    }
    private void checkRehash(){
        if(counter/(double)capacity >= maxLoadFactor){
            this.reHash();
        }
    }
    private void reHash(){
        this.k += 1;
        ChainedHashTable<K,V> newHashTable = new ChainedHashTable(hashFactory, this.k, maxLoadFactor);
        // Rehash elements from the existing array to the new array
        for (int i = 0; i < capacity; i++) {
            for (Pair<K,V> item : arr[i]) {
                newHashTable.insert(item.first(), item.second());
            }
        }
        this.capacity = this.capacity << 1;
        this.arr = newHashTable.arr;
        this.hashFunc = newHashTable.hashFunc;
    }
}
