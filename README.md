## Kubernetes
Introduzione a Kubernetes

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/architettura-generale.jpg)

### Gli elementi che compongono Kubernetes:

- cluster: costituito da un master-node e worker-node
- master-node: è la macchina che esegue le operazioni di controllo del nostro cluster
- worker-node: sono le macchine che eseguono i pod, l'astrazione di unità minima gestita dal Kubernetes
- pod: elemento di elaborazione minimo gestita dal Kubernetes, hanno un indirizzo ip, possono contenere uno o più docker container (esistono patter che accoppiano un container che esegue un servizio con un *init-container* un container dedicato alle sole operazioni di configurazione, creazione e scrittura su volumi)
- deployment: oggetto importante che scatena le seguenti operazioni sul cluster Kubernetes: creazione di oggetti di tipo *replicaset* che istanziano a loro volta i pod sulle varie macchine
- persistent volume claim: il cluster Kubernetes crea il volume sul network filesystem del provider esterno e lo collega al pod

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/pod.jpg)

### Nomenclatura:
 - Docker Engine (DE), il demone engine Docker running sulle macchine del cluster
 - Images, the artifacts need to be built, once running they are called *containers* on docker-host and *pods* in Kubernetes scenario
 - Command Line Interface (CLI), il client SDK, running on *clientside*, sull'host del developer e necessario per inviare in remoto comandi *kubectl* al cluster kubernetes
 - Docker Hub, the public registry where to upload our images provided by docker (https://hub.docker.com/repository/docker/<docker-userId>)
 - Google Container Registry (GCR), Azure Container Registry (ACR), i registry di immagini rispettivamente forniti da Google e Microsoft

### Installazione su Public Cloud Providers di *Kubernetes as a Service* 
ref.  https://blog.alterway.fr/en/kubernetes-101-launch-your-first-kubernetes-app.html

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/providers.jpg)

### You can use a free service to test with your github account: 
https://kubesail.com/ Paste *Lets get started* code details into ~/.kube/config on your developer machine to access your cluster using kubectl *cli* command
![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/kubesail.jpg)

### Create your first pod based on *yml* file

```bat
kubectl create -f pod1.yml
pod/kube created
```

### View all pods

```bat
kubectl get pod
NAME   READY   STATUS    RESTARTS   AGE
kube   1/1     Running   0          11m
```

### View log to trace 
Visualizzare il log dell'applicazione, se in un pod sono presenti più contenitori dovremmo specificare il nome del pod ed il nome del contenitore:

```bat
kubectl logs kube
```

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

