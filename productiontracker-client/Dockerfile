# Create image based on the official Node 16 image from dockerhub
FROM node:14

# Create a directory where our app will be placed
RUN mkdir -p /app

# Change directory so that our commands run inside this new directory
WORKDIR /app

# Copy dependency definitions
#Used when building image from docker-compose build
#COPY ../productiontracker-client/package.json /app
#Used when building image from spotify
COPY ./package.json /app

# Install dependecies
RUN npm install


RUN npm install -g @angular/cli@14.0.2

# Get all the code needed to run the app
#Used when building image from docker-compose
#COPY ../productiontracker-client /app/

#Used when building image from spotify
COPY . /app/

# Expose the port the app runs in
EXPOSE 4200

# Serve the app
#CMD ng serve --host 0.0.0.0
#CMD ["npm", "start"]
CMD ["ng", "serve", "--host", "0.0.0.0"]

#FROM tomcat:9
#RUN ["apt-get", "update"]
#RUN ["apt-get", "install", "-y", "vim"]
#RUN sed -i 's/port="8080"/port="80"/' ${CATALINA_HOME}/conf/server.xml
#ADD ./dist/productiontracker-client /usr/local/tomcat/webapps/ROOT
#CMD ["catalina.sh", "run"]

