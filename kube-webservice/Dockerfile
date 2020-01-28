FROM java:8
COPY target/kube-webservice-0.0.1.jar .
VOLUME /tmp
CMD /usr/bin/java -Xmx96m -Xss512k -jar kube-webservice-0.0.1.jar
EXPOSE 5051

