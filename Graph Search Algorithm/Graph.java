package test;

import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;

public class Graph {
	
	
		private LinkedList<Integer> G[]; 
	
		public Graph(Integer noOfVertices) {
			// Creation of an Array of LinkedList (Of which later we will add entries to the linked lists to make the adjacency list)
			G = new LinkedList[noOfVertices];
			for (int i=0; i<G.length; i++) {
				G[i] = new LinkedList<Integer>();		
			}
		}
		
//		public void addVertex(int arrayVertex, int targetVertex, boolean targetIsHospital) {
//			this.G[arrayVertex].add(0, new Vertex(targetVertex, targetIsHospital));
//		}
		
		public void addNode(int arrayNumber, int targetVertex) {
			this.G[arrayNumber].add(0, targetVertex);
//			System.out.println("Edge added: "+ arrayNumber + "--" + targetVertex);
		}

		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			for (int i=0; i<G.length; i++) {
				result.append ("[" + i + "] ==> " + G[i] + "\n");
			}
			return result.toString();
		}
		
		public LinkedList<Integer>[] getGraphList() {
			return this.G;
		}
		
		public int length() {
			return G.length;
		}
		
		public static int getMaxNode(Scanner node_data, int linesToSkip) {		
			int max = -1; // Assume no negative nodes.
			
			while(linesToSkip > 0) {
				node_data.nextLine();
				linesToSkip--;
			}
			
			while(node_data.hasNextInt()) {
				
				String[] nextData = node_data.nextLine().split("\\s+");
				int currentNode = Integer.parseInt(nextData[0]);
				if (currentNode > max) { // Since node is essentially a number, check if bigger.
					max = currentNode;
//					System.out.println("Current Max:" + max);
				}
			}
			System.out.println("MaxNode: " + max);
			return max;
		}

		
}
