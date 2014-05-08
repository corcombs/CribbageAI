import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
public class Bot extends Player {
	private Card cardToThrow;
	private Hand holdHand = new Hand();
	private double currentHandScore;
	private double highestHandScore;
	private double multiplier=1;
	private int whatHand[]={0,0,0,0};
	private Rules aiRules = new Rules();
	private Card cardArr[] = new Card[2];
	int j;
	private String line = "";
	private String splitBy = ",";
	private String[] useable;
	BufferedReader br;
	private double inDoubles[] = new double[4];
	private int inInts[] = new int[4];
	private int pointsFromSelection[]=new int[4];
	private double expectedPoints[]=new double[4];
	private double expectedSelectionPoints[]=new double[4];
	private int totalPointsSelection;
	private double outDoubles[]=new double[4];
	private int outInts[]=new int[4];
	String csvFile = "/Users/corbincombs/Documents/workspace/Cribbage/bin/weights.csv";
	public Card[] throwCards(){
		//go through all possible hands of 4 cards and score them.
		try {
			 
			br = new BufferedReader(new FileReader(csvFile));
			int i=0;
			while ((line = br.readLine()) != null){
				// use comma as separator
				useable = line.split(splitBy);
				inDoubles[i]=Double.parseDouble(useable[0]);
				inInts[i]=Integer.parseInt(useable[1]);
				i++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		highestHandScore=0;
		for(int i=0;i<this.getHand().getCardCount()-4;i++){
			for(int j=i+1;j<this.getHand().getCardCount()-3;j++){
				for(int k=j+1;k<this.getHand().getCardCount()-2;k++){
					for(int l=k+1;l<this.getHand().getCardCount()-1;l++){
						currentHandScore=0;
						holdHand.addCard(this.getHand().getCard(i));
						holdHand.addCard(this.getHand().getCard(j));
						holdHand.addCard(this.getHand().getCard(k));
						holdHand.addCard(this.getHand().getCard(l));
						//**********************************************************
						//READ FROM FILE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						//EXCHANGE MULTIPLIER VARIABLE FOR DATA FROM FILE!!!!!!!!!!!	
						//**********************************************************
						
						expectedPoints[0]=(aiRules.check15s(holdHand)*inDoubles[0]);
						expectedPoints[1]=(aiRules.checkPairs(holdHand)*inDoubles[1]);
						expectedPoints[2]=(aiRules.checkSuits(holdHand)*inDoubles[2]);
						expectedPoints[3]=(aiRules.checkRuns(holdHand)*inDoubles[3]);
						currentHandScore=expectedPoints[0]+expectedPoints[1]+expectedPoints[2]+expectedPoints[3];
						//set the place of the highest scoring hand and set the highest expected score
						if(currentHandScore>highestHandScore){
							highestHandScore=currentHandScore;
							whatHand[0]=i;
							whatHand[1]=j;
							whatHand[2]=k;
							whatHand[3]=l;
							expectedSelectionPoints[0]=expectedPoints[0];
							expectedSelectionPoints[1]=expectedPoints[1];
							expectedSelectionPoints[2]=expectedPoints[2];
							expectedSelectionPoints[3]=expectedPoints[3];
						}
						
						holdHand.clear();
					}
				}
			}
		}
		j=0;
		//Fill card array with 2 cards to throw
		for(int i=0;i<6;i++){
			if(whatHand[0]!=i && whatHand[1]!=i && whatHand[2]!=i && whatHand[3]!=i){
				if(j<2){
					cardArr[j]=this.getHand().getCard(i);
				}
				j++;
			}
		}
		this.getHand().removeCard(cardArr[0]);
		this.getHand().removeCard(cardArr[1]);
		
		return cardArr;
		
	}
	public void updateWeights(Card starterCard){
		this.getHand().addCard(starterCard);
		currentHandScore=0;
		currentHandScore=currentHandScore+(aiRules.check15s(this.getHand(),starterCard)*inDoubles[0]);
		currentHandScore=currentHandScore+(aiRules.checkPairs(this.getHand(),starterCard)*inDoubles[1]);
		currentHandScore=currentHandScore+(aiRules.checkSuits(this.getHand(),starterCard)*inDoubles[2]);
		currentHandScore=currentHandScore+(aiRules.checkRuns(this.getHand(),starterCard)*inDoubles[3]);
		for(int i=0;i<4;i++){
			
			if(expectedSelectionPoints[i]>0){
				outDoubles[i]=((inDoubles[i]*inInts[i])+(currentHandScore/expectedSelectionPoints[i]))/(inInts[i]+1);
				outInts[i]=inInts[i]+1;
			}else{
				outDoubles[i]=inDoubles[i];
				outInts[i]=inInts[i];
			}
		}
		try{
			FileWriter writer = new FileWriter(csvFile);
	 
			writer.write(String.valueOf(outDoubles[0]));
			writer.write(',');
			writer.write(Integer.toString(outInts[0]));
			writer.write('\n');
			writer.write(String.valueOf(outDoubles[1]));
			writer.write(',');
			writer.write(Integer.toString(outInts[1]));
			writer.write('\n');
			writer.write(String.valueOf(outDoubles[2]));
			writer.write(',');
			writer.write(Integer.toString(outInts[2]));
			writer.write('\n');
			writer.write(String.valueOf(outDoubles[3]));
			writer.write(',');
			writer.write(Integer.toString(outInts[3]));
			writer.write('\n');
	 
		//continue inputting...
	 
			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
		this.getHand().removeCard(starterCard);
	}
}
