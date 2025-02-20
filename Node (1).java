package doublyLinked;

public class Node {
	String names;
	Node prev, next;
	
	public Node(String names) {
		this.names = names;
		this.prev = this.next = null;
	}

}
