
public class Rules {
	private int returnedPoints;
	private boolean keepGoing;
	private int handTotal;
	private int runLength, searchLength, i;
	public int checkPairs(Hand hand){
		//Works for any size
		returnedPoints=0;
		for(int i=0;i<hand.getCardCount()-1;i++){
			for(int j=i+1;j<hand.getCardCount();j++){
				if(hand.getCard(i).getValue()==hand.getCard(j).getValue()){
					returnedPoints=returnedPoints+2;
				}
			}
		}
		return returnedPoints;
	}
	public int checkPairs(Hand hand, Card starterCard){
		hand.addCard(starterCard);
		returnedPoints=this.checkPairs(hand);
		hand.removeCard(starterCard);
		return returnedPoints;
	}
	public int checkRuns(Hand hand, Card starterCard){
		hand.addCard(starterCard);
		hand.sortByValue();
		keepGoing=true;
		returnedPoints=0;
		//checks 5 card runs
		for(int i=0;i<hand.getCardCount()-1;i++){
			if(hand.getCard(i).getValue()+1==hand.getCard(i+1).getValue()){
				if(i+1==4){
					returnedPoints=returnedPoints+5;
					keepGoing=false;
					break;
				}
			}else{
				break;
			}
		}
		//4 card runs
		if(keepGoing){
			for(int i=0;i<hand.getCardCount()-2;i++){
				if(hand.getCard(i).getValue()+1==hand.getCard(i+1).getValue()){
					if(i+1==4){
						returnedPoints=returnedPoints+4;
						keepGoing=false;
					}
				}
			}
		}	
		//3 card run
		if(keepGoing){
			for(int i=0; i<hand.getCardCount()-2;i++){
				for(int j=i+1; j<hand.getCardCount()-1;j++){
					for(int k=j+1; k<hand.getCardCount();k++){
						if(hand.getCard(i).getValue()+1==hand.getCard(j).getValue() && hand.getCard(j).getValue()+1==hand.getCard(k).getValue()){
							returnedPoints=returnedPoints+3;
						}
					}
				}
			}
		}
		
		
		hand.removeCard(starterCard);
		return returnedPoints;
	}
	public int checkRuns(Hand hand){
		//works for AI hands
		hand.sortByValue();
		keepGoing=true;
		returnedPoints=0;


		//4 card runs
		for(int i=0;i<hand.getCardCount()-1;i++){
			if(hand.getCard(i).getValue()+1==hand.getCard(i+1).getValue()){
				if(i+1==4){
					returnedPoints=returnedPoints+4;
					keepGoing=false;
				}
			}
		}	
		//3 card run
		if(keepGoing){
			for(int i=0; i<hand.getCardCount()-2;i++){
				for(int j=i+1; j<hand.getCardCount()-1;j++){
					for(int k=j+1; k<hand.getCardCount();k++){
						if(hand.getCard(i).getValue()+1==hand.getCard(j).getValue() && hand.getCard(j).getValue()+1==hand.getCard(k).getValue()){
							returnedPoints=returnedPoints+3;
						}
					}
				}
			}
		}
		
		
		return returnedPoints;
	}
	public int checkSuits(Hand hand){
		keepGoing=true;
		returnedPoints=0;
		i=0;
		while(keepGoing){
			i++;
			if(i>=hand.getCardCount()){
				returnedPoints=hand.getCardCount();
				keepGoing=false;
			}else if(hand.getCard(i-1).getSuit()!=hand.getCard(i).getSuit()){
				keepGoing=false;
			}
		}
		return returnedPoints;
	}
	public int checkSuits(Hand hand, Card starterCard){
		hand.addCard(starterCard);
		returnedPoints=this.checkSuits(hand);
		hand.removeCard(starterCard);
		return returnedPoints;
	}
	public int checkNobs(Hand hand, Card starterCard){
		
		for(int i=0;i<hand.getCardCount();i++){
			if(hand.getCard(i).getValue()==11 && hand.getCard(i).getSuit()==starterCard.getSuit()){
				return 1;
			}
		}
		hand.removeCard(starterCard);
		return 0;
	}
	public int check15s(Hand hand){
		returnedPoints=0;
		//go through all pairs
		for(int i=0;i<hand.getCardCount()-1;i++){
			for(int j=i+1;j<hand.getCardCount();j++){
				if(hand.getCard(i).getScore()+hand.getCard(j).getScore()==15){
					returnedPoints=returnedPoints+2;
				}
			}
		}
		//go through all groups of 3
		for(int i=0; i<hand.getCardCount()-2;i++){
			for(int j=i+1; j<hand.getCardCount()-1;j++){
				for(int k=j+1; k<hand.getCardCount();k++){
					if(hand.getCard(i).getScore()+hand.getCard(j).getScore()+hand.getCard(k).getScore()==15){
						returnedPoints=returnedPoints+2;
					}
				}
			}
		}
		handTotal=0;
		for(int i=0;i<hand.getCardCount();i++){
			handTotal=hand.getCard(i).getScore()+handTotal;
		}
		if(handTotal==15){
			returnedPoints=returnedPoints+2;
		}
		//go through all groups of 4
		for(int i=0;i<hand.getCardCount();i++){
			if(handTotal-hand.getCard(i).getScore()==15){
				returnedPoints=returnedPoints+2;
			}
		}
		return returnedPoints;
	}
	public int check15s(Hand hand, Card starterCard){
		hand.addCard(starterCard);
		returnedPoints=this.check15s(hand);
		hand.removeCard(starterCard);
		return returnedPoints;
	}

}
