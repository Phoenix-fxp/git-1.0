package test_driven_development;



import java.util.*;

import java.util.regex.Pattern;



public class poker {

    static String poker_pattern = "([2-9]|T|J|Q|K|A)(D|S|H|C)";

    static Map<Character, Integer> map;

    static {

        map = new HashMap<>();

        for (int i = 2; i < 10; i++) {

            map.put((char)(i+48), i);

        }

        map.put('T', 10);

        map.put('J', 11);

        map.put('Q', 12);

        map.put('K', 13);

        map.put('A', 14);

    }





    /**

     * @param black 黑方所持扑克

     * @param white 白方所持扑克

     * @return int 1 黑方胜，2 白方胜， 3 平手；

     * @error 返回值小于0，输入错误：-1表示输入字符串无四个空格，-2表示分割后的字符串不符合牌的组成，-3表示有相同的牌

     */

    public static int play(String black, String white) {

        int res = 3;

        String[] blacks = black.split(" ");

        String[] whites = white.split(" ");

        if (isInputCorrect(blacks, whites) != 0) {

            return isInputCorrect(blacks, whites);

        }

        // 正式play

        List<Integer> B_Vals = myDataByNum(blacks);

        List<Integer> W_Vals = myDataByNum(whites);

        List<List<Integer>> B = myDataByColor(blacks);

        List<List<Integer>> W = myDataByColor(whites);

        boolean[] b_kind = isWhat(B, B_Vals);

        boolean[] w_kind = isWhat(W, W_Vals);

        for (int i = 0; i < b_kind.length; i++) {

            if (b_kind[i] || w_kind[i]) {

                if (b_kind[i] && !w_kind[i]) {

                    return 1;

                }else if (!b_kind[i] && w_kind[i]){

                    return 2;

                }

                switch (i) {

                    case 0:

                    case 1:

                    case 2:

                        return normal(B_Vals, W_Vals);

                    case 3:

                        int num1 = hasThreeSame(B_Vals), num2 = hasThreeSame(W_Vals);

                        if (num1 > num2)

                            return 1;

                        if (num1 < num2)

                            return 2;

                        return 3;

                    case 4:

                        int[] arr1 = new int[3];

                        int[] arr2 = new int[3];

                        twoCouple(B_Vals, arr1);

                        twoCouple(W_Vals, arr2);

                        for (int j = 0; j < 3; j++) {

                            if (arr1[i] > arr2[j])  return 1;

                            if (arr1[j] < arr2[j])  return 2;

                        }

                        return 3;

                    case 5:

                        int[] arrB = new int[4];

                        int[] arrW = new int[4];

                        oneCouple(B_Vals, arrB);

                        oneCouple(W_Vals, arrW);

                        for (int j = 0; j < 4; j++) {

                            if (arrB[j] > arrW[j])  return 1;

                            if (arrB[j] < arrW[j])  return 2;

                        }

                        return 3;

                }

            }

        }

        res = normal(B_Vals, W_Vals);

        return res;

    }





    /**

     * 判断输入是否正确

     * @param blacks

     * @param whites

     * @return  -1表示输入字符串无四个空格，-2表示分割后的字符串不符合牌的组成，-3表示有相同的牌，0表示输入无误

     */

    static int isInputCorrect(String[] blacks, String[] whites) {

        // 判断输入是否正常

        if (blacks.length != 5 || whites.length != 5) return -1;

        for (String s : blacks) {

            if (!isPoker(s)) return -2;

        }

        for (String s : whites) {

            if (!isPoker(s)) return -2;

        }

        // 看有没有相同的牌

        for (String s1 : blacks) {

            for (String s2 : whites) {

                if (s1.equals(s2)) return -3;

            }

        }

        return 0;

    }

    /**

     * 判断字符串是否能组成牌

     * @param p

     * @return boolean

     */

    static boolean isPoker(String p) {

        if (p.length() != 2 || !Pattern.matches(poker_pattern, p)) {

            System.out.println(p);

            return false;

        }

        return true;

    }

    /**

     * 给所持扑克牌分类

     * @param lists

     * @param vals

     * @return boolean[] 数组长度为6，0-5，分别表示同花顺至一个对子，哪个为true则表示是哪个类型，若无true，则是散牌

     */

    static boolean[] isWhat(List<List<Integer>> lists, List<Integer> vals){

        boolean[] kinds = new boolean[6];

        if (isSameCol(lists) >= 0 && isShunZi(vals) > 0) {

            kinds[0] = true;

        } else if (isSameCol(lists) >= 0) {

            kinds[1] = true;

        } else if (isShunZi(vals) > 0) {

            kinds[2] = true;

        } else if (hasThreeSame(vals) > 0) {

            kinds[3] = true;

        } else if (hasCouple(vals) > 0) {

            kinds[6-hasCouple(vals)] = true;

        }

        return kinds;

    }

    /**

     * 处理数据，方便判断是顺子，有无对子等

     * @param pokers

     * @return List<Integer>

     */

    static List<Integer> myDataByNum(String[] pokers) {

        List<Integer> vals = new ArrayList<>();

        for (String p : pokers) {

            vals.add(map.get(p.charAt(0)));

        }

        Collections.sort(vals);

        return vals;

    }

