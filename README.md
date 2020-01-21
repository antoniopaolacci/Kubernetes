# Kubernetes
Introduzione a Kubernetes

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/architettura-generale.jpg)

Gli elementi che compongono Kubernetes:

- cluster: costituito da un master-node e worker-node
- master-node: è la macchina che esegue le operazioni di controllo del nostro cluster
- worker-node: sono le macchine che eseguono i pod, l'astrazione di unità minima gestita dal Kubernetes
- pod: elemento di elaborazione minimo gestita dal Kubernetes, hanno un indirizzo ip, possono contenere uno o più docker container (esistono patter che accoppiano un container che esegue un servizio con un *init-container* un container dedicato alle sole operazioni di configurazione, creazione e scrittura su volumi)
- deployment: oggetto importante che scatena le seguenti operazioni sul cluster Kubernetes: creazione di oggetti di tipo *replicaset* che istanziano a loro volta i pod sulle varie macchine
- persistent volume claim: il cluster Kubernetes crea il volume sul network filesystem del provider esterno e lo collega al pod

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/pod.jpg)

La nomenclatura:
 - Docker Engine (DE), il demone engine Docker running sulle macchine del cluster
 - Images, the artifacts need to be built, once running they are called *containers* on docker-host and *pods* in Kubernetes scenario
 - Command Line Interface (CLI), il client SDK, running on *clientside*, sull'host del developer e necessario per inviare in remoto comandi *kubectl* al cluster kubernetes
 - Docker Hub, the public registry where to upload our images provided by docker
 - Google Container Registry (GCR), Azure Container Registry (ACR), il registry rispettivamente fornito da Google e Microsoft

Installazione su Public Cloud Providers di *Kubernetes as a Service* 

ref.  https://blog.alterway.fr/en/kubernetes-101-launch-your-first-kubernetes-app.html








