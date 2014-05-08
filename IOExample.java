import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

String csvFile = "weights.csv";
BufferedReader br = null;
String line = "";
String splitBy = ",";


		while ((line = br.readLine()) != null) {
		    // use comma as separator
			String[] useable = line.split(splitBy);
		}
		
		
//output:

try
	{
	FileWriter writer = new FileWriter(csvFile);
 
	writer.append("1");
	writer.append(',');
	writer.append("1");
	writer.append('\n');
	writer.append("1");
	writer.append(',');
	writer.append("1");
	writer.append('\n');
 
	//continue inputting...
 
	writer.flush();
	writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 