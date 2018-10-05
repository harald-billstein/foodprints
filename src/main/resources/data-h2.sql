
-- CREATE USERS

-- password is BCrypt encoding of 'password123'
INSERT INTO users (id, username, password) VALUES (1, 'testuser', '$2a$10$xNI07629MO/nSt1fjkcvw.mHx5Pyz6ZBWKWKvci2meGeWlPqY8Ot6');

INSERT INTO roles (id, role) VALUES (1, 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- CREATE EMISSIONS
INSERT INTO Emission (category, co2e) VALUES ('BEEF', 26.0);
INSERT INTO Emission (category, co2e) VALUES ('PORK', 3.0);
INSERT INTO Emission (category, co2e) VALUES ('CHICKEN', 6.0);
INSERT INTO Emission (category, co2e) VALUES ('VEGAN', 1.1);
INSERT INTO Emission (category, co2e) VALUES ('VEGETARIAN', 1.9);
INSERT INTO Emission (category, co2e) VALUES ('FISH', 3.0);


