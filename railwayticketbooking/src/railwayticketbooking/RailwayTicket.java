package railwayticketbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RailwayTicket {
	int ticketid = 0;
	int passengerid = 0;
	Map<BerthType, List<Berth>> berth = new HashMap<BerthType, List<Berth>>();
	Map<Integer, List<Passenger>> ticket = new HashMap<Integer, List<Passenger>>();
	Map<Integer, List<Berth>> bookedslots = new HashMap<Integer, List<Berth>>();
	Map<String, List<Passenger>> rwTickets = new HashMap<String, List<Passenger>>();

	int totalBerth = 0;
	int totalRac = 0;
	int totalWaiting = 0;

	public String berthCreation(int berthNum, int rac, int waiting) {
		this.totalBerth = berthNum;
		this.totalRac = rac;
		this.totalWaiting = waiting;
		int k = 1;
		List<Berth> list = new ArrayList<Berth>();
		for (int i = 1; i <= berthNum / 3; i++) {
			Berth berObj = new Berth();
			berObj.setSeatNo(k++);
			berObj.setType(BerthType.Upper);
			list.add(berObj);
		}
		berth.put(BerthType.Upper, list);
		list = new ArrayList<Berth>();
		for (int i = 1; i <= berthNum / 3; i++) {
			Berth berObj = new Berth();
			berObj.setSeatNo(k++);
			berObj.setType(BerthType.Middle);
			list.add(berObj);
		}
		berth.put(BerthType.Middle, list);
		list = new ArrayList<Berth>();
		for (int i = 1; i <= berthNum / 3; i++) {
			Berth berObj = new Berth();
			berObj.setSeatNo(k++);
			berObj.setType(BerthType.Lower);
			list.add(berObj);
		}
		berth.put(BerthType.Lower, list);
//		rwTickets.put("RAC", new ArrayList<Passenger>());
//		rwTickets.put("Waiting", new ArrayList<Passenger>());
//		list = new ArrayList<Berth>();
//		for (int i = 1; i <= totalRac; i++) {
//			Berth berObj = new Berth();
//			berObj.setSeatNo(k++);
//			berObj.setType(BerthType.SIDE_LOWER_BIRTH);
//			list.add(berObj);
//		}
//		berth.put(BerthType.SIDE_LOWER_BIRTH, list);
		rwTickets.put("RAC", new ArrayList<Passenger>());
		rwTickets.put("Waiting", new ArrayList<Passenger>());
		return "creation succesful";
	}

	public String bookBerth(Passenger info, int id, BerthType type) throws Exception {
		List<Passenger> arrList = ticket.get(id);
		if (arrList == null) {
			arrList = new ArrayList<Passenger>();
			ticket.put(id, arrList);
			info.setStatus("Booked");
		}
		info.setStatus("Booked");
		arrList.add(info);
		List<Berth> book = bookedslots.get(id);
		if (book == null) {
			book = new ArrayList<Berth>();
			bookedslots.put(id, book);
		}
		if (berth.get(type).size() == 0) {
			if (berth.get(BerthType.Upper).size() != 0) {
				arrList.remove(id);
				return bookBerth(info, id, BerthType.Upper);
			} else if (berth.get(BerthType.Middle).size() != 0) {
				arrList.remove(id);
				return bookBerth(info, id, BerthType.Middle);
			} else if (berth.get(BerthType.Lower).size() != 0) {
				arrList.remove(id);
				return bookBerth(info, id, BerthType.Lower);
			} else {
				if (rwTickets.get("RAC").size() < totalRac) {
					arrList = rwTickets.get("RAC");
					arrList.add(info);
					info.setStatus("RAC");
					return "RAC Booked";
				} else if (rwTickets.get("RAC").size() >= totalRac && rwTickets.get("Waiting").size() < totalWaiting) {
					arrList = rwTickets.get("Waiting");
					arrList.add(info);
					info.setStatus("Waiting");
					return "";
				} else {
					ticket.remove(id);
					return "No tickets available";
				}
			}
		}
		Berth berthObj = berth.get(type).get(0);
		berth.get(type).remove(0);
		book.add(berthObj);
		return "";
	}

	public String showTicket() {
		String out = "";
		Set<Integer> keys = ticket.keySet();
		for (int key : keys) {
			out += ticket.get(key).toString();
		}
		return out;
	}

	public String availableTicket() {
		String avail = "";
		avail += "Upper berth " + berth.get(BerthType.Upper).size() + "\n";
		avail += "Middle berth " + berth.get(BerthType.Middle).size() + "\n";
		avail += "Lower berth " + berth.get(BerthType.Lower).size() + "\n";
		avail += "RAC " + (totalRac - rwTickets.get("RAC").size()) + "\n";
		avail += "Waiting " + (totalWaiting - rwTickets.get("Waiting").size()) + "\n";
		return avail;
	}

	public String cancelTicket(int id) {
		int size = ticket.get(id).size();
		String status = ticket.get(id).get(0).getStatus();
		ticket.remove(id);
		if (status.equals("Booked")) {
			for (int i = 0; i < size; i++) {
				List<Passenger> rac = rwTickets.get("RAC");
				List<Passenger> wait = rwTickets.get("Waiting");
				Passenger obj = rac.get(0);
				if (obj.passengerid != id && obj.getStatus().equals("RAC") && obj != null) {
					rac.remove(0);
					obj.setStatus("booked");
					if (wait.size() != 0) {
						Passenger obj1 = wait.get(0);
						wait.remove(0);
						obj1.setStatus("RAC");
						List<Passenger> arr1 = rwTickets.get("RAC");
						arr1.add(obj1);
					}
				}
			}
			return "removed";
		} else if (status.equals("RAC")) {
			List<Passenger> rac = rwTickets.get("RAC");
			List<Passenger> wait = rwTickets.get("Waiting");
			ticket.remove(id);
			for (int i = 0; i < size; i++) {
				Passenger obj = wait.get(0);
				if (obj.getPassengerid() != id && obj.getStatus().equals("Waiting") && wait.get(0) != null) {
					Passenger obj1 = wait.get(0);
					wait.remove(0);
					obj1.setStatus("RAC");
					List<Passenger> arr1 = rwTickets.get("RAC");
					arr1.add(obj1);
				}
			}
			for (int i = 0; i < rac.size(); i++) {
				Passenger obj = rac.get(i);
				if (obj.getPassengerid() == id) {
					rac.remove(id);
				}
			}
			return "removed";
		} else {
			List<Passenger> waiting = rwTickets.get("Waiting");
			ticket.remove(id);
			for (int i = 0; i < waiting.size(); i++) {
				if (waiting.get(i).getPassengerid() == id) {
					waiting.remove(i);
					return "removed";
				}
			}
		}
		return "not found";
	}

	public int createId() {
		return ticketid++;
	}
}