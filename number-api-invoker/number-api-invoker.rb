webBeans = Java.type("org.apache.webbeans.config.WebBeansContext").currentInstance()
lifecyle = webBeans.getService(Java.type("org.apache.webbeans.spi.ContainerLifecycle"))
lifecyle.startApplication(nil)

numberApi = Java.type("javax.enterprise.inject.spi.CDI")
                .current()
                .select(Java.type("org.tomitribe.graalvm.microprofile.number.api.client.NumberResourceApi"))
                .get()

print numberApi.getNumber()
