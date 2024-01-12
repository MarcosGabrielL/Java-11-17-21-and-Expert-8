package java8.Expert.and.Java70.cursoJavaAvancado.models;

public class Contact {

	private String name;
	private int number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Contact(String name, int number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	
}
