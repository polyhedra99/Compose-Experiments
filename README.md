# Compose-Experiments
A simple demo of animations and states in Compose. There is no architecture in this project, this is just a collection of screens, written with Jetpack Compose.

# Navigation Drawer
The navigation drawer is clearly the main point of interest in this project. The design itself is heavily inspired by Jetlag application, yet the implementation is much better. It features anchored draggable state (unlike the original), integration with navigation controller and much better animations on screen change. The AGSL shader on the background is also pretty cool.
Here is a short preview:
https://github.com/polyhedra99/Compose-Experiments/assets/34110629/9b047000-ed14-4239-8b61-7ea05c0a5fc8

# Work List, the first screen
Somewhat resembling a work chat application. Padding gets smaller further down the screen, creating an illusion of depth. Same goes for background color. AGSL shader on the foreground of sample avatars creates an effect of light reflection. Horizontal drag is disabled when the drawer is opened to easily get back to the screen, avoiding frustrating UX. Performance was the main topic of interest, so this screen underwent quite a few optimisations to avoid stutters and lag.

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/1a2eb573-490e-4c0c-b639-cfbf8877ccd9">

# Demo Pager, the second screen
Despite being the second in the list, this one was written as a last one, when I ran out of any other ideas. It provides a pager with increasingly darker colors, based on the RGB values, provided by the sliders below. The background of the sliders card is also changed accordingly. Text's color is the inverted value of the background. Adjacent items are scaled down, with alpha reduced. This creates an illusion of depth with minimal computations required. 

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/14d037bf-f4c0-45fe-b527-40163b7e0f04">

# Demo Plot, third screen
This screen is a bit different, as it's basically a collection of plots, drawn on the canvas. The interesting thing is the moving circles, following the function's path, based on the time value. Not really something you would see in a real app, but is a good showcase of animating offset, nonetheless.

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/917902b4-5025-4d10-89c6-0975a570c1de">

# Conclusion
Naturally, there are improvements to be made. For example, some parameters could be extracted into dedicated models, readability of composables that depend on many states could be improved. Not going to lie, I will likely never do that, as I kind of lost the dedication for this project and simply wanted to achieve some resonable results, not a state-of-the-art code.
That said, the first version of this project was written over the span of the weekend and I am satisfied with the way it turned out, especially the drawer. Delivery tops perfectionism.
