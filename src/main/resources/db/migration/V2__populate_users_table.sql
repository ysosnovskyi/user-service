INSERT INTO users (name, balance)
SELECT
        'User' || i,
        (RANDOM() * 10000)::int
FROM generate_series(1, 1000000) AS s(i);