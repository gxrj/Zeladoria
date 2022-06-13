### Introduction

<span>
The purpose of this project is to provide a fullstack web application 
to provide communication between Macaé's city citizens and urban agents
to ask for infrastructure services, assitance services, report irregularities/crimes,
environmental assistance, etc.
</span>

### Used Tools
<ul>
  <li>Spring boot</li>
  <li>Spring Security</li>
  <li>Angular + Ionic</li>
  <li>H2 database (only for development environment)</li>
  <li>PostgreSQL</li>
</ul>

<h5>Obs: this project still under development</h5>

### Backend structure
<span>
  
-  Currently backend uses OAuth2.1 framework hence its composed of an Authorization Server application
  and an Resource Server application. 
  
-  The Authentication proccess follows the Authorization code rule so authrorization server provides two
  login forms, one for citizens and other for public agents, they differ because they have different types
  of credentials
  
-  Authorization server's name server is configured as auth-server
  
-  Authorization server runs at 8090 port so it's url becomes http://auth-server:8090
  
-  Resource server runs at 8080 port
 
- The backend project is set to develoment environment, so h2 database is set to mixed mode
  which means its hosted at resorce server hence it has to run before authorization server application
 
-  Authorization server makes use of new springs security's authorization-server project in version 0.2.3
  Resorce server makes use of spring-boot-starter-oauth2-resource-server's library in version 2.6.7
  
-  Both applications runs on to of Spring boot verion 2.7.0
</span>

### Frontend structure
<span>
  
-  The system will be used for two different types of user, with totally diferent flows of service, so its split in
  one frontend application for citizens 'citizen-client' and other frontend application for public agent 'agent-client'
  
- Frontend for citizens will be run in desktop as well as mobile plataforms, currently using angular+ionic, at port 8100 
  
- Frontend for public agents will be run in desktop only but also uses angular+ionic at 8100 port as well
  
</span>

### Instructions to run

-  As explained in the sixth bullet point of backend structure section, resource server application has to be run before
 authorization server application
 
#### Step 1: Run Resource Server

- When inside Zeladoria's folder just run:

        ./backend/resource-server/gradlew -p ./backend/resource-server bootRun
        
#### Step 2: Run Authorization Server

- When inside Zeladoria's folder just run:

        ./backend/authz-server/gradlew -p ./backend/authz-server bootRun
        
#### Step 3: Run citizen frontend application
- Must be inside Zeladoria/frontend/citizen-client, then run:

        ng run app:serve --host=localhost --port=8100
        
#### Step 4: Run public agent frontend application
- Must be inside Zeladoria/frontend/agent-client, then run:

        ng run app:serve --host=localhost --port=8200

In the case your machine can stand all those four applications running in parallel:
- Resource Server runs at <code>8080</code>'s port
- Authorization Server runs at <code>8090</code>'s port (required for authenticated actions)
- Citizen frontend application runs at <code>8100</code>'s port
- Agent frontend application runs at <code>8200</code>'s port

If you have a modest machine you can switch between which frontend application to run by time

Warning: For anonymous actions you gonna just need Resource Server and Citizen Frontend up
 
