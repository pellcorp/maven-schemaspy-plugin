package com.wakaleo.schemaspy.util;

import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseConfig {
	/**
     * A map of JDBC prefixes and database types.
     * Used to determine the database type based on a JDBC URL.
     */
    private static final String[][] DATABASE_TYPES_MAP
          = {{"derby", "derby" },
            {"db2", "db2"},
            {"firebirdsql", "firebirdsql"},
            {"hsqldb:hsql", "hsqldb" },
            {"informix-sqli", "informix-sqli" },
            {"microsoft:sqlserver", "mssql" },
            {"jtds", "mssql-jtds" },
            {"mysql", "mysql" },
            {"oracle:oci8", "ora" },
            {"oracle:thin", "orathin" },
            {"postgresql", "pgsql" },
            {"sybase:Tds", "sybase" }};
    
	private final String type;
	private final String host;
	private final String port;
	private final String database;
	
	public DatabaseConfig(String jdbcUrl) throws IllegalArgumentException {
		String cleanUrl = jdbcUrl.substring("jdbc:".length()).replace(":@", "://");
		
		for (String[] databaseTypeEntry : DATABASE_TYPES_MAP) {
			String jdbcPrefix = databaseTypeEntry[0];
			String jdbcType = databaseTypeEntry[1];
			if (cleanUrl.startsWith(jdbcPrefix)) {
				cleanUrl = jdbcType + cleanUrl.substring(jdbcPrefix.length());
			}
		}
		
		int i = cleanUrl.indexOf("://");
		if (i == -1) {
			i = cleanUrl.indexOf(":");
			cleanUrl = cleanUrl.substring(0, i) + "://" + cleanUrl.substring(i + 1);
			i = cleanUrl.indexOf("://");
		}
		
		String prefix = cleanUrl.substring(0, i).replace(":", "-");
		String suffix = cleanUrl.substring(i + "://".length()).replace(";", "/");
		
		String[] suffixParts = suffix.split(":");
		if (suffixParts.length == 3) {
			suffix = suffixParts[0] + ":" + suffixParts[1] + "/" + suffixParts[2];
		}
		
		suffixParts = suffix.split("/");
		if (suffixParts.length == 1) {
				suffix = "none/" + suffix;
		}
		
		cleanUrl = prefix + "://" + suffix;
		
		URI uri;
		try {
			uri = new URI(cleanUrl);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
		
		this.type = uri.getScheme();
		
		if (!uri.getHost().equals("none")) {
			this.host = uri.getHost();
		} else {
			this.host = null;
		}
		
		if (uri.getPort() != -1) {
			this.port = uri.getPort() + "";
		} else {
			this.port = null;
		}
		
		String path = uri.getPath();
		if (path.startsWith("/")) {
			path = uri.getPath().substring(1); // get rid of first slash
		}
		path = path.replace(":", "/");
		if (path.startsWith("databaseName=")) { // mssql server hack!
			path = path.substring("databaseName=".length()); 
		}
		
		this.database = path;
	}
	
	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}
}
