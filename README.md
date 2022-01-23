# MovieExplorer
MovieExplorer is a simple project that demonstrates approach for building modularized android apps.
Every module has its own api API - dependencies that it requires and classes that it exposes to the outside world.
Each module has its own frameworks independent graph of dependencies.
Project contains two types of modules: core and feature modules. Core modules encapsulate set of classes that serve same purpose, ex. :network, :database etc.
Feature modules represent separate UI flow, ex. :movies:feature-populart or :movies:feature-favourite.
Individual modules are splitted into data-domain-presentation layers (if it makes sense).
## Conventions 
 - feature modules should not depend on each other
 - dependency graph for every feature module should be released after user leaves UI flow
## Tech stack
Clean Architecture, MVVM, Kotlin, Coroutines, Dugger2, Room, Retrofit, Paging3, Navigation Component
## UI
![readme_screenshot](https://user-images.githubusercontent.com/11457475/150688602-a5e803af-83bc-4586-b9d7-a7171b3d4abb.jpg)

