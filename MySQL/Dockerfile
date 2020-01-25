FROM mysql:latest
COPY world.sql /tmp
WORKDIR /tmp
COPY world.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD=example