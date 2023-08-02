#!/bin/bash

docker exec -it bioslisdb bash -c "source /home/oracle/.bashrc; exit | sqlplus sys/Oradoc_db1@ORCLCDB as sysdba @/home/oracle/create_user.sql"