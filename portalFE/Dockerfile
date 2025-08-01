# --- Stage 1: Build the Vite app ---
FROM node:20-alpine AS build

WORKDIR /app

# Copy all files
COPY . .

# Install dependencies and build
RUN npm install && npm run build


# --- Stage 2: Serve with Nginx ---
FROM nginx:alpine

# Copy the built app to nginx's default static directory
COPY --from=build /app/dist /usr/share/nginx/html

# Optional: Remove default nginx config and add custom one if needed
# COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
