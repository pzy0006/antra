package com.unicourse.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

 Spring security 6.X -> Authentication and autorization 
 Authentication : who are you
 authorization: what permission(s) you have

@EnableWebSecurity ->  web level protector

this is an entry point for integrating and starting spring security with the web layer.

@EanleMethodSecurity -> a method layer protector
allows you to use authorization annotations ( preAuthorze, post authorize annotations) on methods in the service or repo layer

PreAuthorize and post authorize -> authorization check 
Pre -> performs an authorization check before the method is executed
@PreAutorize("hasRole('ADMIN')")
public String myName(String myName){...}
hasRole('ADMIN') -> SqEL -> Spring expression language -> define complext and fine-grained authorizetion rules
post -> .... after the method is executed

@Secured -> check based role Strings(not a sqel)
@Secure({"ROLE_USER, ROLE_ADMIN"}) 
public List<String> mycats(){....}

@AuthticationPrincipal
what is Principal
 you are an authenticated user!!!
 Authtication <- object -> username, user ID, user role, .....

Inject the current principal ->  you loged in -> current principal ->  you use it for a method
@Getmapping("/me")
public UserDetials getCurrentUser(@AuthenticationPrincipal UserDetails currentUser){....}


who will do Authentication: Authentication manager
Authentication manager: the primary interface for handling authentication
providerManager : implements authentication manager
AuthenticationProvider :  a component that performs the actual authentication logic

Authorization manager -> will do authorization 
made desicion based on the current user 's authentication and the protected resources being accessed
	web level -> URL-Level
	method


filter chain proxy!!
it does not do any logic thing, it is a proxy,
chianing function. thismyfirstfilter.{}.dofilter.{}.....
filter chain is core entry point

client -> requet -> filter(ftiler chain proxy -> logout filter, cors filter, security context manager filter, exception handling filter...) -> servlet -> logged in 


JWT -> www.jwt.io ->asldkjfiuiq23rqw.aiudff983rh2i3.219q18e4u98qadf
header.payload.signature
Header -> typ(type) -> JWT and alg(algorithm) -> RSA, SHA....

payload -> user info 
	-> cliams -> statement about an entity(user)
	-> refistered cliams, private cliams, public claims -> user info

signature: 
	ensures the message hasnot been tampered
	sig(alg(base64encoder(header)) + "." alg(base64encoder(payload)), secret)


where is jwt stored?
Local storage 
httponly
http header



					client									 				server
step 1		log in -> input username/ password										authtication and authorization and generating tokens(jwt and refresh token)
step 2										< - return tokens to client
step 3		user is accessing something   -> carry with tokens 				 token 过期？ expiry no
											<- if not, you can access it
step 3		user is accessing something ->  carry with tokens				JWT (short lived token)token expiry? yes
											<-if yes, mention your token is expiry 
											carry refresh token   -> 		validate refresh token -> no
step 4	    user store it								<- new jwt token and old refresh token

step 5 									refresh token and jwt token 过期 -》  	generating new refresh token and jwt token
										<- return tokens


how to do jwt validation


step 1: token extraction
			http header -> authorication: Bearer <token> 
			to extract the token string and ready for parsing
step 2: JWT token parsing and signature validation (the critical stpe)
		A jwt token parser(read, validation and decode your jwt)
		signature -> if key mismatch and token modification, the parsing fails -> signature exception
						if all good, -> read header and payload 
step 3: check claims -> exp if it is expired -> expired exception 

step 4: custom business logic...

jwt is integrated with spring security 

custom filter -> jwt filter


why not session? 

the general steps for session 
1: the user sends username and password to the server
2: after successful validation, the server SAVES relevant data within the current conversation (session)
3: the server returns a session id to the user and writes it into the user's cookie
4: the user subsequent requests will passs the session id back to the server via cookie
5: the server will receive the session id and do validation

session has state
jwt is stateless 

