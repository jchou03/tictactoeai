import java.util.ArrayList;

public class Node<E> {
	private E data;
	private ArrayList<Node> pointers;
	private Node<E> parent;
	
	public Node(E data) {
		this.data = data;
		pointers = new ArrayList<Node>();
		parent = null;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public ArrayList<Node> getPointers(){
		return pointers;
	}
	public void setArrayList(ArrayList<Node> pointers) {
		this.pointers = pointers;
	}
	public Node<E> getParent(){
		return parent;
	}
	public void setParent(Node<E> parent) {
		this.parent = parent;
	}
}
