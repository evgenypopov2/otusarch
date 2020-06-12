Схема API Gateway:
apigateway.pdf

Запуск кластера:
./start.sh

Запуск postman-тестов:
newman run apigateway.postman_collection.json

Если при тестах возникла ошибка 500, следует удалить job initdb и запустить заново:
kubectl delete job --all
kubectl apply -f initdb.yaml

После этого повторить тесты
