FROM node
RUN mkdir -p /code
ADD . /code
WORKDIR /code/ui
RUN yarn install && yarn build

FROM openjdk:11-jdk
RUN mkdir -p /code
ADD . /code
WORKDIR /code
RUN ./gradlew stage

FROM openjdk:11-jre
RUN mkdir -p /code/ui
WORKDIR /code/ui
COPY --from=0 /code/ui/build .
WORKDIR /code
COPY --from=1 /code/build/install/theagainagain .
CMD /code/bin/theagainagain