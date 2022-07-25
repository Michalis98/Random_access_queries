package LinearHashing2;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		
		int initPages = 100, pageSize = 5, keysNo, searchNo;
		LinearHashing2 Hash1 = new LinearHashing2(pageSize, initPages);
		
		Random r = new Random();
		int level =0;
		double sum =0;
		int size = 10000;
		
		for (int i = 0 ; i < size ; i++) {
			Hash1.insertKey(r.nextInt(10000000));
		}
		
		for (int i =0 ; i < 30 ; i++) {
			
			level = Hash1.searchKey(r.nextInt(10000000));
			sum = level + sum;
		}
	
		System.out.println("The Results Of the LinearSearchOverFlow Are : " + sum/30);
		
	}

}
