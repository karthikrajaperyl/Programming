package FindGreaterElement;

import java.util.*;

public class NextGreaterElement {
	public static int findMaximum(int[] a, int num, int j) {
		int n = a.length;
		int max = Integer.MIN_VALUE;
		for (int i = j; i < n; i++) {
			if (max < a[i] && num < a[i]) {
				max = a[i];
				break;
			}
		}
		return max == Integer.MIN_VALUE ? -1 : max;
	}

	public static int findMinimum(int[] a, int num, int j, int nextGreater) {
		int n = a.length;
		int min = Integer.MAX_VALUE;
		for (int i = j; i < n; i++) {
			if (num < a[i] && nextGreater > a[i] && min > a[i]) {
				min = a[i];
			}
		}
		return min == Integer.MAX_VALUE ? nextGreater : min;
	}

	public static void main(String[] args) {
		Scanner scannerObj = new Scanner(System.in);
		int n = scannerObj.nextInt();
		int nextGreater, nextSmaller = -1, j, i;
		int[] arr = new int[n];
		Stack<Integer> stack = new Stack<Integer>();
		for (i = 0; i < n; i++) {
			arr[i] = scannerObj.nextInt();
		}

		for (i = 0; i < n; i++) {
			nextSmaller = -1;
			nextGreater = findMaximum(arr, arr[i], i + 1);
			if (nextGreater != -1)
				nextSmaller = findMinimum(arr, arr[i], i + 1, nextGreater);

			arr[i] = nextSmaller;
		}
		for (i = 0; i < n; i++)
			System.out.println(arr[i]);
	}
}
