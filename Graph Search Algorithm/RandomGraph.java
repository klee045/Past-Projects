package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class RandomGraph {
	public Graph getRandomGraph(int noOfNodes){
		Graph graph = new SingleGraph("Random");
		Generator gen = new RandomGenerator(8);
		gen.addSink(graph);
		gen.begin();
		for(int i=0; i< noOfNodes; i++)
			gen.nextEvents();
		gen.end();
		return graph;
	}

	public List<Integer>[] toAdjList(Graph g) {
		int n = g.getNodeCount();
		int e = g.getEdgeCount();

		List<Integer>[] graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if(g.getNode(i).hasEdgeBetween(j)) {
					graph[i].add(j);
					graph[j].add(i);
				}
			}
		}
		return graph;  
	}

	public void ranGraphToTxt(String filePath, int size) {
		Graph g = getRandomGraph(size);
		List<Integer>[] graphAdj = toAdjList(g);
		try {
			WriteFile.writeGraphToFile(filePath, g);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Integer>[] getRanGraphFromTxt(String filePath, Scanner scan) {
		List<Integer>[] graph = null;
		String st;
		String[] parts;
		int i = 0;
		int noOfNodes;

		System.out.println("How many lines to skip at top of graph file?");
		int linesToSkip = Integer.valueOf(scan.nextLine());

		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((st = br.readLine()) != null) {
				// generate graph using no. of nodes
				if (i == 1) {
					parts = st.split(" ");
					noOfNodes = Integer.valueOf(parts[2]);
					graph = new ArrayList[noOfNodes];
					for (int j = 0; j < noOfNodes; j++) {
						graph[j] = new ArrayList<Integer>();
					}
					i++;
					continue;
				}

				// skip lines at top of file
				if (i < linesToSkip) {
					i++;
					continue;
				}

				parts = st.split("\t\t");
				int node1 = Integer.valueOf(parts[0]);
				int node2 = Integer.valueOf(parts[1]);

				if (!(graph[node1].contains(node2)))  
					graph[node1].add(node2);

				if (!graph[node2].contains(node1))
					graph[node2].add(node1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return graph;
	}

	public void genHospitalsToTxt(int sizeOfGraph, Scanner scan, String filePath) {
		int randomInt;
		Random random = new Random();
		System.out.print("Choose no. of hospitals: ");
		int noOfHospital = Integer.valueOf(scan.nextLine());
		Integer[] hospitals = new Integer[noOfHospital];
		for (int i = 0; i < noOfHospital; i++) {
			// get a random int from 0 to no of nodes - 1
			do {
				randomInt = random.nextInt(sizeOfGraph);
			} while (Arrays.asList(hospitals).contains(randomInt));
			hospitals[i] = randomInt;
		}

		try {
			WriteFile.writeHospitalToFile(hospitals, filePath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int[] getHospitalsFromTxt(String filePath, Scanner scan) {
		int[] hospitals = null;
		String st;
		String[] parts;
		int i = 0;
		int j = 0;
		int noOfHospitals;

		System.out.println("How many lines to skip at top of hospital file?");
		int linesToSkip = Integer.valueOf(scan.nextLine());

		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((st = br.readLine()) != null) {
				// generate graph using no. of nodes
				if (i == 0) {
					parts = st.split(" ");
					noOfHospitals = Integer.valueOf(parts[1]);
					hospitals = new int[noOfHospitals];
					i++;
					continue;
				}
				if (i < linesToSkip) {
					i++;
					continue;
				}

//				parts = st.split("\t\t");
				int hospitalNode = Integer.valueOf(st);
//				int node2 = Integer.valueOf(parts[1]);

				hospitals[j] = hospitalNode;
				j++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return hospitals;
	}
}

