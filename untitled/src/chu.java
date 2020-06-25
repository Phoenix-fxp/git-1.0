public class chu {
    public static void main(String[] args) {
        // 定义求和变量
        int sum = 0;

        for(int i = 1 ; i <= 100 ; i++){
            if (i % 3 == 0&&i%5!=0)
                System.out.println("Fizz");
            if (i % 5 == 0&&i%3!=0)
                System.out.println("Buzz");
            if(i % 3 == 0 && i % 5 == 0){
                System.out.println("FizzBuzz");}
            if(i % 3 != 0 && i % 5 != 0 )
                System.out.println(i);

                    }
        }

    }
