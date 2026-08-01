FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-Dspring.data.mongodb.uri=mongodb+srv://anuragkumar78702685001_db_user:Pafy3ZrCqKPjXT8c@cluster0.pawm3gz.mongodb.net/chatApp_db?retryWrites=true&w=majority&appName=Cluster0", "-jar", "target/chat_app-0.0.1-SNAPSHOT.jar"]