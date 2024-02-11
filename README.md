# Compose-Experiments
A simple demo of animations and states in Compose. There is no architecture in this project, this is just a collection of screens, written with Jetpack Compose.

# Navigation Drawer
The navigation drawer is clearly the main point of interest in this project. The design itself is heavily inspired by Jetlag application, yet the implementation is much better. It features anchored draggable state (unlike the original), integration with navigation controller and much better animations on screen change. The AGSL shader on the background is just a slightly changed version of the one, presented in said Jetlag.
Here is a short preview:
https://github.com/polyhedra99/Compose-Experiments/assets/34110629/fdf8fba2-e38f-4cc6-a428-6787778685e7


# Demo List, first screen
This is just a plane simple list of items. Even items are a bit larger to create an illusion of depth. They are also clickable, but it's just a simple vertical expansion, I didn't have enough time to create a custom full screen version of the selected item.

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/31523f7c-7b7c-4c83-b2b9-4f307cd6186a">

# Demo Pager, second screen
Despite being the second in the list, this one was written as a last one, when I ran out of any other ideas. It provides a pager with increasingly darker colors, based on the RGB values, provided by the sliders below. The background of the sliders card is also changed accordingly. Text's color is the inverted value of the background.

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/50ff1bc2-13b0-4e52-aff4-d5c5fca395ed">

# Demo Plot, third screen
This screen is a bit different, as it's basically a collection of plots, drawn on the canvas. The interesting thing is the moving circles, following the function's path, based on the time value. Not really something you would see in a real app, but is a good showcase of animating offset, nonetheless.

<img width="240" alt="image" src="https://github.com/polyhedra99/Compose-Experiments/assets/34110629/917902b4-5025-4d10-89c6-0975a570c1de">

# Conclusion
Naturally, there are improvements to be made. For example, I didn't consider performance issues as it didn't lag on my emulator whatsoever, so no optimisations were made. On top of that, some parameters could be extracted into dedicated models.
That said, this project was written over the span of the weekend and I am satisfied with the way it turned out, especially the drawer. Delivery tops perfectionism.
