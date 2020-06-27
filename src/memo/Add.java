package memo;

import java.util.Scanner;

public class Add {
    public static String[] add(String[] index, String thing){
        for(int i = 1; i<=20; i++){
            if(index[i-1]==""){
                index[i-1] = thing+" 0";//首位是序号 0表示未完成
                break;
            }
        }
        System.out.println("添加成功！");
        return index;
    }
}
