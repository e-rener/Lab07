package it.polito.tdp.dizionario.model;

import java.util.List;

import it.polito.tdp.dizionario.db.WordDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println(String.format("**Grafo creato** - Trovate #%d parole di lunghezza 4\n",  model.createGraph(4).size()));
		
		WordDAO worddao = new WordDAO();
		for(String s: worddao.getAllSimilarWords("casa", 4)){
			System.out.println(s+"\n");
		}
		
//		for(String s: model.findSimilarWords("casa", model.createGraph(4))){
//			System.out.println(s+"\n");
//		}
//			
		List<String> vicini = model.displayNeighbours("casa");
		System.out.println("Vicini di casa: " + vicini);
	
		System.out.println();
		
		System.out.println("Cerco il vertice con grado massimo...");
		System.out.println(model.findMaxDegree());
	}

}
