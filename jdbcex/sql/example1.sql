/*
	example1.sql
	Author: Kevin Gary
	Date:   4/12/04
	
	DESCRIPTION: A bogus function demonstrating IF, FOR, and ROWTYPE

	INPUT:   INTEGER representing a prize money value
	RETURNS: INTEGER representing the total of all prize money entries
		 above the input parameter
*/
CREATE OR REPLACE FUNCTION example1 (integer) RETURNS integer AS '
	DECLARE
		-- Note the use of ALIAS here on a paramter
		salary 		ALIAS for $1;
		total_prizes	INTEGER := 0;

		-- ROWTYPE declaration. Notice it uses the name of the table
		bestgolferrow 	bestgolfers%ROWTYPE;
		some_prizes 	INTEGER := 0;
	BEGIN
		-- RAISE example. Transaction will not be aborted
		RAISE NOTICE ''In example1'';

		-- Second form of FOR loop that takes a Query
		FOR bestgolferrow IN SELECT * from bestgolfers LOOP
			total_prizes := total_prizes + bestgolferrow.prize_money;
			IF bestgolferrow.prize_money > salary THEN
				some_prizes := some_prizes + bestgolferrow.prize_money;
			END IF;
		END LOOP;

		-- RETURN a value with type INTEGER
		RETURN some_prizes;
	END;
	-- Note the end tick here. Body of function is a string
' LANGUAGE 'plpgsql';