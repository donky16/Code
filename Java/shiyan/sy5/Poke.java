package shiyan.sy5;

import java.util.Random;

public class Poke {
    
    public static String[] createCard(int number) {
        String[] cards = new String[number*54];    //创建number付的牌的字符串数组
        String[] suit = {"红桃", "黑桃", "梅花", "方块"};    //花色
        String[] face = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};    //牌的数值
        /**
        * k作为牌的付数遍历，每循环一次，牌的下标加54*k
        * i作为牌的花色遍历，每循环一次，就是遍历了此种花色的所有数值，牌的下标加13*i
        * j作为牌的数值遍历
        * 在52张花色牌遍历完后，加上大小王作为第53，54张牌
        */
        for(int k = 0; k < number; k  ++) {
            for(int i = 0; i < 4; i ++) {
                for(int j = 0; j < 13; j ++) {
                    cards[k*54+i*13+j] = suit[i] + face[j];
                }
            }
            cards[k*54+52] = "大王";
            cards[k*54+53] = "小王";
        }
        
        return cards;
    }
    
    public static void shuffle(String[] cards) {
    /**
    * 在所有牌中随机取两张，并交换位置
    * 进行多次交换，从而洗牌
    */
        for(int i = 0; i < 1000; i ++) {
            Random random = new Random();
            String temp;
            int num1 = random.nextInt(cards.length);
            int num2 = random.nextInt(cards.length);

            temp = cards[num1];
            cards[num1] = cards[num2];
            cards[num2] = temp;
        }
    }
    
    public static void distribute(String[] cards, int player) {
    /**
    * 此分配牌方法，可以处理牌数非均分的可能
    * 因为牌已经打乱，分牌时采用按每人牌数从第一张直接分配
    */
        
        Integer[] cardsNumOfPerPlayer = new Integer[player];    //创建数组存储每个人分配的牌数

        int other = cards.length % player;    //若不能均分，剩余牌数
        int aveCardsNumOfPerPlayer = (cards.length-other)/player;    //除不能均分的牌，没人分到的牌数

        int start = 0;    //每个人分配牌的开始位置
        int end = 0;    //每个人分配牌的结束位置
        for(int i = 0 ; i < player; i ++) {
            cardsNumOfPerPlayer[i] = aveCardsNumOfPerPlayer;    //先将均分的牌数赋值给每人的牌数
            if(i < other) {
                cardsNumOfPerPlayer[i] = cardsNumOfPerPlayer[i] + 1;    //不能均分的牌，分给先发牌的人
            }
            
            System.out.println("玩家" + (i+1));
            
            end = end + cardsNumOfPerPlayer[i];    //结束位置为此人分配的牌数加上一次分配的结束位置
            for(int k = start; k < end; k ++) {        
                System.out.format("%-4s ", cards[k]);
            }
            start = end;    //将上一次结束位置当做下一次开始位置
            System.out.println();
        }
    }
    
    public static void display(String[] cards) {
        for(int i = 0; i < cards.length; i ++) {        
            System.out.format("%-4s ", cards[i]);
            if((i+1) % 10 == 0) {
                System.out.print("\n");
            }
        }
        System.out.println();
    }
}
