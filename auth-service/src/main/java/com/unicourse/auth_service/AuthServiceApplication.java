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
