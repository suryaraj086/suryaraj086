package railwayticketbooking;

public class ObjectSetter {

	public static Passenger passengerSetter(String name, int id, int age) {
		Passenger pass = new Passenger();
		pass.setName(name);
		pass.setAge(age);
		pass.setPassengerid(id);
		return pass;
	}
}
