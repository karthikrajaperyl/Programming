package MaxSubArray;

import java.util.*;

public class Program {

	public static void main(String[] args) {
		int start;
		Scanner scannerObj = new Scanner(System.in);
		HashMap<Integer, Integer> map = new HashMap<>();
		int sum = 0, max = 0, startIndex = 0, endIndex = 0;
		int n = scannerObj.nextInt();
		int[] a = new int[n];
		map.put(0, -1);
		for (int i = 0; i < n; i++) {
			a[i] = scannerObj.nextInt();
		}
		for (int j = 0; j < n; j++) {
			sum += (a[j] == 0) ? -1 : 1;
			if (map.containsKey(sum)) {
				max = Math.max(max, j - map.get(sum));
				startIndex = map.get(sum) + 1;
				endIndex = j;
			} else {
				map.put(sum, j);
			}
		}
		System.out.println(max);
		System.out.println("Start Index- " + startIndex + "End Index- " + endIndex);
	}

}
