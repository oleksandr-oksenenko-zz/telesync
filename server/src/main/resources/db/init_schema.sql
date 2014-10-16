CREATE TABLE IF NOT EXISTS devices (
  id INTEGER PRIMARY KEY,
  url TEXT,
  device_name TEXT,
  tv_name TEXT,
  last_heartbeat timestamp
);

CREATE TABLE IF NOT EXISTS mail_config (
  key TEXT PRIMARY KEY,
  value TEXT
);

INSERT OR IGNORE INTO mail_config
VALUES ('mail_address', '');
