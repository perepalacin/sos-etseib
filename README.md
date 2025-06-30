# 📊 SOS-ETSEIB

An open-source file repository for university students to share, rate, and comment on files, aiding exam preparation. Developed with Java without a framework, PostgreSQL, and Cloudflare R2 for file storage. The front end is server-side generated using Java, JTE, and HTMX.

## 📦 Technologies

- `Java`
- `Maven`
- `HTMX`
- `PostgreSQL`
- `Cloudflare R2 / AWS S3`
- `JavaScript`

## 🚀 Features

- **Authentication**: Custom session based auth system with HttpOnly cookies, including email verification and account recovery.
- **File Uploads**: Users can upload files directly to a public Cloudflare R2 Bucket.
- **Social Interaction**: Students can rate, comment, and discuss files, enhancing collaboration.

## 📚 Learnings

### Spring Boot Insights

- Gained understanding of Spring Boot internals by replicating key features.

### Session Based Auth System

- Implemented an auth system based on sessions stored in a DB to emulate a scalable system based on microservices to authenticate users using multiple distribution servers.

### Java Templating Engine

- Explored backend HTML templating for crafting efficient static sites.

### HTMX

- Experimented with this UI Library for dynamic, interactive front ends.

## 🚦 Running the Project

To set up locally:

0. **Configure .env file**:
```plaintext
DB_USERNAME=db_username
DB_PASSWORD=db_password

EMAIL=admin_email@email.com
GMAIL_PASSWORD=email_password
GMAIL_APP_PASSWORD=gmail_app_password

JWT_SECRET=jwt_secret

CF_READ_ONLY_TOKEN=cloudflare_read_only_token
CF_ACCOUNT_ID=cf_account_id
CF_ACCESS_KEY=cf_access_key
CF_SECRET_KEY=cf_secret_key
CF_ENDPOINT=cf_endpoint
CF_BUCKET_NAME=cf_bucket_name
```

1. Clone the repository to your local machine.
2. Run `.mvn clean install` to install backend dependencies.
3. Build and run the project using Maven or IntelliJ tools.
4. Open [http://localhost:8080](http://localhost:8080) in your web browser to view the app.

## 🎥 Video

Watch the demonstration here: [Video](https://youtu.be/7BXdHiNw73k)