2020-01-21 15:01:54.495  INFO 2 --- [main] com.xantrix.webapp.Application           : Starting Application v0.0.1 on kube with PID 2 (/kube-webservice-0.0.1.jar started by root in /)
2020-01-21 15:01:54.502  INFO 2 --- [main] com.xantrix.webapp.Application           : The following profiles are active: dev
2020-01-21 15:01:58.895  INFO 2 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 5051 (http)
2020-01-21 15:01:58.962  INFO 2 --- [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-01-21 15:01:58.962  INFO 2 --- [main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.16]
2020-01-21 15:01:58.994  INFO 2 --- [main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib/amd64:/usr/lib/x86_64-linux-gnu/jni:/lib/x86_64-linux-gnu:/usr/lib/x86_64-linux-gnu:/usr/lib/jni:/lib:/usr/lib]
2020-01-21 15:01:59.466  INFO 2 --- [main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-01-21 15:01:59.467  INFO 2 --- [main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 4846 ms
2020-01-21 15:02:00.436  INFO 2 --- [main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-01-21 15:02:01.501  INFO 2 --- [main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 4 endpoint(s) beneath base path '/actuator'
2020-01-21 15:02:01.707  INFO 2 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 5051 (http) with context path ''
2020-01-21 15:02:01.730  INFO 2 --- [main] com.xantrix.webapp.Application           : Started Application in 8.774 seconds (JVM running for 10.016)
```

### View pod and labels
```bat
kubectl get pods --show-labels

NAME                               READY   STATUS    RESTARTS   AGE   LABELS
kube                               1/1     Running   0          31m   app=art,env=test
kube-deployment-8454999b96-m55rd   1/1     Running   0          11m   pod-template-hash=8454999b96,run=kube-deployment
```

Una volta che il nostro servizio o pod è stato testato con successo, è magari stato individuato per essere
invocato in produzione. Possiamo variarne l'etichetta o tag con il comando seguente:

### Change label of a pod
```bat
kubectl label po kube env=prod --overwrite
pod/kube labeled
```

In ambiente di produzione abbiamo centinaia di container running o pod, per cui sarà molto utile filtrare
per ottenere informazioni su un sottoinsieme di essi.

### Filter pod based on tag
```bat
kubectl get po -l env=prod
NAME   READY   STATUS    RESTARTS   AGE
kube   1/1     Running   0          35m
```

### Possiamo aggiungere un etichetta o tag per classificare i nostri pods
```bat
kubectl label po kube autore=antonio
pod/kube labeled

kubectl get pods --show-labels
NAME                               READY   STATUS    RESTARTS   AGE   LABELS
kube                               1/1     Running   0          46m   app=art,autore=antonio,env=prod
kube-deployment-8454999b96-m55rd   1/1     Running   0          26m   pod-template-hash=8454999b96,run=kube-deployment
```

### Possiamo escludere la visualizzazione di alcuni classi di pod sulla base dell'associazione con una etichetta o label
Vogliamo identificare tutti i pod che non presentano associata un'etichetta denominata *autore*
```bat
kubectl get po -l "!autore"

NAME                               READY   STATUS    RESTARTS   AGE
kube-deployment-8454999b96-m55rd   1/1     Running   0          98m
```

### Le label oltre ad essere associate ai pod possono essere associate anche ai *nodi*, un esempio:
```
kubectl label node gke-node-01-est-europe-45ed787ef env=test 
```

### Visualizzare tutti i nodi etichettati con un determinato valore di etichetta:
```bat
kubectl get nodes -l env=test 
```
E' utile etichettare anche i nodi perché attraverso questa è possibile associare i pod solo ad un determinato nodo. 
Di deafult l'attivazione del pod è arbitraria, il pod viene collocato in modalità casuale in uno dei nodi a disposizione.

E' possibile specificare nel file *.yml* l'associazione di un pod con un determinato nodo attraverso l'elemento *node-selector*

```yaml
--------------------------------kubernetes/pod.yml file

apiVersion: v1
kind: Pod
metadata:
  name: kube-v2
  labels:
    app: art
    env: test

spec:
  nodeSelector:
    env: "test"
  containers:
    - name: articoli-webservice
      image:  antoniopaolacci/kube-webservice:0.0.1
      ports:
        - name: service
          containerPort: 5051
          protocol: TCP
```

### Visualizzare tutti i namespace (raccoglitori in grado di catalogare e ragguppare tutti i pod afferenti allo stesso tipo)

E' possibile creare anche nuovi namespace in grado di raggruppare i nostri pod.

```bat
kubectl get ns

kubectl create -f namespace1.yml

--------------------------------kubernetes/namespace.yml file
apiVersion: v1
kind: Namespace
metadata:
  name: kube-webservice-namespace

```

### Eliminare un pod, eliminare tutti i pod eliminando il namespace
```bat
kubectl delete po kube
pod "kube" deleted.
```
Soluzione drastica per eliminare tutti i pod, è eliminare il namespace con il comando:
```bat
kubectl delete ns my-kube-webservice-namespace
```

### Replication controller, il primo automatismo
Una risorsa kubernetes che garantisce il continuo funzionamento di un determinato tipo o numero di pod. Monitora lo stato di un pod nel proprio dominio di responsabilità identificata dalla label (etichetta) che abbiamo associato al nostro pod. Gestisce il numero di repliche che vogliamo vengano attivate e lo manterrà costante aumentando o cancellando in maniera automatica i pod *running*.

- label selector, selezioniamo il dominio del replication controller
- replica count, numero di istanze che vogliamo siano sempre attive
- pod template

### Creare un replication controller


```bat
kubectl create -f replicationController1.yml
replicationcontroller/repcon-articoli created
```

```yaml
--------------------------------kubernetes/replicationController1.yml file
## Replication Controller

apiVersion: v1
kind: ReplicationController
metadata:
  name: repcon-articoli                                ## Specifichiamo il dominio, RC sovraintenderà ai POD di tipo articolo
spec:
  replicas: 3                                          ## Il numero di pod attivi sarà sempre uguali a 3
  selector:
    app: artsrv                                        ## Specifichiamo che l'app POD istanziati avranno l'identificativo 'artsrv'
  template:
    metadata:
      labels:
        app: artsrv                                    ## Il metadata del nostro POD avrà come riferimento la label app = 'artsrv' 
    spec:
      containers:                                      ## Specifichiamo il container che dovrà essere attivato
      - name: articoli-webservice
        image: antoniopaolacci/kube-webservice:0.0.1   ## Specifichiamo i campi <docker-hub-id>/<image-name>:<tag>
        ports:
        - containerPort: 5051
 ```

Visualizziamo cosa è successo:
```bat
kubectl get po --show-labels
NAME                               READY   STATUS    RESTARTS   AGE     LABELS
repcon-articoli-z9d8s              1/1     Running   0          5m42s   app=artsrv
repcon-articoli-z9aDs              1/1     Running   0          5m1s    app=artsrv
repcon-articoli-ef34s              1/1     Running   0          6m19s   app=artsrv
```
Sono stati creati ed attivati i tre POD ed etichettati con il riferimento (label) app=artsrv.
Anche cambiando label con il seguente comando, il RC manterrà sempre attive almeno 3 repliche del POD.
```bat
kubectl label pod repcon-articoli-z9d8s app=test --overwrite
pod/repcon-articoli-z9d8s labeled
```

```bat
kubectl get po --show-labels
NAME                               READY   STATUS    RESTARTS   AGE     LABELS
repcon-articoli-z9d8s              1/1     Running   0          5m42s   app=test
repcon-articoli-ab23s              1/1     Running   0          1m32s   app=artsrv
repcon-articoli-z9aDs              1/1     Running   0          5m1s    app=artsrv
repcon-articoli-ef34s              1/1     Running   0          6m19s   app=artsrv
```

### Visualizzare i dettagli di un replication controller

```bat
kubectl describe rc repcon-articoli

Name:         repcon-articoli
Namespace:    antoniopaolacci
Selector:     app=artsrv
Labels:       app=artsrv
Annotations:  <none>
Replicas:     1 current / 3 desired
Pods Status:  1 Running / 0 Waiting / 0 Succeeded / 0 Failed
Pod Template:
  Labels:  app=artsrv
  Containers:
   articoli-webservice:
    Image:        antoniopaolacci/kube-webservice:0.0.1
    Port:         5051/TCP
    Host Port:    0/TCP
    Environment:  <none>
    Mounts:       <none>
   Volumes:       <none>
Conditions:
  Type             Status  Reason
  ----             ------  ------
  ReplicaFailure   True    FailedCreate
Events:            <none>
```

### Replica set, sopperire alle mancanze del replication controller

Creare un replica set è simile alla creazione di ogni altro componente del kubernetes, via file *.yml* con il seguente comando:

```bat
kubectl create -f replicaSet1.yml
```

```yaml
# ReplicaSet 
apiVersion: apps/v1     # Differente tipologia, perché il replica set è gestito da differenti api del kubernetes
kind: ReplicaSet
metadata:
  name: repset-articoli
spec:                   # Numero delle specifiche
  replicas: 3
  selector:
    matchLabels:
        app: artsrv     # etichetta per selezione app=artsrv
  template:
    metadata:
      labels:
        app: artsrv     # creare i POD se essi non sono presenti e saranno generati con etichetta app=artsrv
    spec:               # e caratteristiche seguenti (container, name, image, ...)
      containers:
      - name: articoli-webservice
        image: antoniopaolacci/kube-webservice:0.0.1
        ports:
        - containerPort: 5051
```

```bat
kubectl get rs

NAME                         DESIRED   CURRENT   READY   AGE
kube-deployment-8454999b96   1         1         1       2d23h
repset-articoli              3         0         0       24s
```

Un replica set permette di avere maggiore flessibilità rispetto al replication controller nella selezione di pod che verranno inclusi nel dominio di controllo del nostro replica set. 

```yaml
# ReplicaSet

apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: repset-articoli
spec:
  replicas: 2
  selector:
    matchExpressions:
      - key: app
        operator: In
        values:
            - artsrv
            - barsrv
  template:
    metadata:
      labels:
        app: artsrv
    spec:
      containers:
      - name: articoli-webservice
        image: nlarocca/articoli-webservice-kube:0.0.1-SNAPSHOT
        ports:
        - containerPort: 5051
 
 ## Altri operatori  previsti:
  # In = La label del pod deve avere uno dei volori inseriti in values
  # NotIn = La label del pod NON deve avere uno dei valori inseriti in values
  # Exists = Il key del pod deve avere il nome della key specificato (values non utilizzabile)
  # DoesNotExist = Il key del pod NON deve avere il nome della key specificato (values non utilizzabile)
```

### Aumentare il numero delle repliche gestite da un replica set:
```bat
kubectl scale rs repset-articoli --replicas=1
```
