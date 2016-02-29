-- Taken from Postgres documentation Chapter 6 as a simple example
-- of returning a refcursor, which can be obtained on the client
-- side as a ResultSet. See JDBCProcEx1.java  --KG 2/15/06

CREATE OR REPLACE FUNCTION refcursorfunc() RETURNS refcursor AS '
DECLARE
    mycurs refcursor;
BEGIN
    OPEN mycurs FOR SELECT 1 UNION SELECT 2 UNION SELECT 3;
    RETURN mycurs;
END;' language plpgsql;