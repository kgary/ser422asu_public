package edu.asupoly.ser422;

import java.io.*;
import java.sql.*;

import edu.asupoly.ser422.Ser422DbUtils;
import edu.asupoly.ser422.Ser422DbWrapperException;

public class XRayLOBJavaImpl implements IXRayService {

	public boolean readXRayImage(int id, File outfile)
			throws Ser422DbWrapperException {

		boolean oldAutoCommit = false;
		Connection conn = Ser422DbUtils.getConnection("kgary", "blah", "jdbc:postgresql://localhost/lobtest", "org.postgresql.Driver");
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			oldAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false); // required by Postgres
			// if the file does not exist
			// retrieve it from the database and write it to the named file

			stmt = conn.prepareStatement("SELECT image "
					+ "FROM xray_image " + "WHERE image_id = ?");

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No such file stored.");
			} else {
				Blob b = rs.getBlob(1);
				BufferedOutputStream os;

				os = new BufferedOutputStream(new FileOutputStream(outfile));
				os.write(b.getBytes(1, (int) b.length()), 0, (int) b.length());
				os.flush();
				os.close();
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException se) {
				se.printStackTrace();
				System.out.println("Ignoring, closing connection anyway");
			}
			try {
				conn.setAutoCommit(oldAutoCommit);
				conn.close();
			}
			catch (SQLException se2) {
				se2.printStackTrace();
				System.out.println("Problem closing connection");
			}
		}

		return true;
	}

	public boolean readXRayDiagnosis(int id, File outfile)
			throws Ser422DbWrapperException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean writeXRayImage(int id, File file)
			throws Ser422DbWrapperException {

		// otherwise read it and save it to the database
		PreparedStatement stmt = null;

		FileInputStream fis = null;
		byte[] tmp = new byte[1024];
		byte[] data = null;
		int sz, len = 0;
		boolean oldAutoCommit = false;
		Connection conn = Ser422DbUtils.getConnection();

		try {
			oldAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false); // required by Postgres
			fis = new FileInputStream(file);

			while ((sz = fis.read(tmp)) != -1) {
				if (data == null) {
					len = sz;
					data = tmp;
				} else {
					byte[] narr;    int nlen;

					nlen = len + sz;
					narr = new byte[nlen];
					System.arraycopy(data, 0, narr, 0, len);
					System.arraycopy(tmp, 0, narr, len, sz);
					data = narr;
					len = nlen;
				}
			}
			if (len != data.length) {
				byte[] narr = new byte[len];

				System.arraycopy(data, 0, narr, 0, len);
				data = narr;
			}
			stmt = conn.prepareStatement("INSERT INTO xray_image (image_id, idate, image) VALUES (?,?,?)");
			stmt.setInt(1, id);
			stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			stmt.setObject(3, data);
			stmt.executeUpdate();
			fis.close();
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("Unable to write to database");
		} finally {
			try {
				if (stmt != null) { stmt.close(); }
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				conn.setAutoCommit(oldAutoCommit);
				conn.close();
			} catch (SQLException se2) {
				System.out.println("Unable to close connection");
			}
		}
		return true;
	}

	public boolean writeXRayDiagnosis(int id, File file)
			throws Ser422DbWrapperException {
		// TODO Auto-generated method stub
		return false;
	}

}
