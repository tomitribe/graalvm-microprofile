# Prometheus

## Requirements
 * Docker

## Run
For Linux
                docker run -it --name prometheus-app -p 9090:9090 -v $PWD/conf/prometheus-linux.yml:/etc/prometheus/prometheus.yml prom/prometheus

For Mac OSX

                docker run -it --name prometheus-app -p 9090:9090 -v $PWD/conf/prometheus-mac.yml:/etc/prometheus/prometheus.yml prom/prometheus

It will initialize the server at port 9000.

## Check installation

On a browser open http://localhost:9090/targets[http://localhost:9090/targets] you should be able to see both TomEE and NodJS Targets status.

