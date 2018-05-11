# jhipster_microservices_weddingplaner

After 'cloning' via github from initial build on MacOS X to developer-notebook CentOS 7, the initial start with consul via
'docker-compose -f src/main/docker/consul.yml up'
gives
'glob error { Error: EACCES: permission denied, scandir '/config'' on CentOS 7 notebook

after executing './gradlew' in directory 'weddingplaner' and
'yarn' and './gradlew' in directory 'gateway', I tried a 'docker logs ' where I found
'[DEBUG] http: Request PUT /v1/kv/config/*/data (153.899µs) from=172.18.0.2:48900'

With 'docker exec -it docker_consul_1 /bin/sh' I looked into '/consul/config/' where no information was
saved. On my MacOS X notebook I did the same and found nothing at all in '/consul/config/', too, but the
docker on MacOS X didn't tell me any problems with access permissions.

Permissions '/consul/config/' in docker container on MacOS X:

/consul/config # ls -alth
total 0
drwxr-xr-x    2 consul   consul         4.0K Sep 19  2017 .
drwxr-xr-x    4 consul   consul         4.0K Sep 19  2017 ..

Permissions '/consul/config/' in docker container on CentOS 7:

/consul/config # ls -alth
total 0
drwxr-xr-x    2 consul   consul         6 Sep 19  2017 .
drwxr-xr-x    4 consul   consul        32 Sep 19  2017 ..

The microservices look o.k. in 'localhost:8500' on both developer-PCs, but on CentOS 7 the './gradlew'-log for 'weddingapp'
shows 'Config Server:  Not found or not setup for this application' while on MacOS X the log shows
'Config Server: Connected to Consul Server running in Docker'. Strange for me!

