# 사내 웹시스템 배포 가이드

## 1. 요구사항
- JDK 17, Maven 3.9+, MySQL 8.0, Apache Tomcat 10.1

## 2. DB 준비
```bash
mysql -u root -p < src/main/resources/schema.sql
# 계정 생성 예
CREATE USER 'intra'@'%' IDENTIFIED BY 'intra1234';
GRANT ALL ON intra.* TO 'intra'@'%';
```

## 3. admin 초기 비밀번호 생성 (BCrypt)
DB에 더미 해시가 들어있으므로 반드시 재생성:
```bash
# 임시 실행용 Java 또는 운영자 PC에서 Spring Security로 생성 후 UPDATE
UPDATE CM_USER SET PWD='<BCrypt 해시>' WHERE USER_ID='admin';
```

## 4. 로컬 실행 (내장 Tomcat)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
# http://localhost:8080/login
```

## 5. 외장 Tomcat 배포 (WAR)
```bash
./mvnw clean package -Pprod -DskipTests
# target/intra.war -> $CATALINA_HOME/webapps/intra.war 로 복사
# $CATALINA_HOME/bin/startup.sh
# http://서버:8080/intra/login
```

## 6. 다음 화면 개발 패턴
1. `UserController.list` 복사 → 신규 Controller
2. `UserRepository.findPage` 복사 → 신규 SQL
3. `templates/system/userList.html` 복사 → 신규 html (반드시 layout/default 상속)
4. `CM_MENU`에 URL 등록 → 좌측 메뉴 자동 노출
