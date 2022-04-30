package railwayticketbooking;

import java.util.Scanner;

public class RailwayRunner {

	public static void main(String[] args) {
		RailwayTicket obj = new RailwayTicket();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the slots");
		int book = scan.nextInt();
		int rac = scan.nextInt();
		int waiting = scan.nextInt();
		obj.berthCreation(book, rac, waiting);
		boolean bool = true;
		while (bool) {
			System.out.println("1.Book Ticket\n2.Cancel Ticket\n3.Print Booked Tickets\n4.Print available tickets");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				int id = obj.createId();
				System.out.println("Enter the number of passengers");
				int passNumber = scan.nextInt();
				for (int i = 0; i < passNumber; i++) {
					System.out.println("1.Upper berth\n2.Middle berth\n3.Lower berth");
					System.out.println("Enter the berth type");
					int val = scan.nextInt();
					BerthType type = null;
					switch (val) {
					case 1: {
						type = BerthType.Upper;
						break;
					}
					case 2: {
						type = BerthType.Middle;
						break;
					}
					case 3: {
						type = BerthType.Lower;
						break;
					}
					}
					scan.nextLine();
					System.out.println("Enter the name");
					String name = scan.nextLine();
					System.out.println("Enter the age");
					int age = scan.nextInt();
					Passenger pass = ObjectSetter.passengerSetter(name, id, age);
					System.out.println("Passenger id " + id);
					try {
						System.out.println(obj.bookBerth(pass, id, type));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				System.out.println("Enter the ticket id to cancel");
				int passid = scan.nextInt();
				System.out.println(obj.cancelTicket(passid));
				break;
			case 3:
				System.out.println(obj.showTicket());
				break;
			case 4:
				System.out.println(obj.availableTicket());
				break;

			default:
				break;
			}
		}
		scan.close();
	}

}
