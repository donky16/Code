package sy5;

import java.util.Scanner;

import shiyan.sy5.Poke;

@SuppressWarnings("resource")
public class PokeTest {
	public static void main(String[] args) {
		System.out.println("该扑克牌有几付？");
		Scanner sc = new Scanner(System.in);
		int cardsNum = sc.nextInt();
		String[] cards = Poke.createCard(cardsNum);
		
		System.out.println("有几个玩家？");
		int playerNum = sc.nextInt();
		
		System.out.println("显示所有的牌：");
		Poke.display(cards);
		
		Poke.shuffle(cards);
		
		System.out.println("分配给每个人的牌：");
		Poke.distribute(cards, playerNum);
	}
}
