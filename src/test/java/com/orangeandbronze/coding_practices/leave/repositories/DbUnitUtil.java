package com.orangeandbronze.coding_practices.leave.repositories;

import java.sql.*;

import org.dbunit.*;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;



public class DbUnitUtil {
	
	static IDatabaseConnection getIDatabaseConnection() throws SQLException, DatabaseUnitException {
		Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "");
		// turn off referential integrity to make it easy to insert and delete test data
		jdbcConnection.createStatement().execute("SET DATABASE REFERENTIAL INTEGRITY FALSE;");
		return new DatabaseConnection(jdbcConnection);
	}

	static void cleanInsert() throws Exception {
		IDatabaseConnection connection = getIDatabaseConnection();
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setDtdMetadata(false);
		IDataSet dataSet = builder.build(DbUnitUtil.class.getResourceAsStream("dataset.xml"));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		connection.close();
	}

}
