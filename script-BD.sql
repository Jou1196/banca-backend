CREATE DATABASE banco;

CREATE TABLE customer (
    id_customer SERIAL PRIMARY KEY,  -- ID único del cliente
    full_name VARCHAR(255) NOT NULL,  -- Nombre completo
    address VARCHAR(255) NOT NULL,    -- Dirección
    phone VARCHAR(15),                -- Teléfono
    password VARCHAR(255) NOT NULL,   -- Contraseña
    status VARCHAR(20) NOT NULL       -- Estado (ej. activo, inactivo)
);

CREATE TABLE account (
    id_account SERIAL PRIMARY KEY,      -- ID único de la cuenta
    account_type VARCHAR(50) NOT NULL,   -- Tipo de cuenta (ej. corriente, ahorro)
    balance DECIMAL(10, 2) NOT NULL,     -- Balance actual de la cuenta
    id_customer INT REFERENCES customer(id_customer) ON DELETE CASCADE -- Relación con el cliente
);


CREATE TABLE transaction (
    id_transaction SERIAL PRIMARY KEY,    -- ID único de la transacción
    transaction_type VARCHAR(50) NOT NULL, -- Tipo de transacción (ej. retiro, depósito)
    amount DECIMAL(10, 2) NOT NULL,       -- Monto de la transacción
    initial_balance DECIMAL(10, 2) NOT NULL, -- Saldo inicial antes de la transacción
    status VARCHAR(20) NOT NULL DEFAULT 'Completado',  -- Estado de la transacción
    available_balance DECIMAL(10, 2) NOT NULL,  -- Saldo disponible después de la transacción
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora de la transacción
    id_account INT REFERENCES account(id_account) ON DELETE CASCADE -- Relación con la cuenta
);