======= GEN-Frogger =====

--- Prérequis ---- 
- Java >= 8
- Maven

--- Lancer le serveur ---
Exécuter le fichier server.jar

-- Lancer le client ---
1. Définir l'adresse du serveur dans le fichier config.properties
2. Exécuter le fichier client.jar

--- Compiler depuis les sources ---
1. Se rendre dans le dossier ./sources/protocol
2. Compiler le protocol avec la commande `mvn clean install`
3. Se rendre dans le dossier ./sources/client
4. Compiler le client avec la commande `mvn clean install`
	-> L'exécutable .jar est disponible dans le dossier ./sources/client/target/
5. Se rendre dans le dossier ./sources/server
6. Compiler le serveur avec la commande `mvn clean install`
	-> L'exécutable .jar est disponible dans le dossier ./sources/server/server-application/target/

