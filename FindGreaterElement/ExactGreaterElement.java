package NGE;

import java.util.Scanner;
import java.util.Stack;

public class ExactGreaterElement {
	public void nextElement(int[] a, int[] next, char s) {
		int n1 = a.length;
		Stack<Integer> stack = new Stack<>();
		for (int i = n1 - 1; i >= 0; i--) {
			while (!stack.isEmpty() && (s == 'G' ? a[stack.peek()] < a[i] : (a[stack.peek()] > a[i]))) {
				stack.pop();
			}
			if (stack.isEmpty())
				next[i] = -1;
			else
				next[i] = stack.peek();
			stack.push(i);
		}
	}

	public void nextGreaterOfSmallerElement(int[] a) {
		int len = a.length, temp = 0, prevIter, nextIter;
		int[] NG = new int[len];
		int[] NS = new int[len];
		nextElement(a, NG, 'G');
		nextElement(a, NS, 'S');

		for (int i = 0; i < len; i++) {
			if (NG[i] != -1) {

				if (NS[NG[i]] != -1) {

					prevIter = NG[i];
					nextIter = NS[NG[i]];

					while (nextIter != -1) {
						if (nextIter != -1) {
							if (a[i] >= a[nextIter] && a[prevIter] >= a[i]) {
								break;
							}
						}
						prevIter = nextIter;
						nextIter = NS[prevIter];

					}

				} else {
					prevIter = NG[i];
				}
				temp = a[prevIter];
				a[i] = temp;
			} else {
				a[i] = -1;
			}
		}
	}

	public static void main(String[] args) {
		ExactGreaterElement tempObj = new ExactGreaterElement();
		Scanner scannerObj = new Scanner(System.in);
		int n = scannerObj.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = scannerObj.nextInt();
		tempObj.nextGreaterOfSmallerElement(a);
		for (int i = 0; i < n; i++)
			System.out.println(a[i]);
		scannerObj.close();
	}
}
