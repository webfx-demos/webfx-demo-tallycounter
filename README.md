# Live demo

The demo is published on [this page][demo-live-link].

This is a simple Tally counter using an odometer to display the counter.
The plus & minus buttons are to increment & decrement the counter as you would expect.
The layout is especially designed for mobiles, and you can press the blue button to swap the buttons when you change hands.
The red button is to reset the counter. Here is the [source code][demo-source-link].

# Highlighted features

(in addition to those already explained in the [previous demo][previous-demo-repo-link])

## Reusability

The odometer control was actually already implemented in JavaFx on this [Github repository][hansolo-odometer-link].
This is an example of how you can benefit from the JavaFx ecosystem and reuse or port existing JavaFx libraries in your WebFx applications.

## Responsive design

It's not well-known but JavaFx has a great feature that makes responsive design easy and powerful:
it can callback your code to let you decide how to layout your components and this at any level of the graph (this possibility doesn't exist in standard HTML/JS/CSS).

For example, by overriding the layoutChildren() method of a container, you can easily code your responsive design logic and decide how to resize and relocate your components inside this container in dependence of its width and height.
This technique is more powerful than CSS rules, because a code can access all your context and express all the complexity you want.

You can see in the [source code][demo-source-link] how this demo uses this technique to decide the position of the odometer and the buttons.
The power of this technique will become more evident with the [Enzo clocks demo][webfx-enzoclocks-repo-link] that codes a circle packer algorithm to layout its components.

[demo-live-link]: https://tallycounter.webfx.dev
[demo-source-link]: https://github.com/webfx-project/webfx-demo-tallycounter/blob/main/webfx-demo-tallycounter-application/src/main/java/dev/webfx/demo/tallycounter/TallyCounterApplication.java
[previous-demo-repo-link]: https://github.com/webfx-project/webfx-demo-particles
[hansolo-odometer-link]: https://github.com/HanSolo/odometer
[webfx-enzoclocks-repo-link]: https://github.com/webfx-project/webfx-demo-enzoclocks
