package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao ;
	private Graph<Country, DefaultEdge> graph ;
	private Map <Integer, Country> countryIdMap ;
	private ConnectivityInspector<Country,DefaultEdge> connInsp ;
	
	public Model() {
		this.dao = new BordersDAO() ;	
		this.countryIdMap = new HashMap<>() ;
	}
	
	public void creaGrafo() {
		this.graph = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class) ;
		this.dao.loadAllCountries(countryIdMap) ;
		Graphs.addAllVertices(this.graph,countryIdMap.values()) ;
		
		this.connInsp = new ConnectivityInspector< >(this.graph) ;
	}
	
	public void aggiungiArco(int anno) {
		for(Border b : this.dao.getCountryPairs(anno, countryIdMap)) {
			Graphs.addEdgeWithVertices(this.graph, b.getC1(), b.getC2()) ;
		}
	}
	
	public String confini() {
		String st = "" ;
		
		for(Country c: this.graph.vertexSet()) {
			
			st += c.getStateName() + "\t"+ this.graph.degreeOf(c) + " " + "\n"  ;
		}
		return st + "LE COMPONENTI CONNESSE SONO : " + this.connInsp.connectedSets().size();
	}
	
	public List<String> elencoNomiStati(){
		List<String> elencoStati = new LinkedList<String>();
		this.dao.loadAllCountries(countryIdMap);
		for(Country c: countryIdMap.values()) {
			elencoStati.add(c.getStateName());
		}
		return elencoStati ;
	}
	
	public List <Country> elencoStati(){
		List<Country> elencoStati = new LinkedList<Country>();
		this.dao.loadAllCountries(countryIdMap);
		for(Country c: countryIdMap.values()) {
			elencoStati.add(c);
		}
		return elencoStati ;
	}
	
	public void statiVisitabili (Country statoPartenza, List<Country> visitati){
		
		visitati.add(statoPartenza) ;
		
		for(Country c: Graphs.neighborListOf(this.graph, statoPartenza) ) {
			if(!visitati.contains(c)) {
				statiVisitabili(c, visitati) ;
			}
		}
	}
	
	public List<Country> trovaPath(Country c) {
		
		List<Country> path = new ArrayList<Country>() ;

		statiVisitabili(c,path);
		return path ;
	}
	
//	public String getComponenteConnessa(Country partenza) {
//		String percorso = "" ;
//		Set<Country> visitati = new HashSet<>();
//		DepthFirstIterator<Country, DefaultEdge> it = 
//				new DepthFirstIterator<>(this.graph,partenza);
//		while (it.hasNext()) {
//			visitati.add(it.next());
//		}
//		for(Country c: visitati) {
//			percorso += c.getStateName() +"\n" ;
//		}
//		return percorso ;
//	}
	
}
