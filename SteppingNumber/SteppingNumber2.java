package Program;

import java.util.*;

public class SteppingNumber2 {
	public void displayNumber(int min, int max, int num) {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(num);
		int temp1, tempprev, tempnext;
		while (!queue.isEmpty()) {
			tempprev = -1;
			tempnext = -1;
			temp1 = queue.poll();
			// System.out.println(temp1);
			if (( (temp1 >= min && temp1 <= max) && ((temp1>=0&&temp1<=9)|| Math.abs(num - (temp1 % 10)) == 1))) {
				System.out.println(temp1);
			}
			if (temp1 == 0 || temp1 > max)
				continue;
			if (temp1 % 10 == 0) {
				tempnext = (temp1 % 10) + 1;
			} else if (temp1 % 10 == 9) {
				tempprev = (temp1 % 10) - 1;
			} else {
				tempprev = (temp1 % 10) - 1;
				tempnext = (temp1 % 10) + 1;
			}
			if (tempprev != -1)
				queue.add(temp1 * 10 + tempprev);
			if (tempnext != -1)
				queue.add(temp1 * 10 + tempnext);
		}
	}

	public void displaySteppingNumber(int min, int max) {
		for (int i = 0; i <= 9; i++) {
			displayNumber(min, max, i);
		}
	}

	public static void main(String[] args) {
		Scanner scannerObj = new Scanner(System.in);
		SteppingNumber2 tempObj = new SteppingNumber2();
		int min = scannerObj.nextInt();
		int max = scannerObj.nextInt();
		tempObj.displaySteppingNumber(min, max);
	}
}
