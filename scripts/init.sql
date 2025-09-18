INSERT INTO usuario (password, role, username)
SELECT * FROM (
    VALUES
    ('$2a$10$M.Qiz16Skmbzgm5XhvckeOScUXSwOb0nCDn0BsWT1GUL.9Z0vQNda', 'ADMIN', 'Administrator'),
    ('$2a$10$UzCITCTAUVPc7ck7dqLapeyLxH7TBSs4tBcWVBfOieXSnGbAsvbTW', 'USER', '80553400215')
) AS v(password, role, username)
WHERE NOT EXISTS (SELECT 1 FROM usuario);

