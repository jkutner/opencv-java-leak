FROM heroku/heroku:16-build

RUN apt-get update
RUN apt-get install build-essential -y
RUN apt-get install cmake git libgtk2.0-dev pkg-config libavcodec-dev libavformat-dev libswscale-dev -y
RUN apt-get install python-dev python-numpy libtbb2 libtbb-dev libjpeg-dev libpng-dev libtiff-dev libjasper-dev libdc1394-22-dev -y
RUN apt-get install openjdk-8-jdk ant -y

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

RUN curl -o opencv.zip -L https://github.com/opencv/opencv/archive/3.3.1.zip

RUN unzip opencv.zip -d opencv

WORKDIR opencv/opencv-3.3.1
RUN mkdir build
WORKDIR build

RUN cmake -D CMAKE_BUILD_TYPE=Release -D CMAKE_INSTALL_PREFIX=/usr/local ..

RUN unset BUILD_SHARED_LIBS
RUN make -j7

RUN make install
RUN pwd
RUN mkdir /app
ADD . /app
WORKDIR /app
RUN ./mvnw clean
RUN ./mvnw install
