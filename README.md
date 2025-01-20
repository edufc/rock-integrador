# rock-integrador

## pre requisitos para rodar o projeto
- java 21
- maven
- docker

# Existe um docker compose file para subir o banco de dados

## Componentes que compoe o projeto
- autenticação da api que será exposta, considerando JWT fixo
- validação dos dados da api
- tratamento de exceção customizado
- conexão banco de dados com consulta e atualização de registro
- chamada de api externa, com autenticação e tratamento de exceção
- log


## Fase 1 - Identificar

### exemplo de uma request sucesso
```
curl --location 'http://localhost:8080/pdv/identify' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{
	"id": "09719397055",
	"phone": null
}'
```

### exemplo de uma request erro de validacao campo obrigatorio
```
curl --location 'http://localhost:8080/pdv/identify' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{	
	"phone": null
}'
```


### exemplo de uma request onde a integracao externa lanca erro 401
```
curl --location 'http://localhost:8080/pdv/identify' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{
	"id": "unauthorized",
	"phone": null
}'
```

## Fase 2 - Autorizar
### exemplo de uma request sucesso com integracao externo
```
curl --location 'http://localhost:8080/pdv/authorize' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{
    "id": "46c39696-d3dc-4565-b4e7-9455df571bfa",
    "customer": {
        "id": "09719397055",
        "phone": "d"
    },
    "amount": 10.00,
    "items": [
        {
            "id": "2",
            "quantity": 2.0,
            "price": 5.0
        }
    ]
}'
```


### exemplo de uma request falha de negocio no integracao externo lanca 400
```
curl --location 'http://localhost:8080/pdv/authorize' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{
    "id": "46c39696-d3dc-4565-b4e7-9455df571bfa",
    "customer": {
        "id": "bad-request",
        "phone": "d"
    },
    "amount": 10.00,
    "items": [
        {
            "id": "2",
            "quantity": 2.0,
            "price": 5.0
        }
    ]
}'
```


## Fase 3 - Registrar
### exemplo de uma request sucesso com integracao externo
```
curl --location 'http://localhost:8080/pdv/register' \
--header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmFudGUiLCJpc3MiOiJyb2NrLWludGVncmFkb3IiLCJpYXQiOjE2ODkzNDM1OTYsImV4cCI6MTY4OTM0NzE5Nn0.zfmyUZaRfwyNs27B0TF5_jyzrPBlXm-Q7ocLnWWKhcs' \
--header 'Content-Type: application/json' \
--data '{
    "id":"46c39696-d3dc-4565-b4e7-9455df571bfa",
	"customer": {
		"id": "09719397055",
		"phone": "d"
	},
	"amount": 10.00,
	"items": [
		{
			"id": "2",
			"quantity": 2.0,
			"price": 5.0
		}
	]
}'
```
