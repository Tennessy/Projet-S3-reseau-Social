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
import java.util.LinkedList;
import java.util.Map;

public class Graphe {
	protected ArrayList<Sommet> listeSommet;
	protected int lastId;
	protected String name;

	public int getNbSommet() {
		return this.listeSommet.size();
	}

	public Graphe(String name){
		listeSommet = new ArrayList<Sommet>();
		this.lastId = 0;
		this.name = name;
	}

	public ArrayList<Sommet> getListeSommet(){
		return this.listeSommet;
	}

	public int getNbPage(){
		int nbPage = 0;

		for(Sommet s : this.listeSommet){
			if(s instanceof Page){
				nbPage++;
			}
		}

		return nbPage;
	}

	public int getNbUser(){
		int nbUser = 0;

		for(Sommet s : this.listeSommet){
			if(s instanceof User){
				nbUser++;
			}
		}

		return nbUser;
	}

	public User addUser(int age, String name){
		User u = new User(this.lastId, age, name);
		listeSommet.add(u);
		lastId++;
		return u;
	}

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
	
	public Page addPage(String name, User admin){
		Page p = new Page(this.lastId,name, admin);
		listeSommet.add(new Page(this.lastId, name, admin));
		lastId++;
		return p;
	}
	
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
	
	public String getName(){
		return this.name;
	}
	
	public int getNbArc() {
		int nbArc = 0;
		for (Sommet s : this.listeSommet) {
			nbArc += s.getV_sortant().size();
		}
		return nbArc;
	}
	
	public ArrayList<Sommet> getSommetByName() {
		ArrayList<Sommet> ordered = this.listeSommet;
		Collections.sort(ordered, new NameComparator());
		return ordered;
	}
	
	public ArrayList<Sommet> getSommetByDegree() {
		ArrayList<Sommet> ordered = this.listeSommet;
		Collections.sort(ordered, new IdComparator());
		return ordered;
	}
	
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
	
	public Sommet getSommet(String name) {

		for (Sommet s : this.listeSommet) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	public Sommet getSommet(int id) {
		for (Sommet s : this.listeSommet) {
			if (s.getNumero() == id) {
				return s;
			}
		}
		return null;
	}

	public float getAgeMoyen() {
		int ageM = 0;
		for (Sommet s : this.listeSommet) {
			if (s instanceof User) {
				ageM += ((User) s).getAge();
			}
		}
		return ageM / this.getNbUser();
	}
	
	public HashSet<User> getAdminUsers() {
		HashSet<User> listeAdmins = new HashSet<User>();
		for(Sommet s : this.listeSommet){
			if(s instanceof Page){
				listeAdmins.add( ((Page) s).getAdmin() );
			}
		}
		return listeAdmins;
	}
	
	public void deleteSommet(int id) {
		Sommet sToDelete = this.getSommet(id);

		for (Sommet s : this.listeSommet) {
			s.getV_sortant().remove(sToDelete);
		}

		this.listeSommet.remove(sToDelete);

	}
	
	public static Graphe loadGraphe(String fileName) {
		String[] fileNames = {"users.txt", "pages.txt", "relation_user_page.txt"};
		//this.listeSommet.clear();
		Graphe g = new Graphe(fileName);
		
		BufferedReader br = null;
		try {
			for(int i=0; i<fileNames.length; i++){
				br = new BufferedReader(new FileReader(g.name + "-" + fileNames[i]));
				String line;
				
				while((line = br.readLine()) != null){
					String[] infos = line.split("[|]");
					switch(i){
					case 0:
						g.addUser(Integer.parseInt(infos[0]), Integer.parseInt(infos[2]), infos[1]);
						break;
						
					case 1:
						g.addPage(Integer.parseInt(infos[0]), infos[1], (User) g.getSommet(Integer.parseInt(infos[2])));
						break;
						
					case 2:
						for(int y=1; y<infos.length; y++){
							User temp = (User) g.getSommet(Integer.parseInt(infos[0]));
							if(g.getSommet(Integer.parseInt(infos[y])) instanceof User)
								temp.ajouter_ami((User) g.getSommet(Integer.parseInt(infos[y])));
							else 
								temp.like_page((Page) g.getSommet(Integer.parseInt(infos[y])));
						}
						break;
					}
				}
				
			}
			
			
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
	
	// Stocke toutes les relations des utilisateurs dans le fichier nommé
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
	
		// Stockage de tous les users de l'application dans le fichier users.txt et dans pages.txt pour les pages; chacun des fichiers contient toutes les infos relatives à chaque element
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
		
		public Hashtable<Sommet, Integer> getDistanceBetweenSommets(Sommet s){
			Hashtable<Sommet, Integer> dist = new Hashtable<Sommet, Integer>();
			ArrayList<Sommet> listSommets = new ArrayList<Sommet>(this.listeSommet);
			for(Sommet tempS : listSommets){
					dist.put(tempS, 10000000);
			}
			dist.put(s, 0);
			
			while(!listSommets.isEmpty()){
				Sommet plusPetitSommet = null;
				
				for(Sommet tempS : listSommets){
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
		
		public void addRelation(User u, Sommet s){
			if(s instanceof User)
				u.ajouter_ami((User) s);
			else 
				u.like_page((Page) s);
		}
}