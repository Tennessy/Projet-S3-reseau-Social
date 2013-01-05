import java.util.*;
import java.io.*;

public class Admin implements GestionGraphe {
	Graphe graphe;

	public Admin(String nameGraphe) {
		this.loadGraphe(nameGraphe);
	}
	
	public Admin(Graphe g){
		this.graphe = g;
	}
	
	public Admin(){
		
	}
	
	public void creerGraphe(String name){
		this.graphe = new Graphe(name);
	}

	@Override
	public int getNbSommet() {
		return graphe.getNbSommet();
	}

	@Override
	public int getNbArc() {
		int nbArc = 0;
		for (Sommet s : graphe.getListeSommet()) {
			nbArc += s.getV_sortant().size();
		}
		return nbArc;
	}

	@Override
	public ArrayList<Sommet> getSommet() {
		return graphe.getListeSommet();
	}

	@Override
	public ArrayList<Sommet> getSommetByName() {
		ArrayList<Sommet> ordered = graphe.getListeSommet();
		Collections.sort(ordered, new NameComparator());
		return ordered;
	}

	@Override
	public ArrayList<Sommet> getSommetByDegree() {
		ArrayList<Sommet> ordered = graphe.getListeSommet();
		Collections.sort(ordered, new IdComparator());
		return ordered;
	}

	// Renvois les arcs sous forme de LinkedList<Sommet>. Le premier de la liste
	// est l'user � l'origine des liens
	@Override
	public Hashtable<Sommet, LinkedList<Sommet>> getArc() {
		Hashtable<Sommet, LinkedList<Sommet>> list = new Hashtable<Sommet, LinkedList<Sommet>>();

		for (Sommet s : graphe.getListeSommet()) {
			if (s instanceof User) {
				LinkedList<Sommet> temp = s.getV_sortant();
				list.put(s, temp);
			}

		}

		return list;
	}

	@Override
	public Sommet getSommet(String name) {

		for (Sommet s : graphe.getListeSommet()) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public int getNbUser() {
		return graphe.getNbUser();
	}

	@Override
	public int getNbPage() {
		return graphe.getNbPage();
	}

	@Override
	public float getAgeMoyen() {
		int ageM = 0;
		for (Sommet s : graphe.getListeSommet()) {
			if (s instanceof User) {
				ageM += ((User) s).getAge();
			}
		}
		return ageM / graphe.getNbUser();
	}

	public Sommet getSommet(int id) {
		for (Sommet s : graphe.getListeSommet()) {
			if (s.getNumero() == id) {
				return s;
			}
		}
		return null;
	}

	@Override
	public HashSet<User> getAdminUsers() {
		HashSet<User> listeAdmins = new HashSet<User>();
		for(Sommet s : this.graphe.getListeSommet()){
			if(s instanceof Page){
				listeAdmins.add( ((Page) s).getAdmin() );
			}
		}
		return listeAdmins;
	}

	@Override
	public void addUser(int age, String name) {
		graphe.addUser(age, name);

	}

	public void addUser(User u) {
		graphe.addUser(u.getAge(), u.getName());
	}

	@Override
	public void addPage(String name, User admin){
		graphe.addPage(name, admin);

	}

	@Override
	public void deleteSommet(int id) {
		System.out.println("\n --- Suppression de "
				+ graphe.getListeSommet().get(id).getName() + "\n");
		Sommet sToDelete = this.getSommet(id);

		for (Sommet s : graphe.getListeSommet()) {
			s.getV_sortant().remove(sToDelete);
		}

		graphe.getListeSommet().remove(sToDelete);

	}

	@Override
	public void loadGraphe(String fileName) {
		String[] fileNames = {"users.txt", "pages.txt", "relation_user_page.txt"};
		
		this.graphe = new Graphe(fileName);
			
		BufferedReader br = null;
		try {
			for(int i=0; i<fileNames.length; i++){
				br = new BufferedReader(new FileReader(graphe.getName() + "-" + fileNames[i]));
				String line;
				
				while((line = br.readLine()) != null){
					String[] infos = line.split("[|]");
					switch(i){
					case 0:
						graphe.addUser(Integer.parseInt(infos[0]), Integer.parseInt(infos[2]), infos[1]);
						break;
						
					case 1:
						graphe.addPage(Integer.parseInt(infos[0]), infos[1], (User) this.getSommet(Integer.parseInt(infos[2])));
						break;
						
					case 2:
						for(int y=1; y<infos.length; y++){
							User temp = (User) this.getSommet(Integer.parseInt(infos[0]));
							if(this.getSommet(Integer.parseInt(infos[y])) instanceof User)
								temp.ajouter_ami((User) this.getSommet(Integer.parseInt(infos[y])));
							else 
								temp.like_page((Page) this.getSommet(Integer.parseInt(infos[y])));
						}
						break;
					}
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Le fichier n'existe pas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la lecture du fichier");
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur lors de la lecture du fichier");
			}
		}
		
	}

	@Override
	public void deleteArc(User orig, Sommet cible) {
		User t = (User) this.getSommet(orig.getName());
		if(cible instanceof Page){
			t.unlike_page((Page)cible);
		}
		else if(cible instanceof User){
			t.supprimer_ami((User) cible);
		}
		
	}

	// Stocke toutes les relations des utilisateurs dans le fichier nommé
	// relation_user_page.txt
	public void relationship() throws IOException {

		BufferedWriter b = new BufferedWriter(new FileWriter(graphe.getName() + "-relation_user_page.txt"));

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
		BufferedWriter b = new BufferedWriter(new FileWriter(graphe.getName() + "-users.txt"));
		BufferedWriter c = new BufferedWriter(new FileWriter(graphe.getName() + "-pages.txt"));
		for(Sommet x : graphe.getListeSommet()){
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
		ArrayList<Sommet> listSommets = new ArrayList<Sommet>(this.getSommet());
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
	
	

}
