import java.util.ArrayList;


public class Page extends Sommet {
	
	protected User admin;
	
// Cr�ation d'une page ayant un numero et un nom	
	public Page(int num,String s, User admin) {
		super(num, s);
		this.admin = admin;
	}
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Page)) {
			return false;
		}
		Page other = (Page) obj;
		if(!name.equals(other.getName()))
			return false;
		return true;
	}
	
	public String toString(){
		return numero+"|"+name;
	}
	
	public User getAdmin(){
		return this.admin;
	}

}
