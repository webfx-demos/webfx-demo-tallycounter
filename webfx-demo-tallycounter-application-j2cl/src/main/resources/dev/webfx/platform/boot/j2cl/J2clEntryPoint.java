package dev.webfx.platform.boot.j2cl;

import dev.webfx.platform.boot.ApplicationBooter;
import dev.webfx.platform.boot.spi.ApplicationBooterProvider;
import dev.webfx.platform.util.elemental2.Elemental2Util;

import org.treblereel.j2cl.processors.annotations.GWT3EntryPoint;

import static dev.webfx.platform.service.gwtj2cl.ServiceRegistry.*;

public final class J2clEntryPoint implements ApplicationBooterProvider {

    @GWT3EntryPoint
    public void entryPoint() {
        registerArrayConstructors();
        registerServiceProviders();
        ApplicationBooter.start(this, null);
        Elemental2Util.registerPwa();
    }

    private static void registerArrayConstructors() {

    }

    private static void registerServiceProviders() {
        register(dev.webfx.kit.launcher.spi.WebFxKitLauncherProvider.class, dev.webfx.kit.launcher.spi.impl.elemental2.Elemental2WebFxKitLauncherProvider::new);
        register(dev.webfx.kit.mapper.spi.WebFxKitMapperProvider.class, dev.webfx.kit.mapper.spi.impl.elemental2.Elemental2WebFxKitHtmlMapperProvider::new);
        register(dev.webfx.platform.boot.spi.ApplicationModuleBooter.class, dev.webfx.kit.launcher.WebFxKitLauncherModuleBooter::new, dev.webfx.platform.boot.spi.impl.ApplicationJobsInitializer::new, dev.webfx.platform.boot.spi.impl.ApplicationJobsStarter::new, dev.webfx.platform.resource.spi.impl.web.WebResourceModuleBooter::new);
        register(dev.webfx.platform.console.spi.ConsoleProvider.class, dev.webfx.platform.console.spi.impl.elemental2.Elemental2ConsoleProvider::new);
        register(dev.webfx.platform.os.spi.OperatingSystemProvider.class, dev.webfx.platform.os.spi.impl.elemental2.Elemental2OperatingSystemProvider::new);
        register(dev.webfx.platform.resource.spi.ResourceProvider.class, dev.webfx.platform.resource.spi.impl.elemental2.Elemental2ResourceProvider::new);
        register(dev.webfx.platform.resource.spi.impl.web.WebResourceBundle.class, dev.webfx.platform.resource.j2cl.J2clEmbedResourcesBundle.ProvidedJ2clResourceBundle::new);
        register(dev.webfx.platform.scheduler.spi.SchedulerProvider.class, dev.webfx.platform.uischeduler.spi.impl.elemental2.Elemental2UiSchedulerProvider::new);
        register(dev.webfx.platform.shutdown.spi.ShutdownProvider.class, dev.webfx.platform.shutdown.spi.impl.elemental2.Elemental2ShutdownProvider::new);
        register(dev.webfx.platform.storage.spi.LocalStorageProvider.class, dev.webfx.platform.storage.spi.impl.elemental2.Elemental2LocalStorageProvider::new);
        register(dev.webfx.platform.storage.spi.SessionStorageProvider.class, dev.webfx.platform.storage.spi.impl.elemental2.Elemental2SessionStorageProvider::new);
        register(dev.webfx.platform.uischeduler.spi.UiSchedulerProvider.class, dev.webfx.platform.uischeduler.spi.impl.elemental2.Elemental2UiSchedulerProvider::new);
        register(dev.webfx.platform.useragent.spi.UserAgentProvider.class, dev.webfx.platform.useragent.spi.impl.elemental2.Elemental2UserAgentProvider::new);
        register(javafx.application.Application.class, dev.webfx.demo.tallycounter.TallyCounterApplication::new);
    }
}