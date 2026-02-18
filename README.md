# Jetlight Java HTTP Server with JWT Authentication, IoC, and Role-Based Access

This project is a lightweight, framework-free RESTful HTTP server built with **pure Java**. It includes:

- Custom IoC Container (like a mini Spring)
- Lightweight HTTP server using `HttpServer`
- JWT-based authentication (login & register)
- Role-based access control (ADMIN / USER)
- IP restriction
- Virtual threads for concurrency (Project Loom)
- JSON request/response parsing
- Dispatcher-style handler (like Spring's DispatcherServlet)

---

##  Features

-  Custom dependency injection
-  REST endpoints (`/register`, `/login`, `/tenants`, `/health`)
-  JWT Authentication
-  Secure endpoints using role-based and IP filtering
-  Concurrency with virtual threads
-  Fully extendable controller/handler structure

---

##  Technologies Used

- Java 21+
- `com.sun.net.httpserver.HttpServer`
- Virtual threads (`Executors.newVirtualThreadPerTaskExecutor()`)
- Manually written JSON parser or Jackson fallback
- No frameworks (Spring, Jakarta, etc.)

---

##  Setup

###  Requirements

- Java 21+
- Git

###  Run

```bash
git clone https://github.com/soufianebouaddis/Jetlight-Java-Server.git
cd Jetlight-Java-Server
mvn clean install
```


