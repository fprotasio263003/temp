import java.sql.*;

public class OracleJDBCTest {

    public static void main(String[] args) throws Exception {
        //connect to database
		
        //String serverName = "127.0.0.1";
        //String portNumber = "1521";
        //String sid = "XE";
        //String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
		if (args.length != 5) {
			System.err.println ("Numero de parametros errado: \n" + 
			"Forma de uso: OracleJDBCTest <driver> <url> <userName> <userPassword> <statement>\n" + 
			"O valor '.' pode ser utilizado para utilizar os valores default");
			return;
		}
		String driverName = "oracle.jdbc.driver.OracleDriver";
		if (!(".".equals (args[0]))){
			driverName = args[0];
		}
		
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		if (!(".".equals (args[1]))){
			url = args[1];
		}

        String userName = "sgmotp1";
		if (!(".".equals (args[2]))){
			userName = args[2];
		} else if (!("_".equals (args[2]))){
			userName = null;
		}
		
        String password = "sgmotp#123";
		if (!(".".equals (args[3]))){
			password = args[3];
		} else if (!("_".equals (args[3]))){
			password = null;
		}
		
		String statementStr = "SELECT SYSDATE FROM DUAL";
		if (!(".".equals (args[4]))){
			statementStr = args[4];
		}
		
		System.out.println ("DriverName: " + driverName);
		System.out.println ("URL: " + url);
		System.out.println ("Usuario: " + userName);
		try {
			Class.forName(driverName);
			Connection conn = null;
			if ((userName != null) && (password != null) ) {
				conn = DriverManager.getConnection(url, userName, password);
			} else {
				conn = DriverManager.getConnection(url);
			}
			System.out.println ("Conexao OK!!");
			
			Statement stmt = conn.createStatement();
			ResultSet rs;
 
			System.out.println ("Executando o comando: " + statementStr );
            rs = stmt.executeQuery(statementStr);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();

			String [] columnsDB = new String [columnCount];
			
			// The column count starts from 1
			for (int i = 1; i <= columnCount; i++ ) {
				String columnName = rsmd.getColumnName(i);
				columnsDB [i-1] = columnName;
			}
			
            while ( rs.next() ) {
				System.out.print ("Registro: ");
				for (int i =0; i < columnsDB.length; i++ ){
					String columnValue = rs.getString(columnsDB[i]);
					System.out.print (columnValue);
					if ((i > 0) && (i < columnsDB.length)) {
						System.out.print (", ");
					}
				}
                System.out.println();
            }
			conn.close();
		} catch (Exception e) {
			System.out.println ("Conexao Falhou. Mensagem: " + e.toString());
		}
    }
}