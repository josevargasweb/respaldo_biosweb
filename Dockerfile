FROM tomcat:7.0

COPY target/BiosLis30-0.3.war /usr/local/tomcat/webapps/
RUN   sed -i "s|JAVA_OPTS=\"\$JAVA_OPTS \$JSSE_OPTS\"|JAVA_OPTS=\"\$JAVA_OPTS \$JSSE_OPTS -Duser.language=es -Duser.region=CL\"|g" /usr/local/tomcat/bin/catalina.sh
