# 階段一：使用 Maven + Java 21 編譯 WAR 檔
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 階段二：使用 Tomcat 9 運行環境
FROM tomcat:9.0-jdk21-temurin
WORKDIR /usr/local/tomcat

# 1. 停用 Tomcat 的 8005 Shutdown Port (避免 Render 健康檢查誤打)
RUN sed -i 's/port="8005"/port="-1"/' conf/server.xml

# 2. 清理 Tomcat 預設的 ROOT 專案
RUN rm -rf webapps/ROOT

# 3. 將編譯好的 WAR 檔複製為 ROOT.war (根目錄部署)
COPY --from=builder /app/target/*.war webapps/ROOT.war

# 4. 指定 Render 預設的 8080 Port
ENV PORT=8080
EXPOSE 8080

CMD ["catalina.sh", "run"]