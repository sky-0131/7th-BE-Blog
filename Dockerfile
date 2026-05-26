# 베이스 이미지
FROM openjdk:21-jdk-slim
# 빌드된 jar 파일을 복사
COPY build/libs/*.jar app.jar
# 컨테이너 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
