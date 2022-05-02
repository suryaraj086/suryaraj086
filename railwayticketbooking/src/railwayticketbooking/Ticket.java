package railwayticketbooking;

public class Ticket {
	long departureTime;
	long seatNo;
	String ticketStatus;
	int passengers;
	int ticketNo;
	int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}

	public long getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(long seatNo) {
		this.seatNo = seatNo;
	}

	@Override
	public String toString() {
		return "Ticket [departureTime=" + departureTime + ", seatNo=" + seatNo + ", ticketStatus=" + ticketStatus
				+ ", passengers=" + passengers + ", ticketNo=" + ticketNo + ", price=" + price + "]";
	}

}