session is is stored in cookie -> risk of CSRF
session lacks scalability 

*/
@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
/*
	gmail -> marketing -> pengfei.yao@antra.com -> google chat 
	download google chat and google doc 

	resume -> java (8, 17, 21) spring boot 2.7 + spring cloud + data base (relational , non relational (not must)) + S3 (must) + monitoring(aws cloud watch)
		+testing(junit, mokito )   + aws services  + personal exp (python or AI,DL, RL, ...) + frontend(react,  angular.. must)
	
	-> link -> submit your resume -> hard to modify 
	-> marketing name -> matthew yao ... and marketing email, marketing phone number -> location, city + sate.... edu.... 

	design doc -> 


	kafka: walmart 

	what is message queue?
		producer -> data (message) -> queue(store message(s)) -> comsumer 
		allows diff services communicate with each other and exchange messages(data) asynchronously 
	
		service A -> producer -> can produce messsage 100 meesages / s
	
		service B -> comsumer -> can consume message 30 messages / s

		if u do not want lost your messasge sent from service A, you have to to put something between A and B.  -> messaging queue

	

High performance -> 
		producer 1		-> [queue] -> consumer 1, 2....
		producer 2

		problem -> as the # of producer and consumer increase, we can find that consumers compete for the same messasge at the same time

		producer 1		-> [topic1 ,2] -> consumer 1, 2....
		producer 2

		you have different services -> producer message and insert into topic 1 [ high pressure] -> how to solve high pressure on topic 1


		partition -> load balancing +提高吞吐量
				topic -> partition 1 -> [message 1,2,3]. -> consumer 1
						-> partition 2 ->[message 4,5,6] ->  consumer 3 
						-> partion 3 ->[message 7,8,9]  -> consumer 4..
						...

	paritions are running in different computer -> high scalability 

	
	high availability:
		partition 1 -> leader
		partition 2 and 3 .... replicas 
	
	
	
	
	producer -> kafka cluster[broker0 -> [topicA-partition0 (leader), ], broker1->[topic A - parition1], broker2 ->[topic partition2] ] ->consumer group[consumer1, 2,..]

	what is consumer group
	one or more than one consumer(s) -> services or instances that work together to consume message from a topic
		each message in a topic is processed by only ONE consumer within the group -> offset -> commit 
		if there are multiple consumer groups, each group gets its own copy of me messages -> offset
	offset -> message index -> __consumer_offsets -> integer -> the position of a message in the partition log 

	how consumers consume message
		1: when the consumer starts -> it tell kafka -> hi I want to subscribe to these topics
		2: send request to the broker -> do you have new message for me -> continuously send reuqests.
		3: the broker decides how much data to return each time according the settings:
			min bytes -> data size before responding
			max ms -> max wait time 
			max byte: max data size per fetch
		4: message enter local queue
				the broker's response is first placed into an internal sumer queue , act like a buffer
		5: the application -> api -> poll() method -> pyll amount of messages from buffer


		paritioning strategy: 
			1: if a partition is specified in the record, use this partition
			2: if no partition is specified but a key is present  choose a partition based hashing of key and mod operation
			3: if not partition and key is present choose the partition based on round robin strategy


		message deliver strategy:
			acks = 0 producer does not wait for any ack, 
			acks =1 produer waits for ack from the leader only
			acks = all or -1 : producer waits until the leader and all followers acknowledge the message 

			1: at most once:消息最多递交和处理一次
			
			2: at least once ： 消息至少发送一次

			3:excatly once ： 消息只能被递交一次
				idempotence : PID -》
			
			

	
*/


