Back-end & Simulation
===

Projet Scientifique du S7 de la formation IRC de l'école d'ingénieurs CPE Lyon

## Utiliser le projet

### Production

Build image
```
./gradlew buildImage
```

Load image
```
docker load < build/jib-image.tar
docker run backend-docker-image
```

Build & Run image directly
```
./gradlew runDocker
```

### Developpement

Hot Reload
```
./gradlew -t build
```

Run
```
gradle run
```