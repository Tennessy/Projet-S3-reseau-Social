import java.util.*;

public abstract class Sommet {
	
	public Sommet(int num) {
		// Initialise le sommet en lui donnant un numero d'index et une valeur
		this.numero = num;
		v_sortant = new LinkedList<Sommet>();
	}

	protected LinkedList<Sommet> v_sortant;
	private int numero;

	// Création des accesseurs publics
	public LinkedList<Sommet> getV_sortant() {
		return v_sortant;
	}

	public int getNumero() {
		return numero;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Sommet)) {
			return false;
		}
		Sommet other = (Sommet) obj;
		if (numero != other.numero) {
			return false;
		}
		return true;
	}

}