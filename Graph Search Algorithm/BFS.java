package test;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.graphstream.graph.Graph;


public class BFS {
	private int N;
	private int inf;
	private List<Integer>[] dist;
	private int[] source;
	private Deque<Pair<Integer, Integer>> BFSQueue;
	private int[] visited;
	private int[] prev; // Stores the previous path 
	private List<String>[] storedPath;

	// program to demonstrate distance to 
	// nearest source problem using BFS 
	// from each vertex  
	public BFS() {
		this.N = 2000000 + 1; 
		this.inf = 1000000;

		// This array stores the distances of the 
		// vertices from the nearest source 
		this.dist = new ArrayList[N];

		// a hash array where source[i] = 1 
		// means vertex i is a source 
		this.source = new int[N];

		// The BFS Queue 
		// The pairs are of the form (vertex, distance 
		// from current source) 
		this.BFSQueue = new ArrayDeque(); 

		// visited array for remembering visited vertices 
		this.visited = new int[N];

		// prev array for remembering previous parent nodes
		this.prev = new int[N];
		for (int i = 0; i < this.prev.length ; i++) {
			prev[i] = -1; // -1 means no parent node
		}

		this.storedPath = new ArrayList[N];
	}
	public List<Integer>[] getDist() { return dist; }
	public List<String>[] getPath() { return storedPath; }

	// The BFS function 
	public void BFS(List<Integer>[] graph, int start, int topkNearest) {
		int k = topkNearest;

		// clearing the queue 
		while (BFSQueue.peek() != null) 
			BFSQueue.removeLast(); 

		// push_back starting vertices 
		BFSQueue.addLast(new Pair(start, 0)); 

		while (BFSQueue.peek() != null) { 

			int s = BFSQueue.getFirst().getElement0(); 
			int d = BFSQueue.getFirst().getElement1(); 
			visited[s] = 1;
			BFSQueue.remove(); 

			// stop at the first source we reach during BFS 
			if (source[s] == 1) { 
				dist[start].add(d);
				String path = reconstructPath(start,s,this.prev);
				this.storedPath[start].add(path);
				if (storedPath[start].size() == k) return;
			} 

			// Pushing the adjacent unvisited vertices 
			// with distance from current source = this 
			// vertex's distance + 1 
			for (int i = 0; i < graph[s].size(); i++) {
				if (visited[graph[s].get(i)] == 0) { 
					BFSQueue.addLast(new Pair(graph[s].get(i), d + 1));
					visited[graph[s].get(i)] = 1;
					prev[graph[s].get(i)] = s;
				}
			}
		}
	} 

	// This function calculates the distance of each 
	// vertex from nearest source 
	public void nearestCity(List<Integer>[] graph, int n, int sources[], int S, int topkNearest) { 
		int k = topkNearest;
		// reseting the source hash array 
		for (int i = 0; i < n; i++) // Changed from 1 to 0 (Nodes can start from 0)
			source[i] = 0; 
		for (int i = 0; i < S; i++) 
			source[sources[i]] = 1; 

		for (int i = 0; i < graph.length; i++) {
			storedPath[i] = new ArrayList<>();
			dist[i] = new ArrayList<>();
		}
		// loop through all the vertices and run 
		// a BFS from each vertex to find the distance 
		// to nearest town from it 
		int totalTime = 0;
		for (int i = 0; i < n; i++) { // Changed from 1 to 0 (Nodes can start from 0)
			for (int j = 0; j < n; j++) 
				visited[j] = 0;
			//Flush prev[]
			for (int h = 0; h < this.prev.length ; h++) {
				this.prev[h] = -1; // -1 means no parent node
			}
//			long startTime = System.nanoTime();
			BFS(graph, i, k);
//			long endTime = System.nanoTime();
//			long timeElapsed = endTime - startTime;
//			totalTime += timeElapsed;
			// How to do something before resetting of visited
		} 
//		System.out.println("Total Execution Time = " + totalTime);

	} 

	public void addEdge(List<Integer>[] graph, int u, int v) { 
		graph[u].add(v); 
		graph[v].add(u); 
	} 

	private String reconstructPath(int start,int end, int[] prev) {
		String pathString = String.valueOf(end); // start with end int
		int index = end;
		for(int i = end; prev[i] != -1; i = prev[i]) {
			pathString = prev[i] + "->" + pathString;
			index = prev[i];
		}

		// Check if start and end is connected to be sure if the end point for reconstruction is the start. 
		// else there is no path.
		if (index == start) { 
			return pathString;
		} else {
			return "";
		}
	}
}
