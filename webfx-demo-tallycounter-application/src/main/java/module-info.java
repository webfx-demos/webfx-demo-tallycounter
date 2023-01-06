// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.demo.tallycounter.application {

    // Direct dependencies modules
    requires javafx.base;
    requires javafx.graphics;
    requires webfx.extras.led;
    requires webfx.lib.odometer;
    requires webfx.platform.storage;

    // Exported packages
    exports dev.webfx.demo.tallycounter;

    // Provided services
    provides javafx.application.Application with dev.webfx.demo.tallycounter.TallyCounterApplication;

}