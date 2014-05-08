
public class Player {
	private Hand hand = new Hand();
	private Card card=new Card();
	private Rules rules = new Rules();
	private int score=0;
	private int prevScore=0;
	public Hand getHand(){
		return hand;
	} 
	public void addToHand(Card card){
		hand.addCard(card);
	}
	public void removeFromHand(int cardNumToRemove){
		hand.removeCard(cardNumToRemove);
	}
	public void play(int cardNumToPlay){
		
	}
	public void showHand(){
		hand.showCards();
	}
	public Card throwCard(int cardNumToThrow){
		card=hand.getCard(cardNumToThrow);
		hand.removeCard(card);
		return card;
	}
	public void addToScore(int addToScore){
		score=score+addToScore;
	}
	public int getScore(){
	
		return score;
	}
	public int calcScore(Card starterCard){
		prevScore=score;
		score=score+rules.check15s(hand,starterCard);
		score=score+rules.checkNobs(hand, starterCard);
		score=score+rules.checkPairs(hand, starterCard);
		score=score+rules.checkSuits(hand, starterCard);
		score=score+rules.checkRuns(hand, starterCard);
		return score-prevScore;
	}

}
