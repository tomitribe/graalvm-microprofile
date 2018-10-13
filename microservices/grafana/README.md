# Grafana

## Requirements
 * Docker
 * Prometheus is up and running

## Run
                docker run -it --name grafana-app -p 7000:3000 grafana/grafana

It will initialize the server at port 7000.

## Configuration

### Add datasource
On a browser open http://localhost:7000/[http://localhost:7000/], username:`admin`, password: `admin`.

Open `Add Datasource option` and provide the following:

* Name: `CODEONEDEMO`
* Type: `Prometheus`
* URL: `http://host.docker.internal:9090`

Save the changes.

### Import Dashboard

Navigate to http://localhost:7000/dashboard/import[http://localhost:7000/dashboard/import] and select the file located in `/dashboard/CodeOneDashboard-1539402720889.json` in the repository.

Make sure to select `CODEONEDEMO` in  the field CodeOneDemo.

Click on `Import`.
