package memo;

public class Delete {
    public static String[] delete(String[] index, int number){
        for(int i = 1; i<=20; i++){
            if(index[i-1]=="")
                break;
            if(i>=number){
                index[i-1] = index[i];
            }
        }
        System.out.println("删除成功！");
        return index;
    }
}
