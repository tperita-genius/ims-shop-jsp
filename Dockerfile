# 階段一：使用 Maven + Java 21 編譯 WAR 檔
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 階段二：使用 Tomcat 9 運行環境
FROM tomcat:9.0-jdk21-temurin
WORKDIR /usr/local/tomcat

# 清理 Tomcat 預設的 ROOT 專案
RUN rm -rf webapps/ROOT

# 將編譯好的 WAR 檔複製為 ROOT.war (直接作為網站根目錄部署)
COPY --from=builder /app/target/*.war webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]