/*
docker and k8s
isolation and restriction

namespace - isolation : container A cannot see container B's process, files and network....

cgroups - restriction
	used to control how much CPU, memory, disk IO, networking bandwith.... a container can use


	three main components
	Image -> special type of filesystem
		an operating system -> kernel and user space
			kernel -> hardware
			user space - where application runs
		kernel -> root filesysmtem -> user space
		an image  includes: programs, lib, resources, config..., env variabels..... volumes
		an image contians no dynamic data,  and it is read only and does not change once its built

	container :
	runtime instance of an image
	in java: class and object. class = image, and object = container 
	you can create and stop and delete and pause, just like process 

	repository
	a centralized place to store docker images
	docker hub -> docker registry 
	<repository> : <tag>
	mysql : 5.1.2 -> mysql version 5.1.2



docker file
text file -> instructions  -> how to build a docker image
docker pull, docker run docker push 

volumes -> data storage area managed by docker
shared and reusable: a volume can be mounted to multiple contianers and allowing data to be sahred across them.

bind mounts vs volumes
bind mounts is managed by op, and volumes is managed by docker

docker compose
yaml -> version : "3.0"
services: 
	web:
		ports:
			-"8080":"3000"

	db:
		image: portgres:16
		environment:
				user: postgres
				password:123
		volumes:
			- "your path"

docker compose 


K8s:
	Node: fundamental compute unit 
	type: control plane node(master node) and worker node

	worker node: components
		kubelet: primary agent on every worker node. it can communicates with api server in master node and send commands to continer runtime.
		kube proxy: ip tables - used to forward traffic to where you want to communicate
		container runtime - responsible for runnning containers..

		kubelet - manager, overseeing pod lifecycle and status and communication with api server and send commands
		container runtime - it is the executor
		kube proxy  - networking coordinator managing service routing and load balancing


		master node
		api server - it is the single entry point for communication for all cluster components and external user( you )
		it handles rest requests, vlaidates api object .... for the cluster

		etcd - key value store and used to save ALL state and configuration data for ENTIRE k8s cluster..

		scheduler - respnosible for assigning newly created pods and node in the cluter. 
		
		controller  manager - it controls all controllers - > node controler, repliucaset controller endpoint....


		

		kubectl exec -it ngix:1.2
		general steps ->
		1 kubectl -> api server. before api server sends this command, we need to do authentication and authorization.
		2: api server forwards to node (api server -> kubelet)
		3: kubelet communicates with container runtime(kubelet -> runtime)
		4: runtime launches process (runtime -> container)
		5: data forward back (container -> kubelet -> api server -> your terminal )

		communication in k8s:
		1: control plane communication
				master node's components -> https/restful api
		2: data plance communication 
			between pods- > tcp

		

YAML
	apiVersion
		api group and version that a k8s resources use
		pod -> V1
		service -> V1
		deployment apps/V1

	kind
		pod
		service
		deployment

	metadata
		name UID, namespace -> all about description about the resouces
		labels are used identifu k8s resources
		does not change any behaves about your resources
			labels:
				app: antra-service
				env: prod
				version 2.1
		kubeclt get pods -l app = antra-service
		..... env = prod

		spec
		defines the desired state ofo the resource
			how many replicas you want
			which container image a pod should run
			... bahaves
			resource limit -> cpu memory.
		
		namespace : isolation
			kubectl get pods -> no resources found in defulat namespace.
			kubectl get namespace
				defualt, kube-node, kube public, kube-system

			
	
		liveness and readiness in k8s

			liveness: checks if the container is alive, if the not, k8s consider the contianer to be in unusable state

			readiness: checks if the container is ready


			spec:
				livenessProbe:
					httpGet:
						path: /health/live
						port: 8080
					initialDelaySeconds: 15 启动contianer之后 15 只后开始启动liveness 检查
					periodSecond：20  每20秒检查一次
					failureThreshold： 3， 连续三次失败后重启contianer。。

				readinessProbe


	volume:
		如果pod重新启动，但是你的volume 不会被删除，pod 的bahaves 还是一样的
		但是如果你的pod 被彻底删除，相对应的volume 才会被销毁

		一个pod可以有多个volume

	
		

*/