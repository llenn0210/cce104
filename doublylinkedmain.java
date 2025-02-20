package doublyLinked;
import java.util.Scanner;
public class doublylinkedmain {

	public static void main(String[] args) {
		String names;
		Scanner scan = new Scanner(System.in);
		
		Queue queue = new Queue();
		
		while(true) {
			
			System.out.println("\n===Queue Operations==="+
			"\n1. Enqueue at End"+
			"\n2. Enqueue at Front"+
			"\n3. Dequeue Specific String"+
			"\n4. Display"+
			"\n5. Exit");
			System.out.print("Enter your Choice: ");
			String	option = scan.next();
			switch(option) {
			
			case "1":
				System.out.print("Enter value to enqueu at End: ");
				names = scan.next();
				queue.enqueuEnd(names);
				System.out.print("\""+ names +"\"" +" enqueued to the front of the queue");
				break;
			case "2":
				System.out.print("Enter value to enqueue at Front: ");
				names = scan.next();
				queue.enqueueFront(names);
				System.out.print("\""+ names +"\"" +" enqueued to the front of the queue");
				break;
			case "3":
				System.out.print("Dequeue a string: ");
				names = scan.next();
				queue.dequeue(names);
				System.out.print("\""+ names +"\"" +" is Dequeue");
				break;
			case "4":
				queue.display();
				break;
			case"5":
				System.exit(0);
				break;
			
			}
			
			
			
			
		}
	}

}
