import java.util.Comparator;

//Class impl�mentant l'interface Comparator, permettant ainsi d'utiliser la m�thode sort de Collection pour trier les Sommets par leur nom
public class NameComparator implements Comparator<Sommet>{

	@Override
	public int compare(Sommet s1, Sommet s2) {
		return s1.getName().compareToIgnoreCase(s2.getName());
	}

}

