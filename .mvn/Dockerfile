FROM daggerok/jboss-eap-7.2:7.2.0-alpine
HEALTHCHECK --timeout=3s \
            --retries=33 \
            CMD wget -q --spider http://127.0.0.1:8080/app/ || exit 1
ENV JAVA_OPTS="${JAVA_OPTS} \
  -XX:+UnlockExperimentalVMOptions \
  -XX:+UseCGroupMemoryLimitForHeap \
  -XshowSettings:vm \
  -Xms1303m \
  -Xmx1303m \
  -XX:MetaspaceSize=96M \
  -XX:MaxMetaspaceSize=256m \
  -Djava.net.preferIPv4Stack=true \
  -Djboss.modules.system.pkgs=org.jboss.byteman \
  -Djava.awt.headless=true"
RUN echo "JAVA_OPTS=\"\$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \"" >> ${JBOSS_HOME}/bin/standalone.conf
EXPOSE 5005
COPY --chown=jboss ./target/*.war ${JBOSS_HOME}/standalone/deployments/app.war
