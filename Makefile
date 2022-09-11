.PHONY: build run db

run: 
	./gradlew bootJar
	docker compose up

build: 
	./gradlew build

restart-backend: 
	./gradlew bootJar
	docker compose restart backend

logs:
	docker compose logs -f

db: 
	docker compose exec db psql -U spring                                                                                                              


