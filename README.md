# Kubernetes
Introduzione a Kubernetes

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/architettura-generale.jpg)

## Gli elementi che compongono Kubernetes:

- cluster: costituito da un master-node e worker-node
- master-node: è la macchina che esegue le operazioni di controllo del nostro cluster
- worker-node: sono le macchine che eseguono i pod, l'astrazione di unità minima gestita dal Kubernetes
- pod: elemento di elaborazione minimo gestita dal Kubernetes, hanno un indirizzo ip, possono contenere uno o più docker container (esistono patter che accoppiano un container che esegue un servizio con un *init-container* un container dedicato alle sole operazioni di configurazione, creazione e scrittura su volumi)
- deployment: oggetto importante che scatena le seguenti operazioni sul cluster Kubernetes: creazione di oggetti di tipo *replicaset* che istanziano a loro volta i pod sulle varie macchine
- persistent volume claim: il cluster Kubernetes crea il volume sul network filesystem del provider esterno e lo collega al pod

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/pod.jpg)

## Nomenclatura:
 - Docker Engine (DE), il demone engine Docker running sulle macchine del cluster
 - Images, the artifacts need to be built, once running they are called *containers* on docker-host and *pods* in Kubernetes scenario
 - Command Line Interface (CLI), il client SDK, running on *clientside*, sull'host del developer e necessario per inviare in remoto comandi *kubectl* al cluster kubernetes
 - Docker Hub, the public registry where to upload our images provided by docker (https://hub.docker.com/repository/docker/<docker-userId>)
 - Google Container Registry (GCR), Azure Container Registry (ACR), i registry di immagini rispettivamente forniti da Google e Microsoft

## Installazione su Public Cloud Providers di *Kubernetes as a Service* 
ref.  https://blog.alterway.fr/en/kubernetes-101-launch-your-first-kubernetes-app.html

![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/providers.png)

## You can use a free service to test with your github account: 
https://kubesail.com/ Paste *Lets get started* code details into ~/.kube/config on your developer machine to access your cluster using kubectl *cli* command
![image](https://github.com/antoniopaolacci/Kubernetes/blob/master/img/kubesail.jpg)

## Create your first pod based on *yml* file

```
kubectl create -f pod1.yml
pod/kube created
```

## View all pods

```
kubectl get pod
NAME   READY   STATUS    RESTARTS   AGE
kube   1/1     Running   0          11m
```

## View log to trace 
Visualizzare il log dell'applicazione, se in un pod sono presenti più contenitori dovremmo specificare il nome del pod ed il nome del contenitore:

```
kubectl logs kube

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

## View pod and labels
```
kubectl get pods --show-labels

NAME                               READY   STATUS    RESTARTS   AGE   LABELS
kube                               1/1     Running   0          31m   app=art,env=test
kube-deployment-8454999b96-m55rd   1/1     Running   0          11m   pod-template-hash=8454999b96,run=kube-deployment
```

Una volta che il nostro servizio o pod è stato testato con successo, è magari stato individuato per essere
invocato in produzione. Possiamo variarne l'etichetta o tag con il comando seguente:

## Change label of a pod
```
kubectl label po kube env=prod --overwrite
pod/kube labeled
```

In ambiente di produzione abbiamo centinaia di container running o pod, per cui sarà molto utile filtrare
per ottenere informazioni su un sottoinsieme di essi.

## Filter pod based on tag
```
kubectl get po -l env=prod
NAME   READY   STATUS    RESTARTS   AGE
kube   1/1     Running   0          35m
```

## Possiamo aggiungere un etichetta o tag per classificare i nostri pods
```
kubectl label po kube autore=antonio
pod/kube labeled

kubectl get pods --show-labels
NAME                               READY   STATUS    RESTARTS   AGE   LABELS
kube                               1/1     Running   0          46m   app=art,autore=antonio,env=prod
kube-deployment-8454999b96-m55rd   1/1     Running   0          26m   pod-template-hash=8454999b96,run=kube-deployment
```

## Possiamo escludere la visualizzazione di alcuni classi di pod sulla base dell'associazione con una etichetta o label
Vogliamo identificare tutti i pod che non presentano associata un'etichetta denominata *autore*
```
kubectl get po -l "!autore"

NAME                               READY   STATUS    RESTARTS   AGE
kube-deployment-8454999b96-m55rd   1/1     Running   0          98m
```

## Le label oltre ad essere associate ai pod possono essere associate anche ai *nodi*, un esempio:
```
kubectl label node gke-node-01-est-europe-45ed787ef env=test 
```

## Visualizzare tutti i nodi etichettati con un determinato valore di etichetta:
```
kubectl get nodes -l env=test 
```
E' utile etichettare anche i nodi perché attraverso questa è possibile associare i pod solo ad un determinato nodo. 
Di deafult l'attivazione del pod è arbitraria, il pod viene collocato in modalità casuale in uno dei nodi a disposizione.

E' possibile specificare nel file *.yml* l'associazione di un pod con un determinato nodo attraverso l'elemento *node-selector*

```
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

## Visualizzare tutti i namespace (raccoglitori in grado di catalogare e ragguppare tutti i pod afferenti allo stesso tipo)

E' possibile creare anche nuovi namespace in grado di raggruppare i nostri pod.

```
kubectl get ns

kubectl create -f namespace1.yml

--------------------------------kubernetes/namespace.yml file
apiVersion: v1
kind: Namespace
metadata:
  name: kube-webservice-namespace

```


### Eliminare un pod, eliminare tutti i pod eliminando il namespace
```
kubectl delete po kube
pod "kube" deleted.
```
Soluzione drastica per eliminare tutti i pod, è eliminare il namespace con il comando:
```
kubectl delete ns my-kube-webservice-namespace
```



