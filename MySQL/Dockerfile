# mysql container to run database
FROM mysql:latest
COPY world.sql /tmp
WORKDIR /tmp
# copy database file to container entrypoint folder for automatic load
COPY world.sql /docker-entrypoint-initdb.d
# set mysql root password
ENV MYSQL_ROOT_PASSWORD=example
EXPOSE 3306/tcp