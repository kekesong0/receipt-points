# Receipt Points API

This is a Spring Boot application that calculates reward points for receipts based on a set of predefined rules.

---

## ✨ Features

- `POST /receipts/process`: Submit a receipt and receive a unique ID.
- `GET /receipts/{id}/points`: Retrieve reward points using the receipt ID.
- Dockerized with multi-stage build for easy deployment.

---

## 🛠 Technologies Used

- Java 17
- Spring Boot 3.x
- Gradle
- Docker

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Gradle
- Docker
- (Optional) Postman for API testing

---

## 🔧 Run Locally

### 1. Clone the project

```bash
git clone https://github.com/kekesong0/receipt-points.git
cd receipt-points
```

### 2. Run the application

```bash
./gradlew bootRun
```

The app will start at: `http://localhost:8080`

### 3. Run tests

```bash
./gradlew test
```

---

## 🐳 Run with Docker

This project supports Docker multi-stage builds.

### 1. Build Docker image

```bash
docker build -t receipt-points-app .
```

### 2. Run the container

```bash
docker run -p 8080:8080 receipt-points-app
```

You can access the app at: [http://localhost:8080](http://localhost:8080)

---

## 📨 API Endpoints

### `POST /receipts/process`

- **Description**: Submit a receipt and get back an ID.
- **Request Body (JSON)**:
```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
```

- **Response**:
```json
{
  "code": 200,
  "message": "success",
  "data": "2dd03ef4-0d5b-44b7-955a-7440f80c2116"
}
```

---

### `GET /receipts/{id}/points`

- **Description**: Retrieve points for a specific receipt ID.
- **Response**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "points": 109
  }
}
```

---

## 🧾 Receipt Scoring Rules

- ✅ 1 point for every alphanumeric character in the retailer name
- ✅ 50 points if the total is a round dollar amount
- ✅ 25 points if the total is a multiple of 0.25
- ✅ 5 points for every 2 items
- ✅ If trimmed description length is a multiple of 3, `price × 0.2`, rounded up
- ✅ +6 points if purchase day is odd
- ✅ +10 points if time is between 2:00 PM and 4:00 PM

---

## 🗂 Project Structure

The directory tree of the project is as follows:

```
receipt-points/
├── src/
│   └── main/
│       └── java/
│           └── com.kekesong.receiptpoints/
│               ├── common/
│               │   └── BaseResponse.java
│               ├── controller/
│               │   └── ReceiptController.java
│               ├── pojo/
│               │   ├── Item.java
│               │   └── Receipt.java
│               ├── service/
│               │   ├── ReceiptService.java
│               │   └── impl/
│               │       └── ReceiptServiceImpl.java
│               └── ReceiptPointsApplication.java
├── build.gradle
├── Dockerfile
├── README.md
├── gradlew / gradlew.bat
└── settings.gradle
```

---

## 📌 Additional Notes

If you're running this project using Docker, no extra setup is required as long as your machine has Docker installed.

---

## 👤 Author

**Keke Song**  
University of Wisconsin-Madison  
📧 songkeke702@gmail.com
