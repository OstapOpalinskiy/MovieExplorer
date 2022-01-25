# MovieExplorer
MovieExplorer is a simple project that demonstrates approach for building modularized android apps.
Every module has its own API - dependencies that it requires and classes that it exposes to the outside world.
Each module has its own, frameworks independent, graph of dependencies.
Project contains two types of modules: core and feature modules. Core modules encapsulate set of classes that serve same purpose (:network, :database etc).
Feature modules represent separate UI flow (:movies:feature-popular or :movies:feature-favourite).
Individual modules are splitted into data-domain-presentation layers (if it makes sense).
## Conventions 
 - feature modules should not depend on each other
 - dependency graph for every feature module should be released after user leaves UI flow
## Tech stack
Clean Architecture, MVVM, Kotlin, Coroutines, Dugger2, Room, Retrofit, Paging3, Navigation Component
## UI
https://user-images.githubusercontent.com/11457475/150956645-77528461-0d66-4ea7-92f9-4bf20eb4ab58.mp4


