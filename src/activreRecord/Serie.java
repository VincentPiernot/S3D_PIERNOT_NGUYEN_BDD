package activreRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Serie {
	
	private int id;
	private String nom, genre;
	
	public Serie(String name, String type) {
		this.id = -1;
		this.nom = name;
		this.genre = type;
	}
	
	public void setId(int i) {
		this.id = i;
	}
	
	public static ArrayList<Serie> findAll() throws SQLException {
		ArrayList<Serie> series = new ArrayList<Serie>();
		String SQLPrep = "SELECT * FROM Serie;";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		while (rs.next()) {
			String nom = rs.getString("nom");
			String genre = rs.getString("genre");
			int id = rs.getInt("id");
			Serie p = new Serie(nom,genre);
			p.setId(id);
			series.add(p);
		}
		return series;
	}
	
	public static Serie findById(int i) throws SQLException {
		Serie s = null;
		String SQLPrep = "SELECT * FROM serie where id=?;";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.setInt(1, i);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		if (rs.next()) {
			int id = rs.getInt("id");
			String nom = rs.getString("nom");
			String genre = rs.getString("genre");
			s = new Serie(nom, genre);
			s.setId(id);
		}
		return s;
	}
	
	public static ArrayList<Serie> findByName(String search) throws SQLException {
		ArrayList<Serie> series = new ArrayList<Serie>();
		String SQLPrep = "SELECT * FROM Serie where nom like '%'"+search+"'%';";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		while (rs.next()) {
			String nom = rs.getString("nom");
			String genre = rs.getString("genre");
			int id = rs.getInt("id");
			Serie p = new Serie(nom,genre);
			p.setId(id);
			series.add(p);
		}
		return series;
	}
	
	public static ArrayList<Serie> findByGenre(String search) throws SQLException {
		ArrayList<Serie> series = new ArrayList<Serie>();
		String SQLPrep = "SELECT * FROM Serie where genre = ?";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.setString(1, search);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		while (rs.next()) {
			String nom = rs.getString("nom");
			String genre = rs.getString("genre");
			int id = rs.getInt("id");
			Serie p = new Serie(nom,genre);
			p.setId(id);
			series.add(p);
		}
		return series;
	}
	
	public static void createTable() throws SQLException {
		String createString = "CREATE TABLE Serie ( " + "ID INTEGER  AUTO_INCREMENT, "
				+ "NOM varchar(40) NOT NULL, " + "GENRE varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(createString);
	}

	public static void deleteTable() throws SQLException {
		String drop = "DROP TABLE serie";
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(drop);
	}
	
	public void delete() throws SQLException {
		PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM serie WHERE id=?");
		prep.setInt(1, this.id);
		prep.execute();
		this.id = -1;
	}
	
	public void save() throws SQLException, SerieAbsenteException {
		if (this.id == -1) {
			this.saveNew();
		} else {
			this.update();
		}
	}
	
	public void saveNew() throws SQLException, SerieAbsenteException {
		if (this.id == -1) {
			throw new SerieAbsenteException();
		} else {
			String SQLPrep = "INSERT INTO serie (titre,genre) VALUES (?,?);";
			PreparedStatement prep;
			// l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
			// auto-increment)
			prep = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep,
					Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, this.nom);
			prep.setString(2, this.genre);
			prep.executeUpdate();
			int autoInc = -1;
			ResultSet rs = prep.getGeneratedKeys();
			
			if (rs.next()) {
				autoInc = rs.getInt(1);

			}
			this.id = autoInc;
		}

	}
	
	public void update() throws SQLException, SerieAbsenteException {
		if (this.id == -1) {
			throw new SerieAbsenteException();
		} else {
			String SQLprep = "update serie set nom=?, genre=? where id=?;";
			PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
			prep.setString(1, this.nom);
			prep.setString(2, this.genre);
			prep.setInt(3, this.id);
			prep.execute();
		}

	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getGenre() {
		return this.genre;
	}

}
