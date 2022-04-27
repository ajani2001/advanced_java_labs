CREATE TABLE users(
                      id BIGINT,
                      name VARCHAR
);

CREATE TABLE nodes(
                      id BIGINT,
                      lat DOUBLE PRECISION,
                      lon DOUBLE PRECISION,
                      user_id BIGINT,
                      visible BOOLEAN,
                      version BIGINT,
                      changeset BIGINT,
                      timestamp TIMESTAMP
);