package RGBArrange;

import java.util.*;

public class ArrangeBalloons {
	static int index = 0;

	public void arrangeBalloons(Character a, int count, Character[] temp) {

		while (count > 0) {
			temp[index++] = a;
			count--;
		}
	}

	public void storeArray(Character[] a) {
		int n = a.length, valueIter;
		char keyIter;
		Map<Character, Integer> map = new LinkedHashMap<Character, Integer>();
		for (int i = 0; i < n; i++) {
			if (a[i] == 'R')
				map.put(a[i], map.getOrDefault(a[i], 0) + 1);
			if (a[i] == 'G')
				map.put(a[i], map.getOrDefault(a[i], 0) + 1);
			if (a[i] == 'B')
				map.put(a[i], map.getOrDefault(a[i], 0) + 1);
		}

		for (Map.Entry<Character, Integer> mapIter : map.entrySet()) {
			keyIter = mapIter.getKey();
			valueIter = mapIter.getValue();

			arrangeBalloons(keyIter, valueIter, a);
		}
	}

	public static void main(String[] args) {
		Scanner scannerObj = new Scanner(System.in);
		ArrangeBalloons tempObj = new ArrangeBalloons();
		System.out.println("Enter the Number of balloons ");
		int n = scannerObj.nextInt();
		Character[] a = new Character[n];
		System.out.println("Enter the Ballons Color(in CAPITAL LETTER)");
		for (int i = 0; i < n; i++) {
			a[i] = scannerObj.next().charAt(0);
		}
		tempObj.storeArray(a);
		System.out.println("Arranged Ballons are-");
		for (int i = 0; i < n; i++)
			System.out.println(a[i]);
		scannerObj.close();
	}

}
