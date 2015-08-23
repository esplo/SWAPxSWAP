FROM centos:7
MAINTAINER esplo

RUN curl https://bintray.com/sbt/rpm/rpm | tee /etc/yum.repos.d/bintray-sbt-rpm.repo
RUN yum -y update && \
    yum -y install git sbt wget bzip2

RUN git clone https://github.com/esplo/SWAPxSWAP.git swapxswap

ENV PHANTOMJS_VERSION 1.9.8
RUN wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-${PHANTOMJS_VERSION}-linux-x86_64.tar.bz2 -O /tmp/phantomjs.tar.bz2 && \
    tar -xjf /tmp/phantomjs.tar.bz2 -C /tmp && \
    mv /tmp/phantomjs-${PHANTOMJS_VERSION}-linux-x86_64 /phantomjs

WORKDIR swapxswap

# cache
VOLUME ["/root/.ivy", "/root/.ivy2"]

ADD app.conf /swapxswap/
