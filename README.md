# ApiRest-CardGame

"Card Game API" is a project made in [Kotlin](https://kotlinlang.org/) with [Ktor](https://ktor.io/).
The API allows you to manage a simple card game deck _(shuffle, take a card, put a card back)_.

# How to use it
## First step
Before running make sur that you have IDE (Eclipse Or  IntelliJ IDEA ), you can download it for [windows](https://www.jetbrains.com/fr-fr/idea/download/#section=windows)
or for [Linux](https://www.jetbrains.com/fr-fr/idea/download/#section=linux).

## Second step
You have ton configure your API, specifie _JDBC_DATABASE_URL_ and _JDBC_DATABASE_DRIVER_ in Environment variables on Configurations

Exemple :

JDBC_DRIVER=org.postgresql.Driver;JDBC_DATABASE_URL=jdbc:postgresql:yourDataBaseName?user=postgres&password=your_password

## Build
You can build the project and run it with _Application.kt_
