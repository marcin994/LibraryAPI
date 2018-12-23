-- Add current date for field hireDate while hire book
CREATE TRIGGER hireDate
BEFORE INSERT ON hire
FOR EACH ROW BEGIN

SET NEW.hire_date = now();
SET NEW.last_modify_date = now();

END;

-- Add date of last hire modification
CREATE TRIGGER lastModify
BEFORE UPDATE ON hire
FOR EACH ROW BEGIN

SET NEW.last_modify_date = now();

END;
