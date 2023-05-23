# Getting Started

### User guide

#### how to start the db?
`cd docker &&`
`docker-compose -f mysql.yml up`

### USER AUTH

the sequence of the user operation should be like

register -> query -> update -> delete

mapping to the API in our design should be:

register:  /user/add
query: /user/get   or /user/get/name
update: /user/update
delete: /user/delete

for all the APIs above, except for the register API without auth, the rest all have the auth verification
which means, only after login, the user can only operate with his/her own account

## TODO
we will use more advanced auto method in the future like JWT token based SSO.

the followers impl will do in the future too.


