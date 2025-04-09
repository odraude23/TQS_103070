FROM node:22.11.0-alpine
WORKDIR /app
COPY . .
RUN npm install
RUN npm install bootstrap
RUN npm install react-icons
RUN npm install react-router-dom
EXPOSE 3030             
CMD ["npm", "run", "dev"]