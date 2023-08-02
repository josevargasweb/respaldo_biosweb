FROM store/oracle/database-enterprise:12.2.0.1

COPY database/create_user.sql /home/oracle/