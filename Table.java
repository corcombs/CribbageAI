import java.io.*;

public class Table {

	public static void main(String[] args){
		int cribPoints=0;
		Hand crib= new Hand();
		Hand playedActive=new Hand();
		Player player = new Player();
		Bot bot = new Bot();
		Rules ruler= new Rules();
		Card starterCard= new Card();
		boolean endOfGame = false;
		boolean playersTurn=true;
		int cardToThrow=0;
		Card cardsToThrow[] = new Card[2];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//Begin game
		while(!endOfGame){
			cribPoints=0;
			//Shuffle the deck
			Deck deck = new Deck();
			deck.shuffle();
			
			//Deal cards
			for(int i=0;i<6;i++){
				player.addToHand(deck.dealCard());
				bot.addToHand(deck.dealCard());
			}
			//print players hand
			System.out.println("Your Hand:");
			player.showHand();
			//get cards theyd like to throw
			System.out.print("Enter the card number you'd like to throw first:");				
			try{
		         cardToThrow=Integer.parseInt(br.readLine());
		    }catch (IOException ioe) {
		         System.out.println("IO error trying to read your card!");
		         System.exit(1);
		    }
			System.out.println();
			crib.addCard(player.throwCard(cardToThrow));
			player.showHand();
			System.out.print("Enter the card number you'd like to throw second:");
			try{					
				cardToThrow=Integer.parseInt(br.readLine());
			}catch (IOException ioe) {
		    	System.out.println("IO error trying to read your card!");
		         System.exit(1);
		    }
			System.out.println();
			crib.addCard(player.throwCard(cardToThrow));
			System.out.println("Bot Hand:");
			bot.showHand();
			//Get card numbers to throw
			cardsToThrow=bot.throwCards();
			crib.addCard(cardsToThrow[0]);
			crib.addCard(cardsToThrow[1]);
			starterCard=deck.dealCard();
			System.out.println();
			System.out.println("The Starter Card:");
			System.out.println(starterCard.toString());
			
			if(!playersTurn){
				bot.updateWeights(starterCard);
				System.out.println();
				System.out.println("Bot Hand:");
				bot.showHand();
				bot.calcScore(starterCard);
				System.out.println("Scores "+ bot.calcScore(starterCard) +" points this hand!");
				if(bot.getScore()>=121){
					System.out.println("-----------------------------------");
					System.out.println("You Lose!");
					endOfGame=true;
					break;
				}
			}
			System.out.println();
			System.out.println("Your New Hand:");
			player.showHand();
			System.out.println("Scores "+ player.calcScore(starterCard) +" points this hand!");
			if(player.getScore()>=121){
				System.out.println("-----------------------------------");
				System.out.println("You Win!");
				endOfGame=true;
				break;
			}
			if(playersTurn){
				bot.updateWeights(starterCard);
				System.out.println();
				System.out.println("Bot Hand:");
				bot.showHand();
				System.out.println("Scores "+bot.calcScore(starterCard)+" points this hand!");
				if(bot.getScore()>=121){
					System.out.println("-----------------------------------");
					System.out.println("You Lose!");
					endOfGame=true;
					break;
				}
			}
			System.out.println();
			System.out.println("The Crib:");
			crib.showCards();
			cribPoints=cribPoints+ruler.check15s(crib,starterCard);
			cribPoints=cribPoints+ruler.checkRuns(crib,starterCard);
			cribPoints=cribPoints+ruler.checkSuits(crib,starterCard);
			cribPoints=cribPoints+ruler.checkPairs(crib,starterCard);
			if(playersTurn){
				System.out.println("The Crib scored "+cribPoints+" points for the Bot!");
				bot.addToScore(cribPoints);
			}else{
				System.out.println("The Crib scored "+cribPoints+" points for you!");
				player.addToScore(cribPoints);
			}
			System.out.println();
			System.out.println("-----------------------------------");
			System.out.println("The Current Score:");
			System.out.println("You: "+player.getScore());
			System.out.println("Bot: "+bot.getScore());
			System.out.println("-----------------------------------");
			System.out.println();
			playersTurn=(!playersTurn);
			player.getHand().clear();
			bot.getHand().clear();
			crib.clear();
		}
		

	}	
}	

