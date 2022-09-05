.PHONY: build run db

run: 
	./gradlew bootJar
	docker compose up

build: 
	./gradlew build

db: 
	docker compose exec db psql -U spring                                                                                                              


