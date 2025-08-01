## ⚙️ Backend Setup (Spring Boot)
Unzip file project-docker

```bash
cd portal
docker build -t user-api .
docker run -p 8080:8080 user-api


## ⚙️ Frontend Setup (React+vite+tailwnd)
Unzip file project-docker

```bash
cd user-view
docker build -t user-ui .
docker run -p 5173:80 user-ui




SWAGGER_UI -   http://{Domain}/swagger-ui


to run in local 

Unzip file project-docker

BE- open in any ide -> mvn clean intall ->run application
BE- open in any ide  -> npm i ->run application