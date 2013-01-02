import java.util.*;

public abstract class Sommet {
	
	public Sommet(int num, String name) {
		// Initialise le sommet en lui donnant un numero d'index et une valeur
		this.numero = num;
		this.name = name;
		v_sortant = new LinkedList<Sommet>();
	}

	protected LinkedList<Sommet> v_sortant;
	protected int numero;
	protected String name;

	// Cr�ation des accesseurs publics
	public LinkedList<Sommet> getV_sortant() {
		return v_sortant;
	}

	public int getNumero() {
		return numero;
	}
	
	public String getName(){
		return this.name;
	}

}