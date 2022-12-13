# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por �ltimo el inicio y configuraci�n de la aplicaci�n.

Lee el art�culo [Clean Architecture � Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el m�dulo m�s interno de la arquitectura, pertenece a la capa del dominio y encapsula la l�gica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este m�dulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define l�gica de aplicaci�n y reacciona a las invocaciones desde el m�dulo de entry points, orquestando los flujos hacia el m�dulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no est�n arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
gen�ricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patr�n de dise�o [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicaci�n o el inicio de los flujos de negocio.

## Application

Este m�dulo es el m�s externo de la arquitectura, es el encargado de ensamblar los distintos m�dulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma autom�tica, inyectando en �stos instancias concretas de las dependencias declaradas. Adem�s inicia la aplicaci�n (es el �nico m�dulo del proyecto donde encontraremos la funci�n �public static void main(String[] args)�.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**


## Mi hola Mundo
-Crear archivo build.gradel para poder usar el arquetipo de bancolombia, dentro de la carpeta que usaremos como proyecto
```html
plugins {
	id 'co.com.bancolombia.cleanArchitecture' version "2.4.4"
}
```
-ver tareas del plugin
gradle tasks

-Se crea estructura de proyecto
gradle ca --package=co.com.nequi --type=reactive --name=HolaMundo --coverage=jacoco --lombok=true

-Agregar en el main.gradle las dependencias necesarias
```html
allprojects {
    repositories {
        mavenCentral()
        maven { url "https://repo.spring.io/snapshot" }
        maven { url "https://repo.spring.io/milestone" }
    }
}

subprojects {
    apply plugin: 'java'
    apply plugin: 'jacoco'
    apply plugin: 'io.spring.dependency-management'

    compileJava.dependsOn validateStructure
    sourceCompatibility = JavaVersion.VERSION_1_8

    dependencies {
        implementation 'io.projectreactor:reactor-core'
        implementation 'io.projectreactor.addons:reactor-extra'

        testImplementation 'io.projectreactor:reactor-test'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'

        testImplementation(platform('org.junit:junit-bom:5.8.1'))
        testImplementation('org.junit.jupiter:junit-jupiter')
        testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '4.0.0'

        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        compileOnly "org.projectlombok:lombok:${lombokVersion}"
        annotationProcessor  "org.projectlombok:lombok:${lombokVersion}"
        testCompileOnly  "org.projectlombok:lombok:${lombokVersion}"
        testAnnotationProcessor  "org.projectlombok:lombok:${lombokVersion}"
        implementation platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
    }

    test.finalizedBy(project.tasks.jacocoTestReport)

    jacocoTestReport {
        dependsOn test
        reports {
            xml.enabled true
            xml.destination file("${buildDir}/reports/jacoco.xml")
            csv.enabled false
            html.destination file("${buildDir}/reports/jacocoHtml")
        }
    }

    test {
        useJUnitPlatform {
            includeEngines 'junit-jupiter'
        }
        filter {
            //include all integration tests
            includeTestsMatching "*Test"
        }
    }

}

jacoco {
    toolVersion = "${jacocoVersion}"
    reportsDir = file("$buildDir/reports")
}

task jacocoMergedReport(type: JacocoReport) {
    dependsOn = subprojects.jacocoTestReport
    additionalSourceDirs.setFrom files(subprojects.sourceSets.main.allSource.srcDirs)
    sourceDirectories.setFrom files(subprojects.sourceSets.main.allSource.srcDirs)
    classDirectories.setFrom files(subprojects.sourceSets.main.output)
    executionData.setFrom project.fileTree(dir: '.', include: '**/build/jacoco/test.exec')
    reports {
        xml.enabled true
        csv.enabled false
        html.enabled true
    }
}

tasks.withType(JavaCompile) {
    options.compilerArgs = [
            '-Amapstruct.suppressGeneratorTimestamp=true'
    ]
}
tasks.named('wrapper') {
  gradleVersion = '6.7'
 }
```
-Se crea el entry point
gradle generateEntryPoint --type=webflux
Queda en infrestructure.entryPoints
RouterRest son puntos de entrada a nuestros casos de uso (REST)

-Se crea el caso de uso
gradle generateUseCase --name=holaMundoRest
Queda en domain.usecase
Los casos de uso llevan la logica de negocio

-Se crea el Modelo
gradle generateModel --name=modelHola

-En el handler se invoca el caso de uso y se mapea la respuesta

## Errores Encontrados
Para el error cannot resolve method 'run(class string )' SpringApplication.run
Cambiar el SDK, click derecho sobre el proyecto y escoger "Open Module Settings" -> Project y escoger SDK deseado (jdk 11)