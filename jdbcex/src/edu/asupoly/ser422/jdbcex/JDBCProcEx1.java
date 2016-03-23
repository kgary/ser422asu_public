package edu.asupoly.ser422.jdbcex;

import java.sql.*;

import edu.asupoly.ser422.Ser422DbUtils;

/*
 */
public class JDBCProcEx1 {
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("USAGE: java edu.asupoly.ser422.jdbcex.JDBCProcEx1 <driver><url><username><passwd><procname><result type>[<args>]");
            System.exit(0);
        }

        ResultSet rs = null;
        CallableStatement stmt = null;
        Connection conn = null;

        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[0]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(args[1], args[2], args[3]);

            // Step 3: Prepare a callable statement
            StringBuffer sbquery = new StringBuffer();
            sbquery.append("{? = call ");
            sbquery.append(args[4]);
            sbquery.append("(");
            if (args.length > 6) {
                sbquery.append(" ");
                sbquery.append(args[6]);
            }
            for (int ij = 7; ij < args.length; ij++) {
                sbquery.append(",");
                sbquery.append(args[ij]);
            }
            sbquery.append(")}");

            System.out.println("Preparing Call " + sbquery.toString());
            conn.setAutoCommit(false);
            stmt = conn.prepareCall(sbquery.toString());

            int outType = Integer.parseInt(args[5]);
            stmt.registerOutParameter(1, outType);
            stmt.execute();
            
            // Step 4: Process results
            if (outType==Types.OTHER) {
                // THINGS postgres driver does not support:
                // stmt.execute should return true if result is a ResultSet
                // does not support CallableStatement.getResultSet
                rs = (ResultSet)stmt.getObject(1);

                if (rs == null) {
                    System.out.println("rs was null");
                } else {
                    Ser422DbUtils.printResultSet(rs);
                }
            } else if (outType==Types.INTEGER) {
                int result = stmt.getInt(1);
                System.out.println("Returned " + result);
            } else if (outType==Types.VARCHAR) {
            		System.out.println("Returned " + stmt.getString(1));
        		}
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            // Step 7: Close the DB resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception exc) {
                System.out.println("Exception cleaning up DB resources!");
                exc.printStackTrace();
            }
            System.exit(0);
        }
    }
}
