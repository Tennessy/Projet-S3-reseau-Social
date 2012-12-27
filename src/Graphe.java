import java.util.HashSet;

public class Graphe {
	HashSet<Sommet> listeSommet;
	int lastId;
	int nbSommet;
	
	public Graphe(){
		listeSommet = new HashSet<Sommet>();
		this.lastId =0;
		this.nbSommet = 0;
	}
	
	public void addUser(int age, String name){
		listeSommet.add(new User(this.lastId, age, name));
		lastId++;
		this.nbSommet++;
	}
	
	public void addPage(String name){
		listeSommet.add(new Page(this.lastId, name));
		lastId++;
		this.nbSommet++;
	}
	
	public void deleteSommet(int id){
		for(Sommet s : listeSommet){
			if(s.getNumero() == id){
				listeSommet.remove(s);
			}
			else if(s.getV_sortant().contains(id)){
				s.getV_sortant().remove(id);
			}
		}
	}
	
}
