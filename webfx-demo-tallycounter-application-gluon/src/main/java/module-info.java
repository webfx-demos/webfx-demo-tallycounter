// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.demo.tallycounter.application.gluon {

    // Direct dependencies modules
    requires webfx.demo.tallycounter.application;
    requires webfx.kit.openjfx;
    requires webfx.platform.boot.java;
    requires webfx.platform.console.java;
    requires webfx.platform.resource.java;
    requires webfx.platform.scheduler.java;
    requires webfx.platform.shutdown.gluon;

    // Meta Resource package
    opens dev.webfx.platform.meta.exe;

}