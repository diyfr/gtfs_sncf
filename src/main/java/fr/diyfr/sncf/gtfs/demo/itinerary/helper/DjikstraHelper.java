package fr.diyfr.sncf.gtfs.demo.itinerary.helper;

import java.util.*;

import fr.diyfr.sncf.gtfs.demo.itinerary.model.*;
import fr.diyfr.sncf.gtfs.demo.load.model.GTFSData;
import fr.diyfr.sncf.gtfs.model.*;

public class DjikstraHelper {
	// La liste des noeuds visités
	private Set<String> settledNodes;
	// La liste des noeuds non visités
	private Set<String> unSettledNodes;
	// Mapping entre un noeud et son prédécesseur
	private Map<String, String> predecessors;
	// La distance entre le noeud courant (ici un id) et un des noeuds du graphe
	private Map<String, Integer> distance;

	// Les gares d'origine et destination
	private String UICStart, UICEnd;

	// Liste des noeuds enfants (dépendant d'un Arc)
	private List<ChildNode> childs = new ArrayList<ChildNode>();

	// Map codeUI/ stop_id
	private Map<String, String> uicToStopID = new HashMap<String, String>();
	// Liste des Noeuds principaux
	private List<String> parentNode = new ArrayList<String>();
	// Liste des Arcs
	private List<Arc> arcs = new ArrayList<Arc>();

