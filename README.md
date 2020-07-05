# FJ33-microservices-spring-cloud
Curso FJ-33 da Caelum, do monolito aos microsserviços com java e spring cloud

## Dependência:
```
java-jdk
maven
docker
docker-compose
nodejs
npm
```

## Executando o projeto:

### Realizando o build:

#### Build do Angular:
```
#Na pasta raiz do projeto, entre na pasta do eats-ui
cd ./eats-ui 

#Instale as dependencias 
npm install 

#Realize o build do angular
npm run ng build
```

#### Build dos modulos maven:
```
#Na pasta raiz do projeto
mvn clean install package
```

#### Build dos containers:
```
#Na pasta raiz do projeto
docker-compose build
```

#### Execução dos containers:
```
#Na pasta raiz do projeto
docker-compose up
```

#### Alernativamente, execute todos os comandos de uma vez na pasta raiz do projeto:
```
 cd ./eats-ui && npm install && npm run ng build && cd .. && mvn clean install package && docker-compose build && docker-compose up
```


### Login

Administrador:
```
Login: admin
Senha: 123456
```
Restaurante:
```
Login: longfu
Senha: 123456
```


## Referencias

### Apostila Caelum (gratuita e open source) de microservices com spring cloud 
```
https://github.com/caelum/apostila-microservices-com-spring-cloud
```

