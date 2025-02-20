package doublyLinked;

public class Queue {

	private Node front, rear;
	
	public Queue() {
		this.front = this.rear = null;
		
	
			}
	public void enqueuEnd (String names) {
		Node newNode  = new Node(names);
		if (rear == null) {
			front = rear = newNode;
		}else {
			rear.next = newNode;
			newNode.prev = rear;
			rear = newNode;
		}
		}
	public void enqueueFront (String names) {
		Node newNode  = new Node(names);
		if (front == null) {
			front = rear = newNode;
		}else {
			front.prev = newNode;
			newNode.next  = front;
			front = newNode;
		}
		}
	
	public boolean dequeue(String value) {
		if (front == null) {	
			System.out.println("Queue is empty");
			return false;
			}
		if (front.names.equals(value)) {	
			front = front.next;	
			if (front != null) {
				front.prev = null;
			} else {
				rear = null;
				}
			System.out.println("Dequeued: " + value);
			return true;
			}
		Node current = front;
		while (current.next != null && !current.next.names.equals(value)) {
			current = current.next;
			
			}
		if (current.next == null) {
			System.out.println("Value not found in queue");
			return false;
		}
		if (current.next == rear) {
			rear = current;
			rear.next = null;	
		} else {
			
			Node temp = current.next;
			current.next = temp.next;
			temp.next.prev = current;
		}
		System.out.println("Dequeued: " + value);
		return true;
		}
	
	
	
		public void display() {
			if (front == null) {
				System.out.print("Queue is empty ");
				return;
			}
			Node temp = front;
			System.out.print("Queue: ");
				while (temp != null) {
					System.out.print(temp.names + " ");
					temp = temp.next;
					
				}
				System.out.println();
			
		}
	
}
