import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ISA_project {
	private static ArrayList<String> instructions;
	private static ArrayList<String> arraysCreated;
	private static HashMap<String, Double> doubleValues;
	private static int[] numArray = new int[5]; 
	private static double reg1 = 0, reg2 = 0, reg3 = 0, sum = 0;
	private static int difference = 0;
	private static Scanner in;

	public static void main(String[] args) {
		instructions = new ArrayList<String>();
		doubleValues = new HashMap<String, Double>();
		arraysCreated = new ArrayList<String>();
		String key = "";

		try {
			FileReader fileIn = new FileReader(
					"fileName");
			BufferedReader reader = new BufferedReader(fileIn);

			while ((key = reader.readLine()) != null) {
				instructions.add(key);
			}

			reader.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
		Start();
	}

	public static void Start() {

		in = new Scanner(System.in);
		String beg = null;
		String linesToRun = null;
		String line1 = null;
		String line2 = null;

		for (int index = 0; index < instructions.size(); index++) {
			if (instructions.get(index).substring(5).equals("CLEARALL")) {
				reg1 = 0;
				reg2 = 0;
				reg3 = 0;
			}
			if(instructions.get(index).substring(5, 7).equals("IN")) {
				String address = instructions.get(index).substring(9);

				input(address, index);
			}

			if(instructions.get(index).contains("RESERVE")) {
				if (instructions.get(index).substring(5, 12).equals("RESERVE")) {

					String nameOfVariable = instructions.get(index).substring(
							14);
					
					doubleValues.put(nameOfVariable, reg1);
				}
			}

			if (instructions.get(index).contains("COIL")) {
				
				String variableName = instructions.get(index).substring(11);
				for (int i = 0; i < variableName.length(); i++) {
					if (variableName.charAt(i) == ',') {
						beg = variableName.substring(0, i);
						linesToRun = variableName.substring(i + 2);
						break;
					}
				}
	
				line1 = linesToRun.substring(0, 3);
				line2 = linesToRun.substring(4);
	
				difference = Integer.parseInt(line2) - Integer.parseInt(line1) + 1;
					 			
				for (double cal = 0; cal < doubleValues.get(beg); cal++) {
					
					for (int t = index + 1  ; t <  index + difference + 1 ; t++) {
						
						if (instructions.get(t).substring(5, 7).equals("IN")) {
							String address = instructions.get(t).substring(9, 12);
							input(address, t);
						}
					
						if (instructions.get(t).contains("RESERVE"))
							sum = sum + reg1;
						
					}
				}
				index += difference;
				
			}

			if (instructions.get(index).contains("OUT")) {
				if (instructions.get(index).substring(10).equals("sum"))
					System.out.println(sum);
			}

			if (instructions.get(index).substring(5, 8).equals("STOP"))
				break;
			
			if(instructions.get(index).contains("PACK"))
			{
				String givenValues = instructions.get(index).substring(11);
				
				for (int i = 0; i < givenValues.length(); i++) {
					if (givenValues.charAt(i) == ',') {
						beg = givenValues.substring(0, i);
						linesToRun = givenValues.substring(i + 2);
						arraysCreated.add(beg);
						break;
					}
				}
					
				line1 = linesToRun.substring(0, 3);
				line2 = linesToRun.substring(4);
					
				difference = Integer.parseInt(line2) - Integer.parseInt(line1) + 1;
				
				for(int x = 0; x < instructions.size(); x++)
				{
					if(instructions.get(x).substring(5).equals(beg))
					{
						for(int indx = 0; indx < 5;indx++)
							numArray[indx] = Integer.parseInt(instructions.get(x + indx + 1).substring(10));
					}
				}				
			}
			
			if(instructions.get(index).contains("CHECK"))
			{
				String givenValues = instructions.get(index).substring(12);
				boolean found = false;
				
				for (int i = 0; i < givenValues.length(); i++) {
					if (givenValues.charAt(i) == ',') {
						beg = givenValues.substring(0, i);
						linesToRun = givenValues.substring(i + 2);
						break;
					}
				}
				if(arraysCreated.contains(beg))
				{
				
					Integer i = doubleValues.get(linesToRun).intValue();
					
					for(int x = 0; x < 5;x++)
					{
						if(numArray[x] == i)
							found = true;
					}
					
					if(found)
						System.out.println("The integer was found in the array!");
					else
						System.out.println("The integer was not found in the array!");
				}
			}
		}
	}

	public static void findString(String address) {
		for (int i = 0; i < instructions.size(); i++) {
			if (instructions.get(i).substring(0, 3).equals(address)) {
				System.out.println(instructions.get(i).substring(13));
				break;
			}
		}
	}

	public static void input(String address, int index) {
		if (instructions.get(index).substring(5).contains(",")) {
			findString(address);
		}
		reg1 = in.nextDouble();
	}
}