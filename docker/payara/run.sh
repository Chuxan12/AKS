#!/bin/sh
set -eu

HTTP_PORT=${HTTP_PORT:-8080}
DB_HOST=${DB_HOST:-db}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-academy}
DB_USER=${DB_USER:-academy}
DB_PASSWORD=${DB_PASSWORD:-academy}

DS_FILE=/opt/payara/datasource.asadmin
cat > "${DS_FILE}" <<EOF
create-jdbc-connection-pool --ping=false --restype javax.sql.DataSource --datasourceclassname org.postgresql.ds.PGSimpleDataSource --property user=${DB_USER}:password=${DB_PASSWORD}:serverName=${DB_HOST}:portNumber=${DB_PORT}:databaseName=${DB_NAME}:URL=jdbc\\:postgresql\\://${DB_HOST}\\:${DB_PORT}/${DB_NAME} AcademyPool
create-jdbc-resource --connectionpoolid AcademyPool jdbc/AcademyDS
EOF

exec java -jar /opt/payara/payara-micro.jar \
    --nocluster \
    --contextroot / \
    --port "${HTTP_PORT}" \
    --deploy /opt/payara/app.war \
    --addlibs /opt/payara/libs/postgresql.jar \
    --postbootcommandfile "${DS_FILE}"
