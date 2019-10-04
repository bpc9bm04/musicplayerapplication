FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=muzixdb
ENV MYSQL_USER_NAME=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/muzixdb
ADD ./target/accountmanager-1.0.jar /usr/src/accountmanager-1.0.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "accountmanager-1.0.jar"]
