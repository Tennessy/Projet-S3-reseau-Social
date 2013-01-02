import java.io.*;
import java.util.*;


public class Reseau {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Admin ad = new Admin();
		
		//Test ajout d'utilisateur + affichage info + recuperation User par son nom
		ad.addUser(20, "hjkl");
		ad.addUser(30, "ghjk");
		User ten = (User) ad.getSommet("hjkl");
		System.out.println("Nom : " + ten.getName() + ", Age : " + ten.getAge() +" Index "+ten.getNumero());
		
		User u = new User(2, 15, "azerty");
		ad.addUser(u);
		
		// Test ajout amis
		ten.ajouter_ami(u);
		ten.ajouter_ami(u);
		System.out.println();
		
		//Test ajout page + like page
		ad.addPage("Page1");
		Page p = (Page) ad.getSommet("Page1");
		ten.like_page(p);
		
		System.out.println("Nombre d'utilisateurs : " + ad.getNbUser());
		System.out.println("Nombre de pages : " + ad.getNbPage());
		System.out.println("Age moyen des utilisateurs : " + ad.getAgeMoyen());
		System.out.println("Nombre d'arcs : " + ad.getNbArc());
		
		System.out.println("\n---------Triage par nom---------");
		for(Sommet s : ad.getSommetByName()){
			System.out.println(s.getName());
		}
		System.out.println();
		affichageArcs(ad);
		
		ad.deleteSommet(1);
		affichageArcs(ad);
		
		
		System.out.println("Nombre d'arcs : " + ad.getNbArc());
		
		
			ad.relationship();
	}
	
	// recuperation des arcs et affichage
	public static void affichageArcs(Admin ad){
		for (Map.Entry<Sommet, LinkedList<Sommet>> entry : ad.getArc().entrySet()){
		     System.out.print(entry.getKey().getName()); 
		     LinkedList<Sommet> sommets = entry.getValue();
			 for(Sommet s : sommets){
				 System.out.print(" -> " + s.getName());
			 }
			 System.out.println();
		}
	}

}
