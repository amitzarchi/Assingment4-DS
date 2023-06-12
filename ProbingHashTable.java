import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Pair<Pair<K,V>,Boolean>[] arr = null;
    private int counter;
    private int k;


    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.k = k;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.counter = 0;
        this.arr = (Pair<Pair<K,V>,Boolean>[])new Pair<?,?>[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = null;
        }
    }

    public V search(K key) {
        for (int i=getPlace(key); i<capacity; i++){
            if (this.arr[i] == null){
                return null;
            }
            else if (!this.arr[i].second() && this.arr[i].first().first() == key){
                return arr[i].first().second();
            }
        }
        return null;
    }

    public void insert(K key, V value) {
        this.counter += 1;
        this.checkRehash();
        Pair<Pair<K,V>,Boolean> item = new Pair(new Pair(key, value), false);
        for (int i=getPlace(key); i<capacity; i++){
            if (this.arr[i] == null){
                this.counter += 1;
                this.arr[i] = item;
                return;
            }
            else if (this.arr[i].second() == true){
                this.arr[i] = item;
                return;
            }
        }
    }

    public boolean delete(K key) {
        boolean keepSearch = true;
        for (int i=getPlace(key); i<capacity; i++){
            if (this.arr[i] == null){
                return false;
            }
            else if (!this.arr[i].second() && this.arr[i].first().first() == key){
                Pair<Pair<K,V>,Boolean> item = new Pair(this.arr[i].first(), true);
                this.arr[i] = item;
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
        ProbingHashTable<K,V> newHashTable = new ProbingHashTable(hashFactory, this.k, maxLoadFactor);
        // Rehash elements from the existing array to the new array
        for (int i = 0; i < capacity; i++) {
            Pair<Pair<K,V>,Boolean> item = this.arr[i];
            if(!item.second()) {
                newHashTable.insert(item.first().first(), item.first().second());
            }
        }
        this.capacity = this.capacity << 1;
        this.counter = newHashTable.counter + 1;
        this.arr = newHashTable.arr;
        this.hashFunc = newHashTable.hashFunc;
    }
}
