import java.util.*;


public class User extends Sommet{
	private TreeMap<String,Integer> individu;
	// Création d'un user ayant un numero un nom & un age
	//le nom et l'age sont stockés dans une treemap
	public User(int num,int age,String nom) {
		super(num, nom);
		individu.put(nom, age);
		// TODO Auto-generated constructor stub
	}
	// un individu suit un autre
	public void ajouter_ami(User u){
		if(v_sortant.contains(u))
			System.out.println("Utilisateur faisant déja parti des voisins sortants");
		else{
			v_sortant.add(u);
			System.out.println("Utilisateur ajouté");
		}
	}
	// un individu aime une page
	public void like_page(Page p){
		if(v_sortant.contains(p))
			System.out.println("Page faisant deja parti des voisins sortants");
		else{
			v_sortant.add(p);
			System.out.println("Page ajoutee");
		}
	}
	

}
