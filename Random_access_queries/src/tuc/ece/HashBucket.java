package tuc.ece;
import java.io.*;

class HashBucket {
	int level2;
	int divided;
	private int keysNumber;
	private int[] keys;
	private HashBucket overflowBucket;

	public HashBucket(int bucketSize) {		// Constructor: initialize variables
		keysNumber = 0;
		keys = new int[bucketSize];
		overflowBucket = null;
	}

	public int numKeys(){return keysNumber;}

	public void insertKey(int key, LinearHashing linearHashing) { // inserts a key to the node


		int i;
		int bucketSize = linearHashing.getBucketSize();
		int keysNum = linearHashing.getKeysNum();
		int keySpace = linearHashing.getKeySpace();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++){
		   if (this.keys[i] == key){	//key already here. Ignore the new one
		     return;
		   }
		}
		if (i < bucketSize){				// bucket not full write the new key
		  keys[i] = key;
		  this.keysNumber++;
		  keysNum++;
		  linearHashing.setKeysNum(keysNum); 			// update linear hashing class.
		  //System.out.println("HashBucket.insertKey: KeysNum = " + keysNum );
		}
		else {
		    //System.out.println("Overflow.............");
		    if (this.overflowBucket != null){	// pass key to the overflow
		      this.overflowBucket.insertKey(key, linearHashing);
		    }
		    else {						// create a new overflow and write the new key
			this.overflowBucket = new HashBucket(bucketSize);
			keySpace += bucketSize;
		        linearHashing.setKeySpace(keySpace);		// update linear hashing class.
			this.overflowBucket.insertKey(key, linearHashing);
		    }
		}
	}


	int removeLastKey(LinearHashing lh) {	// remove bucket last key

		int retval;
		int bucketSize = lh.getBucketSize();
		int keySpace = lh.getKeySpace();

		if (this.overflowBucket == null) {
		  if (this.keysNumber != 0){
		    this.keysNumber--;
		    return this.keys[this.keysNumber];
		  }
		  return 0;
		}
		else {
		  retval = this.overflowBucket.removeLastKey(lh);
		  if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
		    this.overflowBucket = null;
		    keySpace -= bucketSize;
		    lh.setKeySpace(keySpace);			// update linear hashing class.
		  }
		  return retval;
		}
	}

	public int searchKey2(int key, LinearHashing lh) {
		level2=0;
		return searchKey(key,lh);
		
	}
	
	public int searchKey(int key, LinearHashing lh) {
		  
		int i;
		int bucketSize = lh.getBucketSize();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++) {
		   if (this.keys[i] == key) {	//key found
			 
			   return level2;
		   }
		   level2++;
		  
		}
		if (this.overflowBucket != null) {				//look at the overflow for the key if one exists
			return this.overflowBucket.searchKey(key,lh);
	      }
	      else {
		  return level2;
	      }
	}

	public void splitBucket(LinearHashing linearHashing, int n, int bucketPos, HashBucket newBucket) {	//splits the current bucket

		int i;
		int bucketSize = linearHashing.getBucketSize();
		int keySpace = linearHashing.getKeySpace();
		int keysNum = linearHashing.getKeysNum();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize);) {
		   if ((this.keys[i]%n) != bucketPos){	//key goes to new bucket
		     newBucket.insertKey(this.keys[i], linearHashing);
		     this.keysNumber--;
		     keysNum = linearHashing.getKeysNum();
		     keysNum--;
		     linearHashing.setKeysNum(keysNum);		// update linear hashing class.
		     //System.out.println("HashBucket.splitBucket.insertKey: KeysNum = " + keysNum );
		     this.keys[i] = this.keys[this.keysNumber];
		   }
		   else {				// key stays here
		     i++;
		   }
		}

		if (this.overflowBucket != null) {	// split the overflow too if one exists
		  this.overflowBucket.splitBucket(linearHashing, n, bucketPos, newBucket);
		}
		while (this.keysNumber != bucketSize) {
		     if (this.overflowBucket == null) {
			 return;
		     }
		     if (this.overflowBucket.numKeys() != 0) {
		       this.keys[this.keysNumber] = this.overflowBucket.removeLastKey(linearHashing);
		       if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
			 this.overflowBucket = null;
			 keySpace -= bucketSize;
			 linearHashing.setKeySpace(keySpace);      // update linear hashing class.
		       }
		       this.keysNumber++;
		     }
		     else {				// overflow empty free it
			 this.overflowBucket = null;
			 keySpace -= bucketSize;
		         linearHashing.setKeySpace(keySpace);	// update linear hashing class.
		     }
	 	}
	}

	public void mergeBucket(LinearHashing lh, HashBucket oldBucket) {	//merges the current bucket

		int keysNum = 0;

		while (oldBucket.numKeys() != 0) {
		     this.insertKey(oldBucket.removeLastKey(lh), lh);
		}
	}


	
} // HaskBucket class
