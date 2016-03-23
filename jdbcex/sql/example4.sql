/*
	example4.sql
	Author: Kevin Gary
	Date:   4/12/04
	
	DESCRIPTION: A bogus function demonstrating TYPE and CURSOR

	INPUT:   INTEGER representing a prize money value
	RETURNS: INTEGER representing the total of all prize money entries
		 above the input parameter
*/
CREATE OR REPLACE FUNCTION example4 (integer) RETURNS integer AS '
	DECLARE
		-- Note the use of ALIAS here on a paramter
		salary 		ALIAS for $1;
		counter 	INTEGER := 0;

		-- TYPE declaration. Notice it uses the name of the table
		money 	bestgolfers.prize_money%TYPE;
		purse   tournament.purse%TYPE;
		
		-- CURSOR declaration
		bg_cursor	CURSOR (VAL INTEGER) FOR
					SELECT prize_money FROM bestgolfers 
					WHERE prize_money > VAL;
		bg_cursor2	CURSOR (VAL INTEGER) FOR
					SELECT purse FROM tournament
					WHERE purse > VAL;
		bg_ref		REFCURSOR; 
	BEGIN
		-- RAISE example. Transaction will not be aborted
		RAISE NOTICE ''In example4'';

		-- OPEN the cursor
		OPEN bg_cursor(salary);
		bg_ref := bg_cursor;
		FETCH bg_ref INTO money;
		WHILE FOUND LOOP
			counter := counter + money;
			FETCH bg_ref INTO money;
		END LOOP;
		-- Note we can CLOSE with the refcursor
		CLOSE bg_ref;

		-- Now switch the ref to refer to the second cursor
		-- OPEN the cursor
		OPEN bg_cursor2(salary);
		bg_ref := bg_cursor2;
		FETCH bg_ref INTO purse;
		WHILE FOUND LOOP
			counter := counter + purse;
			FETCH bg_ref INTO purse;
		END LOOP;
		-- Note we can CLOSE with the refcursor
		CLOSE bg_ref;

		-- RETURN a value with type INTEGER
		RETURN counter;
	END;
	-- Note the end tick here. Body of function is a string
' LANGUAGE 'plpgsql';