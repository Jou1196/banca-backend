-- ==========================================================
-- BANCA BACKEND - PostgreSQL Schema (Customers, Accounts, Movements, Outbox)
-- ==========================================================


CREATE EXTENSION IF NOT EXISTS pgcrypto;



DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'account_type') THEN
    CREATE TYPE account_type AS ENUM ('AHORRO', 'CORRIENTE');
  END IF;

  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'movement_type') THEN
    CREATE TYPE movement_type AS ENUM ('DEPOSIT', 'WITHDRAWAL');
  END IF;

  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'outbox_status') THEN
    CREATE TYPE outbox_status AS ENUM ('PENDING', 'PROCESSING', 'SENT', 'FAILED');
  END IF;
END $$;

-- ==========================================================
-- 3) CUSTOMERS
-- ==========================================================
CREATE TABLE IF NOT EXISTS customers (
  id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  full_name          VARCHAR(200) NOT NULL,
  identification     VARCHAR(30)  NOT NULL,
  address            VARCHAR(255),
  phone              VARCHAR(30),
  password           VARCHAR(255) NOT NULL,
  status             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at         TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at         TIMESTAMP
);


CREATE UNIQUE INDEX IF NOT EXISTS ux_customers_identification
  ON customers (identification);

-- ==========================================================
-- 4) ACCOUNTS
-- ==========================================================
CREATE TABLE IF NOT EXISTS accounts (
  id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  account_number VARCHAR(30) NOT NULL,
  type           account_type NOT NULL,               -- 'AHORRO' / 'CORRIENTE'
  balance        NUMERIC(19,2) NOT NULL DEFAULT 0,
  status         BOOLEAN NOT NULL DEFAULT TRUE,
  customer_id    UUID NOT NULL,
  created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at     TIMESTAMP,

  CONSTRAINT fk_accounts_customer
    FOREIGN KEY (customer_id)
    REFERENCES customers(id)
    ON DELETE RESTRICT,

  CONSTRAINT ck_accounts_balance_nonnegative
    CHECK (balance >= 0)
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_accounts_account_number
  ON accounts (account_number);

CREATE INDEX IF NOT EXISTS ix_accounts_customer_id
  ON accounts (customer_id);

-- ==========================================================
-- 5) MOVEMENTS
-- ==========================================================
CREATE TABLE IF NOT EXISTS movements (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  account_id      UUID NOT NULL,
  amount          NUMERIC(19,2) NOT NULL,
  type            movement_type NOT NULL,             -- 'DEPOSIT' / 'WITHDRAWAL'
  balance_before  NUMERIC(19,2) NOT NULL,
  balance_after   NUMERIC(19,2) NOT NULL,
  movement_date   DATE NOT NULL,
  created_at      TIMESTAMP NOT NULL DEFAULT NOW(),

  CONSTRAINT fk_movements_account
    FOREIGN KEY (account_id)
    REFERENCES accounts(id)
    ON DELETE RESTRICT,

  CONSTRAINT ck_movements_amount_positive
    CHECK (amount > 0),

  CONSTRAINT ck_movements_balance_nonnegative
    CHECK (balance_before >= 0 AND balance_after >= 0)
);

CREATE INDEX IF NOT EXISTS ix_movements_account_id
  ON movements (account_id);

CREATE INDEX IF NOT EXISTS ix_movements_movement_date
  ON movements (movement_date);


CREATE INDEX IF NOT EXISTS ix_accounts_customer_account
  ON accounts (customer_id, id);

-- ==========================================================
-- 6) OUTBOX EVENT (Patrón Outbox)
-- ==========================================================
CREATE TABLE IF NOT EXISTS outbox_event (
  id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  aggregate_type VARCHAR(80)  NOT NULL,              
  aggregate_id   UUID         NOT NULL,              
  event_type     VARCHAR(120) NOT NULL,              
  payload        JSONB        NOT NULL,
  status         outbox_status NOT NULL DEFAULT 'PENDING',
  attempts       INT NOT NULL DEFAULT 0,
  last_error     TEXT,
  created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
  processed_at   TIMESTAMP
);


CREATE INDEX IF NOT EXISTS ix_outbox_status_created
  ON outbox_event (status, created_at);


CREATE UNIQUE INDEX IF NOT EXISTS ux_outbox_aggregate_event
  ON outbox_event (aggregate_type, aggregate_id, event_type);


