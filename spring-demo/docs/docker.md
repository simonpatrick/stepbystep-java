#DockerFile
# https://cnodejs.org/topic/53f494d9bbdaa79d519c9a4a

FROM docker pull docker.cn/docker/ubuntu
MAINTAINER :simon patrick
RUN apt-get update

Questions:
# What is the Dockerfile instruction to specify the base image?
    The right answer was FROM
# Which Dockerfile instruction sets the default command for your image?
    The right answer was ENTRYPOINT or CMD
# What is the character used to add comments in Dockerfiles?
    The right answer was #
# Which Dockerfile instruction sets the username to use when running the image?
    The right answer was USER
# What is the Dockerfile instruction to execute any command on the current image and commit the results?
    The right answer was RUN
# Which Dockerfile instruction sets ports to be exposed when running the image?
    The right answer was EXPOSE
# What is the Dockerfile instruction to specify the maintainer of the Dockerfile?
    The right answer was MAINTAINER
# Which Dockerfile instruction lets you trigger a command as soon as the container starts?
    The right answer was ENTRYPOINT or CMD

## https://docs.docker.com/userguide/level2/


``` bash
    # Redis
    #
    # VERSION       0.42
    #
    # use the ubuntu base image provided by dotCloud
     FROM  ubuntu

    MAINTAINER Ro Ha roberto.hashioka@dotcloud.com

    # make sure the package repository is up to date
     RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list
     RUN apt-get update

    # install wget (required for redis installation)
     RUN apt-get install -y wget

    # install make (required for redis installation)
     RUN apt-get install -y make

    # install gcc (required for redis installation)
    RUN RUN apt-get install -y gcc

    # install apache2
     RUN wget http://download.redis.io/redis-stable.tar.gz
    RUN tar xvzf redis-stable.tar.gz
    RUN cd redis-stable && make && make install

    # launch redis when starting the image
     ENTRYPOINT ["redis-server"]

    # run as user dameon
     USER daemon

    # expose port 6379
     EXPOSE 6379
```