    /**

     * 处理数据，方便判断是否为同花

     * @param pokers

     * @return List<List<Integer>>

     */

    static List<List<Integer>> myDataByColor(String[] pokers) {

        List<List<Integer>> lists = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {

            lists.add(new ArrayList<>());

        }

        for (int i = 0; i < 5; i++) {

            String poker = pokers[i];

            switch (poker.charAt(1)) {

                case 'D':

                    lists.get(0).add(map.get(poker.charAt(0)));

                    break;

                case 'S':

                    lists.get(1).add(map.get(poker.charAt(0)));

                    break;

                case 'H':

                    lists.get(2).add(map.get(poker.charAt(0)));

                    break;

                case 'C':

                    lists.get(3).add(map.get(poker.charAt(0)));

            }

        }

        for (int i = 0; i < 4; i++) {

            Collections.sort(lists.get(i));

        }

        return lists;

    }

    /**

     * 判断是否是同花

     * @param myData

     * @return  int n 表示花色， -1 则不是同花。

     */

    static int isSameCol(List<List<Integer>> myData) {

        int n = -1;

        for (int i = 0; i < myData.size(); i++) {

            if (myData.get(i).size() > 0) {

                if (myData.get(i).size() == 5) {

                    n = i;

                }

                return n;

            }

        }

        return n;

    }

    /**

     * 判断是否是顺子

     * @param vals

     * @return int 如果返回-1，不是顺子；否则返回顺子的最大值

     */

    static int isShunZi(List<Integer> vals) {

        for (int i = 1; i < vals.size(); i++) {

            if (vals.get(i) != vals.get(i-1)+1) return -1;

        }

        return vals.get(vals.size()-1);

    }

    /**

     * 含有对子数量

     * @param vals

     * @return int 返回对子数量

     */

    static int hasCouple(List<Integer> vals){

        int n = 0;

        for (int i = vals.size()-1; i > 0; i--) {

            if (vals.get(i) == vals.get(i-1)) {

                n++;

            }

        }

        return n;

    }

    /**

     * 是否有三个相同的数

     * @param vals

     * @return int -1表示不存在，否则返回三个相同的数的数值

     */

    static int hasThreeSame(List<Integer> vals){

        for (int i = vals.size()-1; i > 1; i--) {

            if (vals.get(i) == vals.get(i-1) && vals.get(i-1) == vals.get(i-2)) {

                return i;

            }

        }

        return -1;

    }

    /**

     * 散牌的比较

     * @param black

     * @param white

     * @return int 1 黑胜， 2 白胜， 3 平局。

     */

    static int normal(List<Integer> black, List<Integer> white) {

        for (int i = black.size()-1; i >= 0; i--) {

            int b = black.get(i), w = white.get(i);

            if (b != w) {

                if (b > w) return 1;

                return 2;

            }

        }

        return 3;

    }

    /**

     * 对有两个对子的牌进行数据转换

     * @param B_Vals  从小到大的排列的牌

     * @param arr1  arr1[0]大对子数值，arr1[1]小对子数值，arr1[2]剩下那张牌的大小

     */

    static void twoCouple(List<Integer> B_Vals, int[] arr1) {

        for (int j = B_Vals.size()-1; j > 0;) {

            if (B_Vals.get(j) == B_Vals.get(j-1)) {

                if (B_Vals.get(j) > arr1[0]) {

                    arr1[1] = arr1[0];

                    arr1[0] = B_Vals.get(j);

                } else {

                    arr1[1] = B_Vals.get(j);

                }

                j -= 2;

            }else {

                arr1[2] = B_Vals.get(j);

                j--;

            }

        }

        if (B_Vals.get(0) > arr1[2]) {

            arr1[2] = B_Vals.get(0);

        }

    }

    /**

     * 对只有一个对子的牌进行数据转换

     * @param Vals  从小到大的排列的牌

     * @param arr   arr[0]为对子大小，按剩余牌的的降序排列进行赋值

     */

    static void oneCouple(List<Integer> Vals, int[] arr) {

        for (int i = Vals.size()-1; i > 0;) {

            if (Vals.get(i) == Vals.get(i-1)) {

                arr[0] = Vals.get(i);

                i -= 2;

            }

            for (int j = 1; j < 4; j++) {

                if (Vals.get(i) > arr[j]) {

                    arr[j] = Vals.get(i);

                    i++;

                    break;

                }

            }

        }

        if (Vals.get(0) > arr[3]) {

            arr[3] = Vals.get(0);

        }

    }



    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

//        String b = "2H 4S 4C 2D 4H";

//        String w = "2C 3H 5S 8C AD";

        System.out.println("请输入黑方所持牌：");

        String b = input.nextLine();

        System.out.println("请输入白方所持牌：");

        String w = input.nextLine();

        System.out.print("对战结果： ");

        switch (play(b, w)) {

            case 1:

                System.out.print("black wins");

                break;

            case 2:

                System.out.print("white wins");

                break;

            case 3:

                System.out.print("tie");

                break;

            default:

                System.out.print(play(b, w));

        }

    }

}