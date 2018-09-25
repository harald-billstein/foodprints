
-- CREATE USERS

-- password is BCrypt encoding of 'password123'
INSERT INTO users (id, username, password) VALUES (1, 'testuser', '$2a$10$xNI07629MO/nSt1fjkcvw.mHx5Pyz6ZBWKWKvci2meGeWlPqY8Ot6');

INSERT INTO roles (id, role) VALUES (1, 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- CREATE EMISSIONS
INSERT INTO emission (category, co2e) VALUES ('BEEF', 26.0);
INSERT INTO emission (category, co2e) VALUES ('PORK', 3.0);
INSERT INTO emission (category, co2e) VALUES ('CHICKEN', 6.0);
INSERT INTO emission (category, co2e) VALUES ('VEGAN', 1.1);
INSERT INTO emission (category, co2e) VALUES ('VEGITARIAN', 1.9);
INSERT INTO emission (category, co2e) VALUES ('FISH', 3.0);

-- CREATE BEEF ENTRIES
-- Week 36
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (1, '2018-09-03', 1, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (2, '2018-09-04', 2, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (3, '2018-09-05', 1, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (4, '2018-09-06', 3, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (5, '2018-09-07', 1, 'BEEF');

-- Week 37
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (6, '2018-09-10', 4, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (7, '2018-09-11', 1, 'BEEF');
INSERT INTO collection_of_votes (id, local_date, votes, category) VALUES (8, '2018-09-12', 5, 'BEEF');
