import java.util.Comparator;


public class NameComparator implements Comparator<Sommet>{

	@Override
	public int compare(Sommet s1, Sommet s2) {
		return s1.getName().compareToIgnoreCase(s2.getName());
	}

}
