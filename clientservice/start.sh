kubectl create namespace apigateway
kubectl config set-context --current --namespace=apigateway
kubectl apply -f postgres.yaml -f config-map.yaml -f client-deployment.yaml -f auth-deployment.yaml -f client-service.yaml -f auth-service.yaml -f client-ingress.yaml -f auth-ingress.yaml
sleep 20 && kubectl apply -f initdb.yaml
