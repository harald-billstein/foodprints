
-- password is BCrypt encoding of 'password123'
INSERT INTO users (id, username, password) VALUES (1, 'testuser', '$2a$10$xNI07629MO/nSt1fjkcvw.mHx5Pyz6ZBWKWKvci2meGeWlPqY8Ot6');

INSERT INTO roles (id, role) VALUES (1, 'USER');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
