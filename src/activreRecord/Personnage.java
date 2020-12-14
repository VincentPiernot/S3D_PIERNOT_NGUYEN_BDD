package activreRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Personnage {
	private int id, id_serie;
	private String nom;

	public Personnage(String n, int p) {
		this.nom = n;
		this.id_serie = p;
		this.id = -1;
	}

	private Personnage(int ids, String n) {
		this.id = ids;
		this.nom = n;
	}

	public static ArrayList<Personnage> findAll() throws SQLException {
		ArrayList<Personnage> Personnages = new ArrayList<Personnage>();
		String SQLPrep = "SELECT * FROM Personnage;";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		while (rs.next()) {
			String nom = rs.getString("nom");
			int idserie = rs.getInt("id_serie");
			int id = rs.getInt("id");
			Personnage p = new Personnage(nom, idserie);
			p.setId(id);
			Personnages.add(p);
		}
		return Personnages;
	}

	public static Personnage findById(int id) throws SQLException {
		Personnage p = null;
		String SQLPrep = "SELECT * FROM Personnage where id=?;";
		PreparedStatement prep1 = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep);
		prep1.setInt(1, id);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		if (rs.next()) {
			String nom = rs.getString("nom");
			int idserie = rs.getInt("id_serie");
			int idd = rs.getInt("id");
			p = new Personnage(nom, idserie);
			p.setId(idd);
		}
		return p;
	}

	public Serie getSerie() throws SQLException {
		return Serie.findById(this.id_serie);
	}

	public static void createTable() throws SQLException {
		String createString = "CREATE TABLE Personnage ( " + "ID INTEGER  AUTO_INCREMENT, "
				+ "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(createString);
	}

	public static void deleteTable() throws SQLException {
		String drop = "DROP TABLE Personnage";
		Statement stmt = DBConnection.getInstance().getConnection().createStatement();
		stmt.executeUpdate(drop);
	}

	public void save() throws SQLException {
		if (this.id == -1) {
			this.saveNew();
		} else {
			this.update();
		}

	}

	public void saveNew() throws SQLException {
		String SQLPrep = "INSERT INTO Personnage (nom, id_serie) VALUES (?,?);";
		PreparedStatement prep;
		// l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
		// auto-increment)
		prep = DBConnection.getInstance().getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
		prep.setString(1, this.nom);
		prep.setInt(2, this.id_serie);
		prep.executeUpdate();
		int autoInc = -1;
		ResultSet rs = prep.getGeneratedKeys();
		if (rs.next()) {
			autoInc = rs.getInt(1);

		}
		this.id = autoInc;
	}

	public void update() throws SQLException {
		String SQLprep = "update Personnage set nom=?, id_serie=? where id=?;";
		PreparedStatement prep = DBConnection.getInstance().getConnection().prepareStatement(SQLprep);
		prep.setString(1, this.nom);
		prep.setInt(2, this.id_serie);
		prep.setInt(3, this.id);
		prep.execute();
	}

	public void delete() throws SQLException {
		PreparedStatement prep = DBConnection.getInstance().getConnection()
				.prepareStatement("DELETE FROM Personnage WHERE id=?");
		prep.setInt(1, this.id);
		prep.execute();
		this.id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getIdserie() {
		return id_serie;
	}

	public void setIdserie(int id) {
		this.id_serie = id;
	}

}
