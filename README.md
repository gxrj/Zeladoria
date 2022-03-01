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
  <li>Angular</li>
  <li>H2 database (only for development environment)</li>
  <li>PostgreSQL</li>
</ul>

### Backend structure
<span>
  
-  Currently backend uses OAuth2.1 framework hence its composed of an Authorization Server application
  and an Resource Server application. 
  
-  The Authentication proccess follows the Authorization code rule so authrorization server provides two
  login forms, one for citizens and other for public agents, they differ because they have different types
  of credentials
  
-  Authorization server runs at 8090 port
  Resource server runs at 8080 port
  
-  Authorization server makes use of new springs security's authorization-server project in version 0.2.2
  Resorce server makes use of spring-boot-starter-oauth2-resource-server's library in version 2.6.4
  
-  Both applications runs on to of Spring boot verion 2.6.4
</span>

### Frontend structure
<span>
  
-  The system will be use for two different types o user, with totally diferent flows os service, so its split
  one frontend application for citizens 'user-client' and other frontend application for public agent 'agent-client'

</span>
