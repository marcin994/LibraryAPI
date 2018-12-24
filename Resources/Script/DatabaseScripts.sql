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

-- Add test book if doesnt exist and items for them
CREATE PROCEDURE addBooks()
BEGIN

  SET @title = "Title2";
  SET @author = "Autor2";
  SET MAX_SP_RECURSION_DEPTH = 255;

  IF EXISTS (SELECT * FROM book WHERE title = @title AND author = @author) THEN
    BEGIN

      DECLARE i INT DEFAULT 1;
      WHILE  i <= 15 DO
        SELECT next_val FROM hibernate_sequence LIMIT 1 INTO @sequence;
        INSERT INTO book_item(id, author, is_available, is_deleted, isbn, pages, publishing, publishing_year, title, image_id)
          VALUE (@sequence, @author, TRUE, FALSE, i, 0, "Wydawniictwo", '20190101', @title, NULL);

        SELECT id FROM book WHERE title = @title AND author = @author INTO @bookId;

        INSERT INTO book_items(book_id, items_id)
          VALUE (@bookId, @sequence);
        UPDATE book SET item_number = item_number + 1 WHERE id = @bookId;

        UPDATE hibernate_sequence SET next_val = next_val + 1;
        SET i = i + 1;
      END WHILE;
    END;
  ELSE
    BEGIN

      SELECT next_val FROM hibernate_sequence LIMIT 1 INTO @sequence;
      INSERT INTO book(id, author, category, description, is_deleted, item_number, title)
        VALUE (@sequence, @author, "TC", "added by procedure", FALSE, 0, @title);
      UPDATE hibernate_sequence SET next_val = next_val + 1;
      CALL addBooks();

    END;
  END IF;
END;

CALL addBooks();

-- View presenting employees of library
create OR REPLACE VIEW employee AS
SELECT * FROM customer WHERE account_type IN (SELECT code FROM dictionary_item WHERE domain = "ROLE" AND code != "USER");