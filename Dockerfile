FROM clojure:openjdk-8-lein-2.8.3
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY project.clj /usr/src/app/
RUN lein deps
COPY . /usr/src/app
RUN lein ring uberjar
CMD ["java", "-jar", "target/clojure-rest-0.1.0-SNAPSHOT-standalone.jar"]
