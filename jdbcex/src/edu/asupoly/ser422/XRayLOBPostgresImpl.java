package edu.asupoly.ser422;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.largeobject.*;

import edu.asupoly.ser422.Ser422DbUtils;
import edu.asupoly.ser422.Ser422DbWrapperException;

public final class XRayLOBPostgresImpl implements IXRayService {
    private static final int __BUFSIZE = 2048;

    @SuppressWarnings("deprecation")
	public boolean readXRayImage(int id, File outfile)
            throws Ser422DbWrapperException {
        byte[] buf = new byte[__BUFSIZE];

        if (outfile.exists() && !outfile.canWrite()) {
            return false;
        }
        FileOutputStream os = null;
        boolean oldAutoCommit = false;
        Connection conn = Ser422DbUtils.getConnection("kgary", "blah", "jdbc:postgresql://localhost/lobtest", "org.postgresql.Driver");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        LargeObjectManager lobj = null;
        LargeObject obj = null;
        try {
            os = new FileOutputStream(outfile);
        }
        catch (IOException ie) {
            throw new Ser422DbWrapperException(ie);
        }
        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            // Get the Large Object Manager to perform operations with
            lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
            pstmt = conn.prepareStatement("SELECT image FROM xray_image WHERE image_id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) { // open the large object for reading
                int oid = rs.getInt(1);
                obj = lobj.open(oid, LargeObjectManager.READ);
                // read the data
                buf = new byte[obj.size()];
                obj.read(buf, 0, obj.size());
                System.out.println("Now at position in the file: " + obj.tell());
                // push them back out to the output file
                os.write(buf, 0, buf.length);
                os.flush(); // flush if we're buffered
            }
            os.close();
            os = null;
            // Close the object
            obj.close();
            obj = null;

            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;
            conn.setAutoCommit(oldAutoCommit);
            Ser422DbUtils.releaseConnection(conn);
            conn = null;
            return true;
        } catch (IOException ie) {
            System.err.println("IO Exception reading Xray with id: " + id
                    + ", message: " + ie.getMessage());
            throw new Ser422DbWrapperException(ie);
        } catch (SQLException se) {
            System.err.println("SQL Exception reading Xray with id: " + id
                    + ", message: " + se.getMessage());
            throw new Ser422DbWrapperException(se);
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (Throwable t0) {
                t0.printStackTrace();
            }
            try {
                if (obj != null)
                    obj.close();
            } catch (Throwable t1) {
                t1.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Throwable t2) {
                t2.printStackTrace();
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Throwable t3) {
                t3.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.setAutoCommit(oldAutoCommit);
                    Ser422DbUtils.releaseConnection(conn);
                    conn = null;
                }
            } catch (Throwable t4) {
                t4.printStackTrace();
            }

        }
    }

    public boolean readXRayDiagnosis(int id, File file)
            throws Ser422DbWrapperException {
        return false;
    }

    @SuppressWarnings("deprecation")
	public boolean writeXRayImage(int id, File file)
            throws Ser422DbWrapperException {
        FileInputStream fis = null;
        PreparedStatement ps = null;
        Connection conn = Ser422DbUtils.getConnection("kgary", "blah", "jdbc:postgresql://localhost/lobtest", "org.postgresql.Driver");
        boolean oldAutoCommit = true;
        LargeObjectManager lobj = null;
        LargeObject obj = null;

        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO xray_image (image_id, idate, image) VALUES (?, ?, ?)");

            // Get the Large Object Manager to perform operations with
            lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
            // create a new large object
            int oid = lobj.create(LargeObjectManager.READ | LargeObjectManager.WRITE);
            // open the large object for write
            obj = lobj.open(oid, LargeObjectManager.WRITE);
            // Now open the file
            fis = new FileInputStream(file);
            // copy the data from the file to the large object
            byte buf[] = new byte[2048];
            int s = 0;
            while ((s = fis.read(buf, 0, 2048)) > 0)  {
                obj.write(buf, 0, s);
            }

            // Close the large object
            obj.close();
            fis.close();

            // Now insert the row into images LO
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO xray_image (image_id, idate, image) VALUES (?, ?, ?)");
            pstmt.setInt(1, 10346);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3, oid);
            pstmt.executeUpdate();
            conn.commit();

            obj.close();
            obj = null;

            ps.close();
            ps = null;

            conn.setAutoCommit(oldAutoCommit);
            Ser422DbUtils.releaseConnection(conn);
            conn = null;
        } catch (Throwable t) {
            throw new Ser422DbWrapperException(
                    "XRayLOBStandardImpl::writeXRayImage", t);
        } finally {
            try {
            		if (obj != null) {
            			obj.close();
            		}
                if (ps != null)
                    ps.close();
            } catch (Throwable t3) {
                t3.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.setAutoCommit(oldAutoCommit);
                    Ser422DbUtils.releaseConnection(conn);
                    conn = null;
                }
            } catch (Throwable t4) {
                t4.printStackTrace();
            }
        }
        return true;
    }

    public boolean writeXRayDiagnosis(int id, File file)
            throws Ser422DbWrapperException {
        return false;
    }

}
