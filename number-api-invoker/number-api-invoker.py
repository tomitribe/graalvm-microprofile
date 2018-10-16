import java

webBeans = java.type("org.apache.webbeans.config.WebBeansContext").currentInstance()
lifecyle = webBeans.getService(java.type("org.apache.webbeans.spi.ContainerLifecycle"))
lifecyle.startApplication(None)

numberApi = java.type("javax.enterprise.inject.spi.CDI")\
    .current()\
    .select(java.type("org.tomitribe.graalvm.microprofile.number.api.client.NumberResourceApi"))\
    .get()

print(numberApi.getNumber())
