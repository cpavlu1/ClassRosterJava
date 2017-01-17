package classroster;


import java.util.Iterator;

public class SetTester {

	public static void main(String[] args) throws Exception {
		
		LinkedSet<Integer> mySet = new LinkedSet<Integer>();
		for (int i = 0; i < 10; i++) {
			mySet.add(new Integer(i));
		}
		Iterator<Integer> iter = mySet.iterator();
		System.out.print("mySet:");
		while (iter.hasNext()) {
			System.out.print(iter.next()+" ");
		}
		System.out.println();
		System.out.println();
		
		System.out.println(mySet.toString());
		System.out.println();
		System.out.println(mySet);

		
	}

}
