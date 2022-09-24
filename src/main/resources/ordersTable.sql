CREATE TABLE IF NOT EXISTS orders (
          id SERIAL PRIMARY KEY,
          table_id INTEGER NOT NULL,
          name VARCHAR(255) NOT NULL,
          created_at TIMESTAMPTZ NOT NULL,
          minutesToReady INTEGER NOT NULL,
        )
