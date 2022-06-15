CREATE PROCEDURE create_category_and_partition(category_parent_id bigint, category_name varchar)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        category_id bigint;
    BEGIN
        INSERT INTO categories (parent_id, name) VALUES (category_parent_id, category_name) returning id into category_id;
        EXECUTE ('CREATE TABLE category_' || category_id || ' PARTITION OF sights_categories FOR VALUES IN (' || category_id || ')');
    END
$$;

CALL create_category_and_partition(null, 'cultural');

CALL create_category_and_partition(1, 'theatre');

CALL create_category_and_partition(1, 'cinema');

CALL create_category_and_partition(1, 'museum');

CALL create_category_and_partition(1, 'memorial');

CALL create_category_and_partition(null, 'entertainment');

CALL create_category_and_partition(6, 'casino');

CALL create_category_and_partition(6, 'bowling');

CALL create_category_and_partition(6, 'karaoke');

CALL create_category_and_partition(null, 'religious');

CALL create_category_and_partition(10, 'church');

CALL create_category_and_partition(10, 'synagogues');

CALL create_category_and_partition(null, 'architectural');

CALL create_category_and_partition(13, 'sculpture');

CALL create_category_and_partition(13, 'fountain');

CALL create_category_and_partition(null, 'natural');

CALL create_category_and_partition(16, 'park');

CALL create_category_and_partition(16, 'reserve');

CALL create_category_and_partition(16, 'zoo');

CALL create_category_and_partition(16, 'reservoir');

CALL create_category_and_partition(null, 'catering');

CALL create_category_and_partition(21, 'restaurant');

CALL create_category_and_partition(21, 'cafe');

CALL create_category_and_partition(21, 'bar');

CALL create_category_and_partition(21, 'pub');

CALL create_category_and_partition(null, 'child');

CALL create_category_and_partition(26, 'waterpark');

CALL create_category_and_partition(26, 'circus');

CALL create_category_and_partition(26, 'attraction');
