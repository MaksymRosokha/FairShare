CREATE TABLE IF NOT EXISTS expense_groups
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(128) NOT NULL,
    icon       varchar(16),
    status     SMALLINT     NOT NULL,
    owner_id   INT,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk__expense_groups__owner_id__users FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE SET NULL
);