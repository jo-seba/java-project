FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=test_db
ENV MYSQL_USER=test_user
ENV MYSQL_PASSWORD=test_password

COPY ./my.cnf /etc/mysql/my.cnf
COPY ./sql /docker-entrypoint-initdb.d/

CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]
