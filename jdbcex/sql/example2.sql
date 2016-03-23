/*
	example2.sql
	Author: Kevin Gary
	Date:   4/12/04
	
	DESCRIPTION: A bogus function demonstrating TYPE and SELECT INTO

	INPUT:   INTEGER representing a prize money value
	RETURNS: INTEGER representing the total of all prize money entries
		 above the input parameter
*/
CREATE OR REPLACE FUNCTION example2 (integer) RETURNS integer AS '
	DECLARE
		-- Note the use of ALIAS here on a paramter
		salary 		ALIAS for $1;

		-- ROWTYPE declaration. Notice it uses the name of the table
		money 	bestgolfers.prize_money%TYPE;
	BEGIN
		-- RAISE example. Transaction will not be aborted
		RAISE NOTICE ''In example2'';

		-- SELECT INTO syntax
		-- If there is more than one row this will return only the first!
		SELECT INTO money prize_money FROM bestgolfers WHERE prize_money > salary;

		-- RETURN a value with type INTEGER
		RETURN money;
	END;
	-- Note the end tick here. Body of function is a string
' LANGUAGE 'plpgsql';