FROM curlimages/curl:8.4.0 AS downloader
ARG git_user
ARG git_token

RUN cd /home/curl_user &&\
    curl --insecure -sL -H "Authorization: token $git_token" -L https://api.github.com/repos/$git_user/energy-optimizer/tarball > tarball.tar.gz

FROM gradle:8.5.0-jdk21 AS builder
ARG git_user

COPY --from=downloader /home/curl_user/tarball.tar.gz /tarball.tar.gz

RUN cd / &&\
    tar xf tarball.tar.gz &&\
    rm tarball.tar.gz &&\
    mv $git_user-energy-optimizer* energy-optimizer &&\
    cd /energy-optimizer &&\
    gradle build -x test --no-daemon &&\
    mv build/libs/energy-optimizer-*-SNAPSHOT.jar energy-optimizer.jar

FROM openjdk:21-jdk as runner

COPY --from=builder /energy-optimizer/energy-optimizer.jar /energy-optimizer/energy-optimizer.jar

RUN echo "Europe/Berlin" > /etc/timezone

ENV TZ "Europe/Berlin"

WORKDIR /energy-optimizer

HEALTHCHECK --interval=20s --timeout=5s --start-period=40s --retries=5 CMD curl --fail --silent localhost:8080/actuator/health | grep UP || exit 1

CMD ["java", "-jar", "/energy-optimizer/energy-optimizer.jar"]
