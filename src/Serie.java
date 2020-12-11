
public class Serie {

	private int id;
	private String nom, genre;
	private static String sql = "CREATE TABLE Serie ( " + "ID INTEGER  AUTO_INCREMENT, " + "NOM varchar(40) NOT NULL, "
			+ "GENRE varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";

	public Serie(String n, String g) {
		id = -1;
		nom = n;
		genre = g;
	}

	public Serie findAll() {
		return null;

	}

}
