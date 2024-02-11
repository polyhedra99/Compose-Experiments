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
