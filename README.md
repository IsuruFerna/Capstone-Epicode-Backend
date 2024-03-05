# Social Media App 🚀

This is a social media app where users can:

- 📝 Register, log in, and post text and image content
- 👥 Add friends and follow other users
- 💬 Chat with follow-back users in real-time

This application uses:

- ⚛️ React for Front-end
- ☕ Spring-boot + Postgresql for the backend
- 🕸️ Websockets for the chat system

The UI is completely designed by the author with Adobe XD. 🎨

There are two separate git repositories for each front-end and backend:

- 🌐 Front-end repo: https://github.com/IsuruFerna/capstone-Epicode-Front-end
- 🗄️ Back-end repo: https://github.com/IsuruFerna/Capstone-Epicode-Backend



## 🗄️ Back-end 
### Requirements 📋

To run this project, you need to have **Java** and **Maven** installed on your machine.

Then, you need to clone the back-end repository to your local folder. You can use the following command in your terminal:

`git clone https://github.com/user/social-media-app-backend.git`

Next, you need to create a `env.properties` file in the **root folder** of your back-end project and add the following environment variables. Use the `env.example` file as a reference:

- `PG_PASSWORD`: The password of your PostgreSQL database
- `PG_USERNAME`: The username of your PostgreSQL database
- `PG_DB_NAME`: The name of your PostgreSQL database
- `SERVER_PORT`: The port number of your back-end server
- `CLOUDINARY_NAME`: The name of your Cloudinary account
- `CLOUDINARY_API_KEY`: The API key of your Cloudinary account
- `CLOUDINARY_SECRET`: The secret key of your Cloudinary account
- `MAILGUN_API_KEY`: The API key of your Mailgun account
- `MAILGUN_DOMAIN_NAME`: The domain name of your Mailgun account
- `JWT_SECRET`: The secret key for generating JSON Web Tokens

You can get these values from your respective service providers.

Finally, you need to build and run the project. 🚀

**Note**: The CORS origin for both REST API and websockets are set to `http://localhost:3000`, which is the default port for the React development server. This means that your back-end server will only accept requests from your front-end application running on `http://localhost:3000`, and reject any other requests from different origins. 🚫

## REST API Docs 📄

This project provides a comprehensive documentation of the REST API requests and responses using **Swagger UI** and **OpenAPI**.

- `http://your-server/swagger-ui/index.html`: This will show you a user-friendly interface where you can view and test the available endpoints, parameters, and responses. 🖥️
- `http://your-server/v3/api-docs`: This will show you a JSON file that contains the specifications of the API according to the OpenAPI standard. 🗂️

You can replace `your-server` with the actual server link of your back-end application, which you can get from your hosting service or your local machine. If you are running the back-end locally, you can use `http://localhost:8080` or whatever port you are using.

# 🖊️ Author
Isuru Madhushan Fernando Warnakulasuriya Mahalekamge

