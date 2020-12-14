package testEtu;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import activreRecord.Serie;
import activreRecord.SerieAbsenteException;



public class SerieTest {

	@Before
	public void avant() throws SQLException, SerieAbsenteException {
		Serie.createTable();
		Serie s = new Serie("oula","test");
		s.save();
	}
	@After
	public void apres() throws SQLException {
		Serie.deleteTable();
	}
}
