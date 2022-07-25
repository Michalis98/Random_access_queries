package tuc.ece;

import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String args[]) throws IOException, ClassNotFoundException {
		
		int initPages = 100, pageSize = 5, keysNo, searchNo;
		LinearHashing Hash1 = new LinearHashing(pageSize, initPages);
		Map<Integer>map = new Map<>(); 
		BinaryTree bt = new BinaryTree();
		BTree<Integer> btree = new BTree<Integer>();
		
		keysNo = 100000;
		Random r = new Random();
		while(Hash1.getKeysNum() < keysNo){
			Hash1.insertKey((r.nextInt(10000000)));
			map.add(r.nextInt(10000000));
			bt.add(r.nextInt(10000000));
			btree.insert(r.nextInt(10000000));
		}


		for (int i1 =0; i1<5;i1++) {
		
		
		searchNo = 30;
		int result,result1,result2,result3,result4;
		double level = 0,level1 = 0,level2 = 0,level3 = 0,level4 = 0;
		int search_key;
		for (int i = 0; i < searchNo; i++){
			//LinearSearch
		   search_key = (r.nextInt(10000000));
		   result = Hash1.searchKey(search_key);
		   level= result + level;
		  //Chaining
		   map.level=0;
		   result1=map.get(search_key);
			level1 = result1 + level1;
			//binary tree
			bt.containsNode(search_key);
			level2 = level2+bt.level;
			//Btree
			result2= btree.get(search_key);
			level3 = result2+level3;
		   }
		
		System.out.println("The Results Of the BinarySearch Are : " + level2/30);
		System.out.println("The Results Of the Chaining Are : " + level1/30);
		System.out.println("The Results Of the LinearSearch Are : " + level/30);
		System.out.println("The Results Of the Btree Are : " + level3/30);
		System.out.println("---------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------");
		}
		}
}


