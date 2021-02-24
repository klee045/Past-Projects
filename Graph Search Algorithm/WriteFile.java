package test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.graphstream.graph.Graph;

import java.io.IOException;

public class WriteFile {
	private String path;
	private boolean append_to_file = false;

	public WriteFile(String file_path) {
		path = file_path;
	}

	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append_to_file = append_value;
	}

	public void clearFile(String file) throws IOException {
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
	}

	public void writeToFile(String textLine) throws IOException {
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);

		print_line.printf("%s" + "%n", textLine);

		print_line.close();
	}

	///////////////////////////////////////////////////////////////////////////
	// Specific Writing Functions
	public static void writeGraphToFile(String filePath, Graph graph) throws IOException {
		WriteFile writer;
		RandomGraph RandomGraph = new RandomGraph();
		List<Integer>[] graphArray = RandomGraph.toAdjList(graph);
		int n = graph.getNodeCount();
		int e = graph.getEdgeCount();
		writer = new WriteFile(filePath, true);
		writer.clearFile(filePath);
		writer.writeToFile("# Random Generated Graph");
		writer.writeToFile("# Nodes: " + n + " Edges: " + e);
		writer.writeToFile("# FromNodeId\tToNodeId");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < graphArray[i].size(); j++) {
				writer.writeToFile(Integer.toString(i) + "\t\t" 
						+ Integer.toString(graphArray[i].get(j)));
			}
		}
	}

	public static void writeHospitalToFile(Integer[] hospitals, String filePath) throws IOException {
		WriteFile writer;
		int noOfHospital = hospitals.length;
		writer = new WriteFile(filePath, true);
		writer.clearFile(filePath);
		writer.writeToFile("# " + noOfHospital);
		for (int i = 0; i < noOfHospital; i++) { 
			writer.writeToFile(Integer.toString(hospitals[i]));
		}
	}

	public static void writeBFSToFile(String filePath, int topkNearest, int sizeOfGraph, List<Integer>[] dist, List<String>[] storedPath) throws IOException {
		WriteFile writer;
		int k = topkNearest; 

		writer = new WriteFile(filePath, true);
		writer.clearFile(filePath);
		writer.writeToFile("Node\tDistance\tPath");
		for (int i = 0; i < sizeOfGraph; i++) { 
			writer.writeToFile(i + "");
			for (int j = 0; j < k; j++) {
				if (dist[i].size() == 0 || storedPath[i].size() == 0) 
					writer.writeToFile("No Paths");
				else if (j >= dist[i].size() || j >= storedPath[i].size()) 
					writer.writeToFile("No Paths");
				else 
					writer.writeToFile("Path " + (j+1) + "\t" + dist[i].get(j) + "\t\t" + storedPath[i].get(j));
			}
		}
	}	
}
