package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	
	private WordDAO worddao = new WordDAO();
	UndirectedGraph<String, DefaultEdge> graph;
	
	public List<String> createGraph(int numeroLettere) {

		List<String> parole = new ArrayList<String>();
		graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		for(String s: worddao.getAllWordsFixedLength(numeroLettere)){
			graph.addVertex(s);
			parole.add(s);
		}
		
		for(String s: parole){
			//similarWords tramite query sql:
//			for(String parola: worddao.getAllSimilarWords(s, numeroLettere)){
//				if(graph.degreeOf(parola) != 0)
//					graph.addEdge(s, parola);
//			}
			
			//similarWords tramite algoritmo java:
			for(String parola:findSimilarWords(s, parole)){
				//if(graph.degreeOf(parola) == 0)
					graph.addEdge(s, parola);
			}
		}
		//System.out.println("Model -- TODO");
		return new ArrayList<String>(parole);
	}

	 Collection<String> findSimilarWords(String s, List<String> parole) {
		List<String> similarWords = new ArrayList<String>();
		char[] parola = s.toCharArray();
		int contatoreLettereUguali;
		for(String str: parole){
			contatoreLettereUguali = 0;
			char[] strArray = str.toCharArray();
			for(int i=0; i<s.length(); i++){
				if(parola[i] == strArray[i])
						contatoreLettereUguali++;
			}
			if(contatoreLettereUguali == s.length()-1)
				similarWords.add(str);
		}
		return similarWords;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		return new ArrayList<String>(Graphs.neighborListOf(graph, parolaInserita));
	}

	public String findMaxDegree() {
		int degree = 0;
		String res = "";
		for(String s: graph.vertexSet()){
			if(graph.degreeOf(s) > degree){
				degree = graph.degreeOf(s);
				res = s;
			}
		}
		List<String> neighbors = new ArrayList<String>(Graphs.neighborListOf(graph, res));
		return res + ": grado"+degree+"\nVicini: "+neighbors;
	}
}
