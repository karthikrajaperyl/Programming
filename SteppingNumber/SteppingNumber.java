package Program;

import java.util.*;

public class SteppingNumber {
	public boolean checkAdjacent(int firstNumber, int secondNumber) {
		return Math.abs(firstNumber - secondNumber) != 1;
	}

	public boolean isSteppingNumber(int i) {
		int temp = i, j = i % 10, flag = 0;
		int firstNumber = 0, secondNumber;
		if (i >= 0 && i <= 9)
			return true;
		while (temp > 0) {
			firstNumber = temp % 10;
			temp /= 10;
			secondNumber = temp % 10;
			if (temp != 0 && checkAdjacent(firstNumber, secondNumber))
				flag = 1;
			if (flag != 0)
				break;
		}
		return flag == 0 && Math.abs(j - firstNumber) == 1;
	}

	public static void main(String[] args) {
		Scanner scannerObj = new Scanner(System.in);
		SteppingNumber tempObj = new SteppingNumber();
		int a = scannerObj.nextInt();
		int b = scannerObj.nextInt();
		for (int i = a; i <= b; i++) {
			if (tempObj.isSteppingNumber(i)) {
				System.out.println(i);
			}
		}
	}

}
