package tuc.ece;

// Java program to demonstrate implementation of our 
// own hash table with chaining for collision detection 
import java.util.ArrayList;

// A node of chains 
class HashNode<K> {
	K key;

	// Reference to next node
	HashNode<K> next;

	// Constructor
	public HashNode(K key) {
		this.key = key;
	}
}

// Class to represent entire hash table 
class Map<K> {

	int level = 0;
	// bucketArray is used to store array of chains
	private ArrayList<HashNode<K>> bucketArray;

	// Current capacity of array list
	private int numBuckets;

	// Current size of array list
	private int size;

	// Constructor (Initializes capacity, size and
	// empty chains.
	public Map() {
		bucketArray = new ArrayList<>();
		numBuckets = 1000;
		size = 0;

		// Create empty chains
		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	// This implements hash function to find index
	// for a key
	private int getBucketIndex(K key) {
		int hashCode = key.hashCode();
		int index = hashCode % numBuckets;
		return index;
	}

	// Method to remove a given key
	public K remove(K key) {
		// Apply hash function to find index for given key
		int bucketIndex = getBucketIndex(key);

		// Get head of chain
		HashNode<K> head = bucketArray.get(bucketIndex);

		// Search for key in its chain
		HashNode<K> prev = null;
		while (head != null) {
			// If Key found
			if (head.key.equals(key))
				break;

			// Else keep moving in chain
			prev = head;
			head = head.next;
		}

		// If key was not there
		if (head == null)
			return null;

		// Reduce size
		size--;

		// Remove key
		if (prev != null)
			prev.next = head.next;
		else
			bucketArray.set(bucketIndex, head.next);

		return head.key;
	}

	// Returns value for a key
	public int get(K key) {
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		HashNode<K> head = bucketArray.get(bucketIndex);

		// Search key in chain
		while (head != null) {
			if (head.key.equals(key))
				return level;
			level++;
			head = head.next;
		}

		// If key not found
		return level;
	}

	// Adds a key value pair to hash
	public void add(K key) {
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		HashNode<K> head = bucketArray.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			if (head.key.equals(key)) {
				head.key = key;
				return;
			}
			head = head.next;
		}

		size++;
		head = bucketArray.get(bucketIndex);
		HashNode<K> newNode = new HashNode<K>(key);
		newNode.next = head;
		bucketArray.set(bucketIndex, newNode);

	}
}