-- Addresses
INSERT INTO addresses_table (
    id,
    street,
    city,
    state,
    country,
    pincode
)
VALUES (
    1,
    '123 Park Street',
    'Kolkata',
    'West Bengal',
    'India',
    '700016'
);

INSERT INTO addresses_table (
    id,
    street,
    city,
    state,
    country,
    pincode
)
VALUES (
    2,
    '456 MG Road',
    'Bengaluru',
    'Karnataka',
    'India',
    '560001'
);

-- Users
INSERT INTO users (
    id,
    first_name,
    last_name,
    email,
    phone_number,
    role,
    address_id,
    created_at
)
VALUES (
    1,
    'John',
    'Doe',
    'john@example.com',
    '9876543210',
    'ROLE_CUSTOMER',
    1,
    CURRENT_TIMESTAMP
);

INSERT INTO users (
    id,
    first_name,
    last_name,
    email,
    phone_number,
    role,
    address_id,
    created_at
)
VALUES (
    2,
    'Admin',
    'User',
    'admin@example.com',
    '9876543211',
    'ROLE_ADMIN',
    2,
    CURRENT_TIMESTAMP
);
ALTER TABLE addresses_table
ALTER COLUMN id RESTART WITH 3;

ALTER TABLE users
ALTER COLUMN id RESTART WITH 3;