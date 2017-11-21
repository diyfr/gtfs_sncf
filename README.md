# GTFSLibrary

Cette librairie réalisée en 2015 permet de déserialisez les données GTFS [General Transit Feed Specification](http://fr.wikipedia.org/wiki/General_Transit_Feed_Specification) fournies sur le site
[OpenData SNCF](https://ressources.data.sncf.com/explore/dataset/sncf-ter-gtfs/)

## Fonctionnalités

* Objets GTFS
*Les methodes toString() retourne l'objet au format JSON*
* Parser
* Exemple de chargement de données
* Exemple de filtre et recherches sur ces données
* * Par numéro de train
* * Par Date de circulation (compris les trains avec saut de nuit)
* * Par numéro de train et une date.
* Calcul d'itinéraire

Le sous package démo contient différents exemples permettant les manipulations de ces données.

### Build
```bash
mvn package
```
### Exécution de la démo 
Les actions dans le code: [MainApplication.java](https://github.com/diyfr/gtfs_sncf/blob/master/src/main/java/fr/diyfr/sncf/gtfs/demo/MainDemo.java)  
```bash
java -jar gtfs-1.0.02.10.jar <Dossier contenant les données>
```



