import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Benchmark1 {
	private static ArrayList<String> instructions;
	private static HashMap<String, Double> doubleValues;
	private static HashMap<String, String> variables;
	private static double reg1 = 0, reg2 = 0, reg3 = 0, sum = 0;
	private static int difference = 0;
	private static Scanner in;

	public static void main(String[] args) {
		instructions = new ArrayList<String>();
		variables = new HashMap<String, String>();
		doubleValues = new HashMap<String, Double>();
		String key = "";

		try {
			FileReader fileIn = new FileReader(
					"/Users/brian/Desktop/Benchmark#1.txt");
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
						{
							sum = sum + reg1;
						}
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