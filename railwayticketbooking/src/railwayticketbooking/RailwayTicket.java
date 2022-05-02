package railwayticketbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RailwayTicket {
	int ticketid = 0;
	int passengerid = 0;
	Map<BerthType, List<Berth>> berth = new HashMap<>();
	Map<Integer, List<Passenger>> ticket = new HashMap<>();
	Map<Integer, List<Berth>> bookedslots = new HashMap<>();
	Map<String, List<Passenger>> rwTickets = new HashMap<>();
//	Map<Integer, Ticket> ticketDetails = new HashMap<>();

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
		rwTickets.put("RAC", new ArrayList<Passenger>());
		rwTickets.put("Waiting", new ArrayList<Passenger>());
		return "creation successful";
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
				arrList.remove(arrList.size() - 1);
				return bookBerth(info, id, BerthType.Upper);
			} else if (berth.get(BerthType.Middle).size() != 0) {
				arrList.remove(arrList.size() - 1);
				return bookBerth(info, id, BerthType.Middle);
			} else if (berth.get(BerthType.Lower).size() != 0) {
				arrList.remove(arrList.size() - 1);
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
					return "Waiting booked";
				} else {
					ticket.remove(id);
					return "No tickets available";
				}
			}
		}
		Berth berthObj = berth.get(type).get(0);
		berth.get(type).remove(0);
		book.add(berthObj);
		info.setSeatNo(berthObj.getSeatNo());
		return "booked successfully and seat number is " + berthObj.getSeatNo();
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

	public String cancelIndividual(int id, int val) throws Exception {
		List<Passenger> pass = ticket.get(id);
		int size = 1;
		nullChecker(pass);
		Passenger passobj = ticket.get(id).get(val);
		String status = passobj.getStatus();
		List<Berth> slots = bookedslots.get(id);
		pass.remove(val);
		if (status.equals("Booked")) {
			for (int i = 0; i < size; i++) {
				List<Passenger> rac = rwTickets.get("RAC");
				List<Passenger> wait = rwTickets.get("Waiting");
				if (rac.size() != 0 && rac.get(0).passengerid != id && rac.get(0).getStatus().equals("RAC")
						&& rac.get(0) != null) {
					Passenger obj = rac.get(0);
					rac.remove(0);
					obj.setStatus("Booked");
					List<Berth> arr = bookedslots.get(obj.getPassengerid());
					if (bookedslots.get(obj.getPassengerid()) == null) {
						arr = new ArrayList<Berth>();
					}
					arr.add(slots.get(0));
					obj.setSeatNo(slots.get(0).getSeatNo());
					bookedslots.put(obj.getPassengerid(), arr);
					if (wait.size() != 0) {
						Passenger obj1 = wait.get(0);
						wait.remove(0);
						obj1.setStatus("RAC");
						List<Passenger> arr1 = rwTickets.get("RAC");
						arr1.add(obj1);
					}
					slots.remove(0);
				} else if (rac.size() == 0) {
					List<Berth> list = berth.get(slots.get(0).getType());
					list.add(slots.get(0));
					berth.put(slots.get(0).getType(), list);
					slots.remove(0);
				}
			}
			return "removed";
		} else if (status.equals("RAC"))

		{
			List<Passenger> rac = rwTickets.get("RAC");
			List<Passenger> wait = rwTickets.get("Waiting");
			for (int i = 0; i < size; i++) {
				if (wait.size() != 0 && wait.get(0).getPassengerid() != id && wait.get(0).getStatus().equals("Waiting")
						&& wait.get(0) != null) {
					Passenger obj1 = wait.get(0);
					wait.remove(0);
					obj1.setStatus("RAC");
					List<Passenger> arr1 = rwTickets.get("RAC");
					arr1.add(obj1);
				}
			}
			for (int i = 0; i < rac.size(); i++) {
				Passenger obj = rac.get(i);
				if (obj.getPassengerid() == id && passobj.getName().equals(obj.getName())) {
					rac.remove(i);
					break;
				}
			}
			return "removed";
		} else {
			List<Passenger> waiting = rwTickets.get("Waiting");
			for (int i = 0; i < waiting.size(); i++) {
				if (waiting.get(i).getPassengerid() == id && passobj.getName().equals(waiting.get(i).getName())) {
					waiting.remove(i);
					break;
				}
			}
			return "removed";
		}
	}

	public String cancelTicket(int id) throws Exception {
		List<Passenger> pass = ticket.get(id);
		nullChecker(pass);
		int size = 1;
		for (int k = 0; k < pass.size(); k++) {
			String status = ticket.get(id).get(k).getStatus();
			List<Berth> slots = bookedslots.get(id);
			if (status.equals("Booked")) {
				for (int i = 0; i < size; i++) {
					List<Passenger> rac = rwTickets.get("RAC");
					List<Passenger> wait = rwTickets.get("Waiting");
					if (rac.size() != 0 && rac.get(0).passengerid != id && rac.get(0).getStatus().equals("RAC")
							&& rac.get(0) != null) {
						Passenger obj = rac.get(0);
						rac.remove(0);
						obj.setStatus("Booked");
						List<Berth> arr = bookedslots.get(obj.getPassengerid());
						if (bookedslots.get(obj.getPassengerid()) == null) {
							arr = new ArrayList<Berth>();
						}
						arr.add(slots.get(0));
						obj.setSeatNo(slots.get(0).getSeatNo());
						bookedslots.put(obj.getPassengerid(), arr);
						if (wait.size() != 0) {
							Passenger obj1 = wait.get(0);
							wait.remove(0);
							obj1.setStatus("RAC");
							List<Passenger> arr1 = rwTickets.get("RAC");
							arr1.add(obj1);
						}
						slots.remove(0);
						pass.remove(0);
					} else if (rac.size() == 0) {
						List<Berth> list = berth.get(slots.get(0).getType());
						list.add(slots.get(0));
						berth.put(slots.get(0).getType(), list);
						slots.remove(0);
					}
				}
//				return "removed";
			} else if (status.equals("RAC")) {
				List<Passenger> rac = rwTickets.get("RAC");
				List<Passenger> wait = rwTickets.get("Waiting");
				for (int i = 0; i < size; i++) {
					if (wait.size() != 0 && wait.get(0).getPassengerid() != id
							&& wait.get(0).getStatus().equals("Waiting") && wait.get(0) != null) {
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
						rac.remove(i);
					}
				}
//				return "removed";
			} else {
				List<Passenger> waiting = rwTickets.get("Waiting");
				for (int i = 0; i < waiting.size(); i++) {
					if (waiting.get(i).getPassengerid() == id) {
						waiting.remove(i);
					}
				}
			}
		}
		ticket.remove(id);
		return "not found";

	}

	public void nullChecker(Object val) throws Exception {
		if (val == null) {
			throw new Exception("Failed");
		}
	}

	public String ticketPersons(int id) throws Exception {
		List<Passenger> list = ticket.get(id);
		nullChecker(list);
		String out = "";
		for (int i = 0; i < list.size(); i++) {
			out += "press " + i + "to delete " + list.get(i);
		}
		return out;
	}

	public int createId() {
		return ticketid++;
	}
}