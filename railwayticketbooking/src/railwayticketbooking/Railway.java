package railwayticketbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Railway {
	Map<BerthType, List<Integer>> berth = new HashMap<BerthType, List<Integer>>();
	Map<Integer, List<Passenger>> ticket = new HashMap<Integer, List<Passenger>>();
	Map<Integer, Ticket> ticketInfo = new HashMap<Integer, Ticket>();

	public String book(Passenger info, int id, BerthType type, Ticket tick) {
		if (berth.get(type).size() == 0) {
			book(info, id, type, tick);
		}
		ticketInfo.put(id, tick);
		List<Passenger> arr = ticket.get(id);
		if (arr == null) {
			arr = new ArrayList<Passenger>();
		}
		arr.add(info);
		return null;
	}
}