kubectl create namespace cache
kubectl config set-context --current --namespace=cache
kubectl apply -f postgres.yaml -f config-map.yaml -f cache-deployment.yaml -f cache-service.yaml -f cache-ingress.yaml
