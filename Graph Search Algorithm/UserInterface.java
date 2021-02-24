package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

	public void startUI() {
		String filePath;
		int size, k;
		Scanner scan = new Scanner(System.in);
		RandomGraph ranGraph = new RandomGraph();
		BFS bfs = new BFS();

		System.out.println("Choose Source File:");
		System.out.println("1. Real Road Network");
		System.out.println("2. Generate Own Random Graph");
		int choice = Integer.valueOf(scan.nextLine());

		switch (choice) {
		case 1:
			System.out.println("Enter File Path of Real Road Network:");
			filePath = scan.nextLine();
			String storedPath = filePath;
			
			// Reading graph from txt
			System.out.println("Reading graph from txt file...");
			System.out.println("How many lines to skip at top of Real Road Network file?");
			int linesToSkip = Integer.valueOf(scan.nextLine());
			
			String st;
			String[] parts;
			Graph graphX = null;
			int maxNode = -1; 
			try {
				// generate graph using no. of nodes
				System.out.println("Beginning Graph creation...");
				File file = new File(filePath);
				maxNode = Graph.getMaxNode(new Scanner(file), linesToSkip); // Get max nodes to fit size of real data appropriately
				graphX = new Graph(maxNode+1); // Make graph appropriately based on max nodes
				BufferedReader br = new BufferedReader(new FileReader(file)); // Read file again to add node to graph
				while ((st = br.readLine()) != null) {
					
					// Skip lines first
					while (linesToSkip > 0) { 
						st = br.readLine();
						linesToSkip--;
					}
					
					parts = st.split("\\s+");
					int arrayVertex = Integer.valueOf(parts[0]);
					int targetVertex = Integer.valueOf(parts[1]);
					graphX.addNode(arrayVertex, targetVertex);
				}
				System.out.println("End of insertion. Graph created");
//				System.out.println("Graph X" + graphX);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// Generate hospital
			// User sel # of hospital, random nodes are selected and stored in txt file
			System.out.println("Enter File Path to Store Hospitals");
			String targetFilePath = scan.nextLine();
			System.out.println("Generating hospitals and Storing in File...");
			try {
				HospitalGenerator.generateHospitalToTxt(graphX.length(), scan, storedPath, targetFilePath, maxNode);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			// Reading Hospital Nodes from Txt
			System.out.println("Enter File Path of Hospital File (XXX.txt):");
			filePath = scan.nextLine();	
			int[] hospitals1 = HospitalGenerator.getHospitalsFromTxt(filePath, scan, graphX);
			int S1 = hospitals1.length;

			// Ask top k nearest
			System.out.print("To Search For k Nearest Hospitals, Choose For k: ");
			do {
				k = Integer.valueOf(scan.nextLine());
				if (!(0 <= k && k <= S1)) {
					System.out.println("Please input a value within the range of 0 to # of hospitals.");
				}
			} while (!(0 <= k && k <= S1));
			if (k == 0) {
				System.out.println("Search cancelled.");
				break;
			}

			// Find nearest hospital distances
			System.out.println("Begin BFS to find hospital distances...");
			bfs.nearestCity(graphX.getGraphList(), graphX.length(), hospitals1, S1, k);

			// writing BFS results to txt file
			System.out.println("Enter file path to store BFS results:");
			filePath = scan.nextLine();
			System.out.println("Writing results to files...");
			try {
				WriteFile.writeBFSToFile(filePath, k, graphX.length(), bfs.getDist(), bfs.getPath());
			} catch (IOException e0) { 
				System.out.println(e0.getMessage());
			}    	

			System.out.println("Finished writing.");
			break;
		case 2:
			// Generate random graph and store in txt
			System.out.println("Enter File Path to Generate Random Graph:");
			filePath = scan.nextLine();
			System.out.print("Enter # of Nodes for Graph: ");
			size = Integer.valueOf(scan.nextLine());
			ranGraph.ranGraphToTxt(filePath, size);

			// Reading graph from txt
			System.out.println("Reading graph from txt file...");
			List<Integer>[] graph = ranGraph.getRanGraphFromTxt(filePath, scan);
			int n = graph.length;

			// User sel # of hospital, random nodes are selected and stored in txt file
			System.out.println("Enter File Path to Store Hospitals");
			filePath = scan.nextLine();
			System.out.println("Generating hospitals and Storing in File...");
			ranGraph.genHospitalsToTxt(n, scan, filePath);
			
			// Reading Hospital Nodes from Txt
			System.out.println("Enter File Path to Read Hospital File:");
			filePath = scan.nextLine();
			int[] hospitals = ranGraph.getHospitalsFromTxt(filePath, scan);
			int S = hospitals.length; 

			// Ask top k nearest
			System.out.print("To Search For k Nearest Hospitals, Choose For k: ");
			do {
				k = Integer.valueOf(scan.nextLine());
				if (!(0 <= k && k <= S)) {
					System.out.println("Please input a value within the range of 0 to # of hospitals.");
				}
			} while (!(0 <= k && k <= S));
			if (k == 0) {
				System.out.println("Search cancelled.");
				break;
			}

			// Find nearest hospital distances
			System.out.println("Begin BFS to find hospital distances...");
			bfs.nearestCity(graph, n, hospitals, S, k);

			// writing BFS results to txt file
			System.out.println("Enter file path to store BFS results:");
			filePath = scan.nextLine();
			System.out.println("Writing results to files...");
			try {
				WriteFile.writeBFSToFile(filePath, k, n, bfs.getDist(), bfs.getPath());
			} catch (IOException e0) { 
				System.out.println(e0.getMessage());
			}    	

			System.out.println("Finished writing.");
		}
	}
}
