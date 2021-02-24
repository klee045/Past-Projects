package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// hospital generated logic
/*
 * Get number of nodes (consideration of skipped nodeS)
 * Generate hospital nodes based on the above node (for consistency)
 * Maintain an array.
 */

// How to use
/*
 * Key in number of lines to skip (Cause heading in txt file maybe not be integer. Necessary for my implementation. Skip also the # line
 * Then press enter and key in values when prompted accordingly
 * 
 * Please change file name as indicated below  "roadNet-CA.txt" if need to your own file.
 */

public class HospitalGenerator {

	public static Scanner user_input = new Scanner(System.in);

	public static int[] getFilledArray(String filePath, int arraySize, Scanner scan) throws NumberFormatException, IOException {
		scan.nextLine(); // Flushing
		int[] filledArray = new int[arraySize]; // Size of array using maxNode from file, with default values 0. +1 since array runs from 0 to maxNode-1;
		// Assume maxNode >= Node Count in Graph
		
		System.out.println("Preparing filled array...");
		System.out.println("How many lines to skip at top of Real Road Network file?");
		int linesToSkip = Integer.valueOf(scan.nextLine());
		String st;
		
		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			

			while ((st = br.readLine()) != null) {

				// Skip lines first
				while (linesToSkip > 0) { 
					st = br.readLine();
					linesToSkip--;
				}

				String[] nextData = st.split("\\s+");
				int node_col_1 = Integer.parseInt(nextData[0]);
				int node_col_2 = Integer.parseInt(nextData[1]);
				if (filledArray[node_col_1] == 0) { // Check if it is empty
					filledArray[node_col_1] = 1; // Set to 1 so that next time will skip
				}
				if (filledArray[node_col_2] == 0) { // Check if it is empty
					filledArray[node_col_2] = 1; // Set to 1 so that next time will skip
				}			
			}
			System.out.print("Node Array Filled.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filledArray;
	}
	
	private static int getNoOfHospitalToChoose(Scanner scan) {
		int noOfHospitalToChoose = -1;
		boolean accepted = false;
		while (!accepted) {
			System.out.print("Please enter number of hospital to choose : ");
			if (scan.hasNextInt()) {
				noOfHospitalToChoose = scan.nextInt();
				break;
			} else {
				System.out.println("Error. Please try again!");
				scan.nextLine();
			}
		}
		return noOfHospitalToChoose;
	}
	
	
	
	public static int[] getHospitalsFromTxt(String filePath, Scanner scan, Graph g) {
		int[] hospitalList = new int[g.length()];
		String st;
		String[] parts;
		int i = 0;
		int noOfHospitals;

		System.out.println("How many lines to skip at top of hospital file?");
		int linesToSkip = Integer.valueOf(scan.nextLine());
		
		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file)); // Read file again to add node to graph
			while ((st = br.readLine()) != null) {
				
				// Skip lines first
				while (linesToSkip > 0) { 
					st = br.readLine();
					linesToSkip--;
				}
				
				parts = st.split("\\s+");
				int hospital = Integer.valueOf(parts[0]);
				hospitalList[i] = hospital;
				i++;
//				System.out.println("Hospital located at node " + hospital);
			}
			System.out.println("End of hospital insertion");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return hospitalList;
	}
	
	public static void generateHospitalToTxt(int graphLength, Scanner scan, String nodeFilePath, String targetfilePath, int maxNode) throws NumberFormatException, IOException {
		
		Random rand = new Random();
		int noOfHospitalToChoose = getNoOfHospitalToChoose(scan);
		int randomInt;
		Random random = new Random();
		Integer[] hospitalList = new Integer[noOfHospitalToChoose]; // Will store hospitalList. Default values all 0 or null.
//		for (int i = 0; i < graphLength; i++) hospitalList[i] = 0;
		
		Scanner scan_FA = new Scanner(nodeFilePath);
		int[] filledArray = getFilledArray(nodeFilePath, graphLength, scan); // Get a shared array for consistency
		
		/////////////////// Logic to add hospital //////////////////////
		int temp = noOfHospitalToChoose;
		int j = 0;
		while (temp > 0) {
			int nodeDrawn = rand.nextInt(maxNode) + 1; // Get random node
//			System.out.println("NodeDrawn is " + nodeDrawn + " : " + filledArray[nodeDrawn]);
			if (filledArray[nodeDrawn] == 1) {  // Means node exist from the logic of the array and hospital not in the list yet
				hospitalList[j] = nodeDrawn; // Set to indicate hospital exists
				temp--; //Reduce number of hospital required
				j++;
			}
			// Continue looping until all hospital has been selected consistently.
		}	
		///////////////////////////////////////////////////////////////
		System.out.println("Total hospital added: " + noOfHospitalToChoose); // Should be what the user input says, just to show on console
//		scan.nextLine();

		try {
			WriteFile.writeHospitalToFile(hospitalList, targetfilePath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
