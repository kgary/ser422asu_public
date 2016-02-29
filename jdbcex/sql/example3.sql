/*
	example3.sql
	Author: Kevin Gary
	Date:   4/12/04
	
	DESCRIPTION: A bogus function demonstrating RECORD

	INPUT:   INTEGER representing a prize money value
	RETURNS: INTEGER representing the total of all prize money entries
*/
CREATE OR REPLACE FUNCTION example3 (integer) RETURNS integer AS '
	DECLARE
		-- Note the use of ALIAS here on a paramter
		salary 		ALIAS for $1;
		total_prizes	INTEGER := 0;

		-- ROWTYPE declaration. Notice it uses the name of the table
		some_rec 	RECORD;
		some_prizes 	INTEGER := 0;
	BEGIN
		-- RAISE example. Transaction will not be aborted
		RAISE NOTICE ''In example3'';

		-- Second form of FOR loop that takes a Query
		FOR some_rec IN SELECT * from bestgolfers LOOP
			total_prizes := total_prizes + some_rec.prize_money;
			IF some_rec.prize_money > salary THEN
				some_prizes := some_prizes + some_rec.prize_money;
			END IF;
		END LOOP;

		-- Now we can reuse RECORD for something else
		-- Add purses to our total
		FOR some_rec IN SELECT purse from tournament LOOP
			-- Notice how some_rec has a new structure
			-- Cannot do that with a %ROWTYPE
			total_prizes := total_prizes + some_rec.purse;
			IF some_rec.purse > salary THEN
				RAISE NOTICE ''Another purse greater than param'';
			END IF;
		END LOOP;

		-- RETURN a value with type INTEGER
		RETURN total_prizes;
	END;
	-- Note the end tick here. Body of function is a string
' LANGUAGE 'plpgsql';