import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Graphe {
	protected ArrayList<Sommet> listeSommet;
	protected int lastId;
	protected String name;

	public int getNbSommet() {
		return this.listeSommet.size();
	}

	//Creation d'un graphe vierge
	//lastId Correspond au dernier ID pris, afin de pouvoir ajouter un Sommet avec un ID disponible, sans avoir à parcourir la listeSommet à chaque fois
	public Graphe(String name){
		listeSommet = new ArrayList<Sommet>();
		this.lastId = 0;
		this.name = name;
	}

	//Retourne une ArrayList contenant tous les Sommets presents dans le Graphe
	public ArrayList<Sommet> getListeSommet(){
		return this.listeSommet;
	}

	//Retourne le nombre de Sommet de type Page present dans le Graphe
	public int getNbPage(){
		int nbPage = 0;

		for(Sommet s : this.listeSommet){
			if(s instanceof Page){
				nbPage++;
			}
		}

		return nbPage;
	}

	//Retourne le nombre de Sommet de type user present dans le Graphe
	public int getNbUser(){
		int nbUser = 0;

		for(Sommet s : this.listeSommet){
			if(s instanceof User){
				nbUser++;
			}
		}

		return nbUser;
	}

	//Ajoute un User à listeSommet avec pour ID lastId et incrémente se dernier de 1
	public User addUser(int age, String name){
		User u = new User(this.lastId, age, name);
		listeSommet.add(u);
		lastId++;
		return u;
	}

	//Ajoute un User avec un ID donné en parametre, en verifiant si celui-ci n'est pas deja utilisé ( Si il l'est, l'ajout n'a pas lieu )
	public void addUser(int id, int age, String name){
		boolean exist = false;
		for(Sommet s : this.listeSommet){
			if(s.getNumero() == id){
				exist = true;
			}
		}
		if(!exist){
			listeSommet.add(new User(id, age, name));
			if(id>this.lastId)
				this.lastId = id;
		}
	}
	
	//Ajoute une Page à listeSommet avec pour ID lastId et incrémente se dernier de 1
	public Page addPage(String name, User admin){
		Page p = new Page(this.lastId,name, admin);
		listeSommet.add(new Page(this.lastId, name, admin));
		lastId++;
		return p;
	}
	
	//Ajoute une Page avec un ID donné en parametre, en verifiant si celui-ci n'est pas deja utilisé ( Si il l'est, l'ajout n'a pas lieu )
	public void addPage(int id, String name, User admin){
		boolean exist = false;
		for(Sommet s : this.listeSommet){
			if(s.getNumero() == id){
				exist = true;
			}
		}
		if(!exist){
			listeSommet.add(new Page(id, name, admin));
			if(id>this.lastId)
				this.lastId = id;
		}
	}
	
	//Retourne le nom du graphe
	public String getName(){
		return this.name;
	}
	
	//Retourne le nombre d'arcs total present dans le graphe
	public int getNbArc() {
		int nbArc = 0;
		for (Sommet s : this.listeSommet) {
			nbArc += s.getV_sortant().size();
		}
		return nbArc;
	}
	
	//Retourne un ArrayList contenant la liste des Sommets triés par Nom present dans le Graphe, en utilisant la classe NameComparator qui implémente l'interface Comparator<Sommet>
	public ArrayList<Sommet> getSommetByName() {
		ArrayList<Sommet> ordered = this.listeSommet;
		Collections.sort(ordered, new NameComparator());
		return ordered;
	}
	
	//Retourne un ArrayList contenant la liste des Sommets triés par degré sortant present dans le Graphe, en utilisant la classe NameComparator qui implémente l'interface Comparator<Sommet>
	public ArrayList<Sommet> getSommetByDegree() {
		ArrayList<Sommet> ordered = this.listeSommet;
		Collections.sort(ordered, new IdComparator());
		return ordered;
	}
	
	//Retourne une Hashtable ayant pour clés les Sommets à l'origine des relations, et pour valeurs des LinkedList contenant l'ensemble des Sommets avec lesquels ils sont liés. 
	public Hashtable<Sommet, LinkedList<Sommet>> getArc() {
		Hashtable<Sommet, LinkedList<Sommet>> list = new Hashtable<Sommet, LinkedList<Sommet>>();

		for (Sommet s : this.listeSommet) {
			if (s instanceof User) {
				LinkedList<Sommet> temp = s.getV_sortant();
				list.put(s, temp);
			}

		}

		return list;
	}
	
	//Retourne un Sommet d'après son nom
	public Sommet getSommet(String name) {

		for (Sommet s : this.listeSommet) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	//Retourne un Sommet d'après son ID
	public Sommet getSommet(int id) {
		for (Sommet s : this.listeSommet) {
			if (s.getNumero() == id) {
				return s;
			}
		}
		return null;
	}

	//Retourne l'Age moyen des utilisateurs, et 0 si le graphe ne contient aucun utilisateur
	public float getAgeMoyen() {
		int ageM = 0;
		for (Sommet s : this.listeSommet) {
			if (s instanceof User) {
				ageM += ((User) s).getAge();
			}
		}
		if(this.getNbUser() > 0)
			return ageM / this.getNbUser();
		else
			return 0;
	}
	
	//Retourne un HashSet contenant la liste des Users qui sont adminitrateurs de Page
	public HashSet<User> getAdminUsers() {
		HashSet<User> listeAdmins = new HashSet<User>();
		for(Sommet s : this.listeSommet){
			if(s instanceof Page){
				listeAdmins.add( ((Page) s).getAdmin() );
			}
		}
		return listeAdmins;
	}
	
	//Supprime un Sommet du graphe via son ID, et supprime toutes les pages dont l'utilisateurs était administrateur
	public void deleteSommet(int id) {
		Sommet sToDelete = this.getSommet(id);
		ArrayList<Sommet> listSommetToRemove = new ArrayList<Sommet>();
		
		for (Sommet s : this.listeSommet) {
			if(s instanceof Page){
				if(((Page)s).getAdmin().getNumero() == sToDelete.getNumero()){
					listSommetToRemove.add(s);
				}
			}
			s.getV_sortant().remove(sToDelete);
		}
		
		for(Sommet s : listSommetToRemove){
			this.listeSommet.remove(s);
		}
		
		this.listeSommet.remove(sToDelete);

	}
	
	//Charge le graphe ayant pour nom "fileName" et retourne celui-ci ( ou null si un des fichiers n'a pas pu être lu )
	public static Graphe loadGraphe(String fileName) {
		//Contient le nom des fichiers dans lesquels sont stockés les données d'un graphe
		String[] fileNames = {"users.txt", "pages.txt", "relation_user_page.txt"};
		Graphe g = new Graphe(fileName);
		
		BufferedReader br = null;
		try {
			//On boucle temps qu'on a pas lu tout les fichiers necessaires
			for(int i=0; i<fileNames.length; i++){
				br = new BufferedReader(new FileReader(g.name + "-" + fileNames[i]));
				String line;
				
				while((line = br.readLine()) != null){
					//On recupere, dans un tableau, l'ensemble des informations stocké sur la ligne actuellement lu du fichier
					String[] infos = line.split("[|]");
					switch(i){
					//Si le fichier en cours de lecture est users, on créer les utilisateurs
					case 0:
						g.addUser(Integer.parseInt(infos[0]), Integer.parseInt(infos[2]), infos[1]);
						break;
					
					//Si le fichier en cours de lecture est pages, on créer les pages
					case 1:
						g.addPage(Integer.parseInt(infos[0]), infos[1], (User) g.getSommet(Integer.parseInt(infos[2])));
						break;
						
					//Si le fichier en cours de lecture est relation_user_page, on créer les differentes relations entre les Sommets
					case 2:
						for(int y=1; y<infos.length; y++){
							User temp = (User) g.getSommet(Integer.parseInt(infos[0]));
								g.addRelation(temp, g.getSommet(Integer.parseInt(infos[y])));
						}
						break;
					}
				}
				
			}
			
			//En cas d'erreur, on renvois null
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Le fichier n'existe pas");
			return null;
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier");
			return null;
		}finally{
			try {
				br.close();
			} catch (Exception e) {
				return null;
			}
		}
		return g;
	}
	
	//Supprime l'arc entre orig et cible
	public void deleteArc(User orig, Sommet cible) {
		User t = (User) this.getSommet(orig.getName());
		if(t != null){
			if(cible instanceof Page){
				t.unlike_page((Page)cible);
			}
			else if(cible instanceof User){
				t.supprimer_ami((User) cible);
			}
		}
	}
	
	// Stocke toutes les relations des utilisateurs dans le fichier nommÃ©
		// relation_user_page.txt
		public void relationship() throws IOException {

			BufferedWriter b = new BufferedWriter(new FileWriter(this.name + "-relation_user_page.txt"));

			for (Map.Entry<Sommet, LinkedList<Sommet>> entry : this.getArc().entrySet()) {
				b.write(entry.getKey().getNumero() + "|");
				LinkedList<Sommet> sommets = entry.getValue();
				for (Sommet s : sommets) {
					if (s instanceof User)
						b.write(s.getNumero() + "|");
					else if (s instanceof Page)
						b.write(s.getNumero() + "|");
				}
				b.write("\n");
			}
			b.close();
		}
	
		// Stockage de tous les users de l'application dans le fichier users.txt et dans pages.txt pour les pages; chacun des fichiers contient toutes les infos relatives Ã  chaque element
		public void users_pages() throws IOException {
			BufferedWriter b = new BufferedWriter(new FileWriter(this.name + "-users.txt"));
			BufferedWriter c = new BufferedWriter(new FileWriter(this.name + "-pages.txt"));
			for(Sommet x : this.listeSommet){
				if(x instanceof Page){
					c.write(x + "|" + ((Page)x).getAdmin().getNumero() + "\n");
				}
				else if(x instanceof User){
					b.write(x + "\n");
				}
			}
			b.close();c.close();
		}
		
		//retourne la distance minimal entre le Sommet s et chaque Sommet du graphe
		public Hashtable<Sommet, Integer> getDistanceBetweenSommets(Sommet s){
			//Hashtable ayant pour clés les Sommets du graphe, et pour valeurs la distance minimale entre S et ceux-ci
			Hashtable<Sommet, Integer> dist = new Hashtable<Sommet, Integer>();
			//ArrayList contenant les Sommets du graphe
			ArrayList<Sommet> listSommets = new ArrayList<Sommet>(this.listeSommet);
			
			//On initialise toute les distances à 10000000
			for(Sommet tempS : listSommets){
					dist.put(tempS, 10000000);
			}
			
			//On modifie la distance entre s et lui même, en la mettant à 0
			dist.put(s, 0);
			
			while(!listSommets.isEmpty()){
				Sommet plusPetitSommet = null;
				
				for(Sommet tempS : listSommets){
					//Si la boucle en est à son premier tour, on definie temporairement le premier Sommet de listSommets comme étant celui avec la plus petite distance
					if(plusPetitSommet == null){
						plusPetitSommet = tempS;
					}
					else if(dist.get(tempS) < dist.get(plusPetitSommet)){
						plusPetitSommet = tempS;
					}
				}
					
				listSommets.remove(plusPetitSommet);
				for(Sommet amiS : plusPetitSommet.getV_sortant()){
					int d = dist.get(plusPetitSommet) + 1;
					if(d <= dist.get(amiS)){
						dist.put(amiS, d);
					}
				}
				
			}
			
			return dist;
		}
		
		//Ajoute une relation entre u et s ( de u vers s )
		public void addRelation(User u, Sommet s){
			if(s instanceof User)
				u.ajouter_ami((User) s);
			else 
				u.like_page((Page) s);
			this.PageRank();
		}
		
		//Calcule et attribut à chaque Sommet son pagerank
		public void PageRank() {
			// TODO Auto-generated method stub
			int i = 0;
			while (i <= 100) {
				// On fait un iterateur sur les sommets du graphe
				Iterator<Sommet> it = this.listeSommet.iterator();
				while (it.hasNext()) {
					// On récupère chaque sommet
					Sommet a = it.next();
					// On calcule la formule
					double p = 0.15 / Math.abs(this.getNbSommet()) + 0.85
							+ VoisinEntrant(a);
					// On modifie le pagerank (il faut rajouter dans la classe
					// sommet, l'attribut pagerank puis l'initialiser à 1 et ecrire
					// des methode get et set permettant de manipuler cet attribut)
					a.setPagerank(p);

				}

				i = i + 1;
			}

		}
		
		// Cette méthode permet de calculer la deuxième partie de la formule : la
		// somme des pagerank des voisins entrant d'un sommet / degré sortant des
		// voisins entrant
		private double VoisinEntrant(Sommet s1) {
			// TODO Auto-generated method stub
			double compteur = 0;
			// On va dans un premier temps calculer le pagerank d'un voisin entrant
			// divisé par son degré sortant puis dans un deuxieme temps faire la
			// somme
			// Pour stoker le 1er calcule, on va le placer dans une array list
			ArrayList<Double> stock;
			stock = new ArrayList<Double>();
			// On creer un iterateur sur les sommet du graphe
			Iterator<Sommet> it = this.listeSommet.iterator();
			while (it.hasNext()) {
				// On récupère chaque sommet
				Sommet s2 = it.next();
				// On fait un iterateur sur les voisins sortant du sommet récupéré
				Iterator<Sommet> t = s2.getV_sortant().iterator();
				while (t.hasNext()) {
					// On récupère chacun de ses voisin sortant
					Sommet s3 = t.next();
					// Si dans les voisin sortant il y a le sommet en question
					if (s1.getName() == s3.getName()) {
						// On ajoute au Array liste le pagerank du sommet divisé par
						// son degré sortant
						stock.add(s2.getPagerank() / s2.getV_sortant().size());
					}
				}

			}
			// On créer un iterateur sur l'ArrayList des nombre calculé puis on fait
			// la somme
			Iterator<Double> it2 = stock.iterator();
			while (it2.hasNext()) {
				compteur += it2.next();
			}
			return compteur;

		}

		// La valeur maximale du pagerank de tous les sommets
		private double max(ArrayList<Sommet> l) {
			try {
				double max = 0;
				for (Sommet d : l) {
					if (d.getPagerank() >= max)
						max = d.getPagerank();
				}
				return max;
			} catch (Exception e) {
				System.out.println(e);
			}
			return 0;
		}

		// La valeur minimale du pagerank de tous les sommets
		private double min(ArrayList<Sommet> l) {
			try {
				double min = 1000000000;
				for (Sommet d : l) {
					if (d.getPagerank() <= min)
						min = d.getPagerank();
				}
				return min;
			} catch (Exception e) {
				System.out.println(e);
			}
			return 0;
		}

		// On determine le plus influent de l'application
		public String PlusInfluent() {
			// TODO Auto-generated method stub
			double plus = max(this.listeSommet);
			String s = "Le(s) plus influent(s) est(sont): \n";
			try {
				for (Sommet x : this.listeSommet) {
					if (x.getPagerank() == plus)
						s += "-" + x.getName() + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;
		}

		// On determine le moins influent de l'application
		public String MoinsInfluent() {
			// TODO Auto-generated method stub
			double moins = min(this.listeSommet);
			String s = "Le(s) moins influent(s) est(sont): \n";
			try {
				for (Sommet x : this.listeSommet) {
					if (x.getPagerank() == moins)
						s += "-" + x + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;
		}

		public String PlusInfluent_Page() {
			String s = "La page la plus influente est: \n";
			ArrayList<Sommet> list = new ArrayList<Sommet>();
			for (Sommet y : this.listeSommet) {
				if (y instanceof Page) {
					list.add(y);
				}
			}
			double plus = max(list);
			try {
				for (Sommet x : list) {
					if (x.getPagerank() == plus)
						s += "-" + x.getName() + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;
		}

		public String PlusInfluent_User() {
			String s = "L'user le plus influent est: \n";
			ArrayList<Sommet> list = new ArrayList<Sommet>();
			for (Sommet y : this.listeSommet) {
				if (y instanceof User)
					list.add(y);
			}
			double plus = max(list);
			try {
				for (Sommet x : list) {
					if (x.getPagerank() == plus)
						s += "-" + x.getName() + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;

		}

		public String MoinsInfluent_Page() {
			String s = "La page la moins influente est: \n";
			ArrayList<Sommet> list = new ArrayList<Sommet>();
			for (Sommet y : this.listeSommet) {
				if (y instanceof Page)
					list.add(y);
			}
			double moins = min(list);
//			System.out.println(moins);
			try {
				for (Sommet x : list) {
					if (x.getPagerank() == moins)
						s += "-" + x.getName() + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;

		}

		public String MoinsInfluent_User() {
			String s = "L'user le moins influent est: \n";
			ArrayList<Sommet> list = new ArrayList<Sommet>();
			for (Sommet y : this.listeSommet) {
				if (y instanceof User)
					list.add(y);
			}
			double moins = min(list);
			try {
				for (Sommet x : list) {
					if (x.getPagerank() == moins)
						s += "-" + x.getName() + "\n";
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return s;
		}
}