package railwayticketbooking;

public class ObjectSetter {

	public static Passenger passengerSetter(String name, int id, int age) {
		Passenger pass = new Passenger();
		pass.setName(name);
		pass.setAge(age);
		pass.setPassengerid(id);
		return pass;
	}

	public static Ticket ticketSetter(int passenger, String status, long departureTime, int ticketNumber) {
		Ticket ticketObj = new Ticket();
		ticketObj.setDepartureTime(departureTime);
		ticketObj.setPassengers(passenger);
		ticketObj.setTicketNo(ticketNumber);
		ticketObj.setTicketStatus("");
		ticketObj.setPrice(100);
		return ticketObj;
	}
}
