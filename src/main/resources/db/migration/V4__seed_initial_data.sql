INSERT INTO users (login, full_name, email, password, role)
SELECT 'user' || i,
       'User ' || i,
       'user' || i || '@example.com',
       '$2a$10$SKF9LPuZ8hcmZBcJSpDtKuuLpKviMMqN2kxJBxgqbBc5GiQD5RF.i', --11111111
       0
FROM generate_series(1, 20) AS t(i)
ON CONFLICT (login) DO NOTHING;

INSERT INTO expense_groups (name, icon, status, owner_id)
SELECT 'Expense Group ' || i,
       CASE (i % 12)
           WHEN 0 THEN '🏠'
           WHEN 1 THEN '✈️'
           WHEN 2 THEN '🍔'
           WHEN 3 THEN '🚗'
           WHEN 4 THEN '💡'
           WHEN 5 THEN '🛒'
           WHEN 6 THEN '🍿'
           WHEN 7 THEN '💊'
           WHEN 8 THEN '🐾'
           WHEN 9 THEN '🎁'
           WHEN 10 THEN '🏋️'
           ELSE '☕'
           END,
       CASE (i % 2)
           WHEN 0 THEN 0
           ELSE 1
           END,
       ((i - 1) % 20) + 1
FROM generate_series(1, 100) AS t(i);

DO
$$
    DECLARE
        v_user_id  INT;
        v_group_id INT;
        v_inserted INT := 0;
    BEGIN
        WHILE v_inserted < 300
            LOOP
                v_user_id := FLOOR(RANDOM() * 20) + 1;
                v_group_id := FLOOR(RANDOM() * 100) + 1;

                BEGIN
                    INSERT INTO group_members (user_id, group_id)
                    VALUES (v_user_id, v_group_id);

                    v_inserted := v_inserted + 1;
                EXCEPTION
                    WHEN unique_violation THEN

                END;
            END LOOP;
    END
$$;