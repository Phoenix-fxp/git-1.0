public class PrintFib {
	
	public static void main(String[] args) {
		
		int a = 1;
		int b = 1;	
		for(int i = 1;i <= 5;i++) {	
			//循环打印a,b两个数，即两个两个打印
			System.out.print(a + "\t" + b + "\t");
			//打印第三、四个数
			a = a + b;
			b = a + b;		
		}
	}
}