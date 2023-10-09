#Go to eureka server click on application which you want to execute  
#Standalone Apiuser  
http://host.docker.internal:59556/users/status/check

#StandAlone Account management  
http://host.docker.internal:59577/account/status/check

#ApiGateway-->ApiUser  
http://localhost:8082/USER-WS/users/status/check

#ApiGateway-->ApiAccount  
http://localhost:8082/ACCOUNT-WS/account/status/check

#ApiGateway-->using-predicate-->users  
http://localhost:8082/users/status/check

#APIGateway--> userService--> saveuser  
#This url is to save newly user in PhotoApiuser app via api gateway  
http://localhost:8082/users-ws/users/
{
    "firstName": "aaa",
    "lastName": "bbb",
    "password":"Just4@now",
    "email":"abhishek.prasad@gmail.com"
}