CREATE TABLE IF NOT EXISTS courses
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(30) UNIQUE NOT NULL,
    title       VARCHAR(160) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS students
(
    id         BIGSERIAL PRIMARY KEY,
    full_name  VARCHAR(160) NOT NULL,
    email      VARCHAR(160) UNIQUE NOT NULL,
    study_year INTEGER      NOT NULL,
    course_id  BIGINT REFERENCES courses (id) ON DELETE SET NULL
);

INSERT INTO courses (code, title, description)
VALUES ('JAVA101', 'Введение в Java', 'Обзор языка и основ JVM'),
       ('WEB201', 'Веб‑приложения', 'HTTP, сервлеты, REST, безопасность')
ON CONFLICT DO NOTHING;

INSERT INTO students (full_name, email, study_year, course_id)
VALUES ('Alice Johnson', 'alice@example.com', 1,
        (SELECT id FROM courses WHERE code = 'JAVA101')),
       ('Bob Brown', 'bob@example.com', 2,
        (SELECT id FROM courses WHERE code = 'WEB201'))
ON CONFLICT DO NOTHING;
