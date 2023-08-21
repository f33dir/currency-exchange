FROM openjdk:11-jdk

ENV DATABASE_URL=jdbc:postgresql://database:5432/currencyexchange
ENV DATABASE_USER=postgres
ENV DATABASE_PASSWORD=postgres
RUN apt install bash
WORKDIR /currency-exchange

CMD ./gradlew update test run