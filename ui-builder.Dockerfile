FROM node
RUN apt update && apt install python-pip -y && pip install awscli