	/**
	 * Constructeur prenant en entrée des données GTFS (filtrées ou
	 * non-filtrées)
	 * 
	 * @param gtfs
	 */
	public DjikstraHelper(GTFSData gtfs) {

		// On base les arcs et noeuds principaux sur les routes
		for (Map.Entry<String, Routes> e : gtfs.getRoutes().entrySet()) {
			String[] split = e.getKey().split("-");
			Arc unArc = new Arc();
			unArc.setA(split[1].replaceAll("\\D+", ""));
			if (!parentNode.contains(unArc.getA())) {
				parentNode.add(unArc.getA());
			}
			unArc.setB(split[2].replaceAll("\\D+", ""));
			if (!parentNode.contains(unArc.getB())) {
				parentNode.add(unArc.getB());
			}
			unArc.setDistance(Integer.MAX_VALUE);
			arcs.add(unArc);
		}

		// On scanne ensuite toutes les dessertes (StopTimes)
		for (Map.Entry<String, List<StopTimes>> e : gtfs.getStopTimes().entrySet()) {
			List<StopTimes> list = e.getValue();
			if (list != null && list.size() > 1) {
				String stopIdEnd = null, stopIdStart = null;
				StopTime ttEnd = null, ttStart = null;
				int currentSequence = 0;
				// Premier passage pour identifier l'origine et destination de
				// la circulation
				for (StopTimes stimes : list) {
					String uicTemp = stimes.getStop_id().replaceAll("\\D+", "");
					if (!uicToStopID.containsKey(uicTemp)) {
						uicToStopID.put(uicTemp, stimes.getStop_id());
					}

					if (stimes.getStop_sequence() == 0) {
						stopIdStart = uicTemp;
						ttStart = stimes.getDeparture_time();
					} else {
						if (stimes.getStop_sequence() > currentSequence) {
							stopIdEnd = uicTemp;
							ttEnd = stimes.getArrival_time();
							currentSequence = stimes.getStop_sequence();
						}
					}
				}

				// On construit un Arc pour cette circulation
				Arc unArc = new Arc();
				unArc.setA(stopIdStart);
				unArc.setB(stopIdEnd);
				unArc.setDistance(getDelta(ttEnd, ttStart));
				if (arcs.contains(unArc)) {
					// Il existe déjà on fait une mise à jour du poids de l'arc
					arcs.get(arcs.indexOf(unArc)).updateDistance(unArc);
				} else {
					// L'arc n'existe pas on référence les extrémités comme
					// noeuds principaux
					if (!parentNode.contains(unArc.getA())) {
						parentNode.add(unArc.getA());
					}
					if (!parentNode.contains(unArc.getB())) {
						parentNode.add(unArc.getB());
					}
					// On ajoute l'arc découvert
					arcs.add(unArc);
				}

				// Deuxième passage, on identifie et sauvegarde les noeuds
				// enfants
				for (StopTimes stimes : list) {
					String uicTemp = stimes.getStop_id().replaceAll("\\D+", "");
					if (stimes.getStop_sequence() != 0 && stimes.getStop_sequence() != currentSequence) {
						if (!parentNode.contains(uicTemp)) {
							ChildNode child = new ChildNode(uicTemp);
							child.setArcParent(unArc);
							if (arcs.get(arcs.indexOf(unArc)).getA().equals(stopIdStart)) {
								child.setDistanceA(stimes.getStop_sequence());
								child.setDistanceB(currentSequence - stimes.getStop_sequence());
							} else {
								child.setDistanceB(stimes.getStop_sequence());
								child.setDistanceA(currentSequence - stimes.getStop_sequence());
							}
							if (childs.contains(child)) {
								childs.get(childs.indexOf(child)).updateDist(child);
							} else {
								childs.add(child);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Retourne la listes gares de correspondances (Optimisation par le délai le
	 * plus court d'un train)
	 * 
	 * @param UICStart
	 * @param UICEnd
	 * @return
	 */
	public List<StopResult> findFastPath(String UICStart, String UICEnd) {
		this.UICStart = UICStart;
		this.UICEnd = UICEnd;
		Arc parentStart = null, parentEnd = null;
		List<Arc> arcsAdded = new ArrayList<Arc>();
		ChildNode childStart = null, childEnd = null;

		// Si le point de départ n'est pas un noeud parent
		if (!parentNode.contains(UICStart)) {
			// on récupère les éléments du noeud enfant pour la gare de départ
			childStart = childs.get(childs.indexOf(new ChildNode(UICStart)));
			// on sauvegarde l'arc parent
			parentStart = childStart.getArcParent();
			// on supprime l'arc parent de ce noeud
			arcs.remove(parentStart);
			// que l'on remplace par deux arcs en définissant ce noeud enfant
			// comme un noeud parent
			Arc arcA = new Arc();
			arcA.setA(parentStart.getA());
			arcA.setB(childStart.getIdChild());
			arcA.setDistance(childStart.getDistanceA());
			arcsAdded.add(arcA);
			Arc arcB = new Arc();
			arcB.setA(parentStart.getB());
			arcB.setB(childStart.getIdChild());
			arcB.setDistance(childStart.getDistanceB());
			arcsAdded.add(arcB);
		}

		if (!parentNode.contains(UICEnd)) {
			childEnd = childs.get(childs.indexOf(new ChildNode(UICEnd)));
			parentEnd = childEnd.getArcParent();
			arcs.remove(parentEnd);
			Arc arcA = new Arc();
			arcA.setA(parentEnd.getA());
			arcA.setB(childEnd.getIdChild());
			arcA.setDistance(childEnd.getDistanceA());
			arcsAdded.add(arcA);
			Arc arcB = new Arc();
			arcB.setA(parentEnd.getB());
			arcB.setB(childEnd.getIdChild());
			arcB.setDistance(childEnd.getDistanceB());
			arcsAdded.add(arcB);
		}

		if (arcsAdded.size() > 0) {
			// On insère dans le graphe les arcs créés à partir des noeuds
			// enfants
			arcs.addAll(arcsAdded);
		}
		settledNodes = new HashSet<String>();
		unSettledNodes = new HashSet<String>();
		distance = new HashMap<String, Integer>();
		predecessors = new HashMap<String, String>();
		distance.put(UICStart, 0);
		unSettledNodes.add(UICStart);

		while (unSettledNodes.size() > 0) {
			String node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}

		List<StopResult> result = new LinkedList<StopResult>();
		List<String> path = getPath();
		if (path != null && path.size() > 0) {
			for (String oneStop : path) {
				StopResult sr = new StopResult();
				sr.setCodeUIC(oneStop);
				sr.setStop_id(getStopIdByUICCode(oneStop));
				if (UICStart.equals(oneStop) && parentStart != null) {
					String parents[] = new String[2];
					parents[0] = parentStart.getA();
					parents[1] = parentStart.getB();
					sr.setParentUIC(parents);
				} else if (UICEnd.equals(oneStop) && parentEnd != null) {
					String parents[] = new String[2];
					parents[0] = parentEnd.getA();
					parents[1] = parentEnd.getB();
					sr.setParentUIC(parents);
				}
				result.add(sr);
			}
		}

		// On remet le graphe dans son état d'origine
		if (arcsAdded.size() > 0) {
			arcs.removeAll(arcsAdded);
		}
		if (parentEnd != null) {
			arcs.add(parentEnd);
		}
		if (parentStart != null) {
			arcs.add(parentStart);
		}

		// On retourne le résultat
		return result;
	}

	/**
	 * Methode permettant de calculer la distance minimal avec un noeud
	 * 
	 * @param node
	 *            Le noeud d'arrivée
	 */
	private void findMinimalDistances(String node) {

		for (Arc unArc : arcs) {
			int value = 0;
			String nextNode = null;
			if (unArc.getA().equals(node) && !settledNodes.contains(unArc.getB())) {
				nextNode = unArc.getB();
				value = unArc.getDistance();
			} else if (unArc.getB().equals(node) && !settledNodes.contains(unArc.getA())) {
				nextNode = unArc.getA();
				value = unArc.getDistance();
			}
			// Nouveal arc
			if (nextNode != null) {
				// Si distance pour ce neoud == null
				if (getShortestDistance(nextNode) > getShortestDistance(node) + value) {
					distance.put(nextNode, getShortestDistance(node) + value);
					predecessors.put(nextNode, node);
					unSettledNodes.add(nextNode);
				}
			}

		}
	}

	/**
	 * Méthode récupérant le noeud le plus proche en fonction d'un liste de
	 * noeuds
	 * 
	 * @param vertexes
	 *            La liste de noeuds ou l'on veut récupérer le noeud le moins
	 *            loin
	 * @return Le noeud le plus proche
	 */
	private String getMinimum(Set<String> vertexes) {
		String noeudLePlusProche = UICStart;
		for (String vertex : vertexes) {
			if (noeudLePlusProche != null && noeudLePlusProche.equals(UICStart)) {
				noeudLePlusProche = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(noeudLePlusProche)) {
					noeudLePlusProche = vertex;
				}
			}
		}
		return noeudLePlusProche;
	}

	/**
	 * Methode renvoyant le poids du plus court chemin avec un noeud destination
	 * 
	 * @param destination
	 *            Le noeud de destination
	 * @return Le poids du plus court chemin
	 */
	private int getShortestDistance(String destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * cette methode retourne le chemin entre le point de depart et le point
	 * d'arrivee et null si il n'y a pas de chemin
	 * 
	 * @param target
	 *            Le noeud d'arrivee
	 * @return La liste des noeuds parcourus
	 */
	private List<String> getPath() {
		List<String> path = new LinkedList<String>();
		String step = UICEnd;
		// on verifie si il existe un chemin
		if (predecessors.get(step) == null) {
			return path;
		}
		path.add(step);
		// System.out.println(predecessors);
		// System.out.println(distance);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// on remet le chemin dans l'ordre correct
		Collections.reverse(path);
		return path;
	}

	/**
	 * Calcule la durée de trajet entre deux arrêts
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static int getDelta(StopTime a, StopTime b) {
		int result = 0;
		try {
			int aT = a.getHours() * 3600 + a.getMinutes() * 60 + a.getSeconds();
			aT = (a.isMidnightJump()) ? aT += 24 * 3600 : aT;
			int bT = b.getHours() * 3600 + b.getMinutes() * 60 + b.getSeconds();
			bT = (b.isMidnightJump()) ? aT += 24 * 3600 : bT;

			if (bT > aT)
				result = bT - aT;
			else
				result = aT - bT;
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Retourne le stop_id pour un code UIC
	 * 
	 * @return the uicToStopID
	 */
	public String getStopIdByUICCode(String codeUIC) {
		return uicToStopID.get(codeUIC);
	}

}
