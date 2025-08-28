# School Management System

A comprehensive, full-stack School Management System built with Spring Boot and React. This system provides complete management solutions for educational institutions including student management, attendance tracking, exam management, fee management, and more.

## 🏫 System Overview

This School Management System is designed to handle all aspects of school administration, from student enrollment to academic performance tracking. It supports multiple user roles with appropriate access controls and provides a seamless experience for administrators, teachers, students, and parents.

## ✨ Key Features

### 🎓 **Academic Management**
- **Student Management:** Complete student lifecycle management with personal, academic, and guardian information
- **Teacher Management:** Teacher profiles, qualifications, and assignment tracking
- **Class & Section Management:** Organize students into classes with subject assignments
- **Subject Management:** Subject creation with teacher assignments and schedules
- **Timetable Management:** Dynamic class scheduling with period management

### 📊 **Attendance & Performance**
- **Attendance Tracking:** Daily attendance with multiple status options (Present, Absent, Late, Excused, Half-day)
- **Class-wise Attendance:** Bulk attendance marking for entire classes
- **Attendance Analytics:** Detailed attendance statistics and percentage calculations
- **Attendance Reports:** Generate comprehensive attendance reports

### 📝 **Examination System**
- **Exam Management:** Create and schedule exams with detailed configurations
- **Result Management:** Record and manage exam results with automatic grade calculations
- **Grade System:** Comprehensive grading system (A+, A, B+, B, C+, C, D, F)
- **Performance Analytics:** Class averages, top performers, and failure analysis
- **Report Cards:** Generate detailed student report cards

### 💰 **Financial Management**
- **Fee Structure:** Flexible fee management with multiple fee types
- **Payment Tracking:** Track payments, due dates, and outstanding balances
- **Payment Methods:** Support for multiple payment methods
- **Fee Reports:** Generate financial reports and receipts

### 📢 **Communication System**
- **Notifications:** System-wide notification management
- **Announcements:** Targeted announcements for specific audiences
- **Priority Levels:** Urgent, high, medium, and low priority notifications
- **Multi-audience Support:** Notifications for students, teachers, parents, and staff

### 👨‍👩‍👧‍👦 **Parent Portal**
- **Child Information:** Access to children's academic information
- **Attendance Monitoring:** View attendance records and statistics
- **Result Tracking:** Access to exam results and report cards
- **Fee Status:** Monitor fee payments and outstanding balances

### 🔐 **Security & Access Control**
- **Role-based Access Control (RBAC):** Comprehensive permission system
- **JWT Authentication:** Secure token-based authentication
- **Multiple User Roles:** Admin, Principal, Teacher, Student, Parent, Accountant, Librarian, Receptionist, Clerk
- **Secure Endpoints:** API endpoint security with role-based restrictions

## 🏗️ System Architecture

### **Backend (Spring Boot)**
- **Framework:** Spring Boot 3.2.3
- **Security:** Spring Security with JWT
- **Database:** PostgreSQL with JPA/Hibernate
- **Documentation:** Swagger/OpenAPI integration
- **Architecture Pattern:** Layered architecture (Controller → Service → Repository → Entity)

### **Frontend (React)**
- **Framework:** React with Vite
- **Styling:** Tailwind CSS
- **Build Tool:** Vite for fast development and building

## 🚀 Technologies Used

### **Backend Technologies**
- **Java 17:** Modern Java features and performance
- **Spring Boot 3.2.3:** Application framework
- **Spring Security:** Authentication and authorization
- **Spring Data JPA:** Database abstraction layer
- **PostgreSQL:** Relational database
- **JWT (JSON Web Tokens):** Secure authentication
- **ModelMapper:** Object mapping
- **Lombok:** Reduce boilerplate code
- **Swagger/OpenAPI:** API documentation

### **Frontend Technologies**
- **React:** User interface library
- **Vite:** Build tool and development server
- **Tailwind CSS:** Utility-first CSS framework
- **JavaScript/TypeScript:** Programming languages

### **Development Tools**
- **Maven:** Dependency management
- **Git:** Version control
- **Swagger UI:** API testing interface

## 📊 Database Schema

### **Core Entities**
- **Users:** Authentication and basic user information
- **Roles:** Role-based access control
- **Students:** Student personal and academic information
- **Teachers:** Teacher profiles and qualifications
- **Parents:** Parent/guardian information with child relationships
- **Classes:** Class organization and management
- **Subjects:** Subject details with teacher assignments

### **Academic Entities**
- **Attendance:** Daily attendance tracking
- **Exams:** Exam schedules and configurations
- **ExamResults:** Student exam results and grades
- **Timetable:** Class schedules and periods

### **Administrative Entities**
- **Fees:** Fee structure and payment tracking
- **Notifications:** System notifications and announcements

## 🔑 User Roles & Permissions

### **ADMIN**
- Full system access
- User management
- System configuration
- All CRUD operations

### **PRINCIPAL**
- Academic oversight
- Teacher management
- Student management
- Exam management
- Financial oversight

### **TEACHER**
- Student management (assigned classes)
- Attendance marking
- Exam creation and evaluation
- Result entry
- Communication with parents

### **STUDENT**
- View personal information
- Check attendance records
- View exam results
- Access timetables
- Receive notifications

### **PARENT**
- View children's information
- Monitor attendance
- Check exam results
- Fee status monitoring
- Receive notifications

### **ACCOUNTANT**
- Fee management
- Payment processing
- Financial reports
- Transaction tracking

### **Support Roles**
- **Librarian:** Library management
- **Receptionist:** Basic information access
- **Clerk:** Administrative tasks

## 🛠️ Setup and Installation

### **Prerequisites**
- Java 17 or higher
- Node.js 16 or higher
- PostgreSQL 12 or higher
- Git

### **Backend Setup**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/dev-shahed/student-management-system.git
   cd student-management-system/smsystem-backend
   ```

2. **Configure the database:**
   - Create a PostgreSQL database named `sms-postgres`
   - Update `application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sms-postgres
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build and run the application:**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run 
   or ./start.sh
   ```

   The backend will start on `http://localhost:8080`

### **Frontend Setup**

1. **Navigate to frontend directory:**
   ```bash
   cd ../smsystem-frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

### **Database Initialization**

The system includes automatic data initialization that will:
- Create required roles (ADMIN, PRINCIPAL, TEACHER, STUDENT, PARENT, etc.)
- Set up basic system configurations

## 📖 API Documentation

### **Swagger Documentation**
Access the complete API documentation at: `http://localhost:8080/swagger-ui`

### **Main API Endpoints**

#### **Authentication**
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

#### **Student Management**
- `GET /api/students` - Get all students
- `POST /api/students` - Create new student
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

#### **Attendance Management**
- `POST /api/attendance/mark` - Mark attendance
- `POST /api/attendance/mark-class/{classId}` - Mark class attendance
- `GET /api/attendance/student/{studentId}` - Get student attendance
- `GET /api/attendance/stats/student/{studentId}` - Get attendance statistics

#### **Exam Management**
- `POST /api/exams` - Create exam
- `GET /api/exams` - Get all exams
- `GET /api/exams/class/{classId}` - Get class exams
- `GET /api/exams/upcoming/class/{classId}` - Get upcoming exams

#### **Exam Results**
- `POST /api/exam-results` - Add exam result
- `GET /api/exam-results/exam/{examId}` - Get exam results
- `GET /api/exam-results/student/{studentId}` - Get student results
- `GET /api/exam-results/student/{studentId}/report-card` - Generate report card

## 🔧 Configuration

### **Application Properties**
Key configuration properties in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/sms-postgres
spring.datasource.username=postgres
spring.datasource.password=your_password

# JWT Configuration
app.jwt-secret=your-jwt-secret-key
app.jwt-token-expiration-time=86400000

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true

# Logging Configuration
logging.level.org.springframework.security=DEBUG
```

## 🚀 Development

### **Project Structure**
```
smsystem-backend/
├── src/main/java/com/smsytem/students/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA entities
│   ├── exception/      # Custom exceptions
│   ├── repository/     # Data repositories
│   ├── security/       # Security configuration
│   └── service/        # Business logic services
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

### **Code Quality Standards**
- **Documentation:** All classes and methods include comprehensive JavaDoc
- **Error Handling:** Consistent exception handling with custom exceptions
- **Security:** Role-based access control on all endpoints
- **Validation:** Input validation on all DTOs
- **Testing:** Unit and integration tests (recommended to add)

## 🔒 Security Features

### **Authentication & Authorization**
- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control (RBAC)
- Secure endpoint protection
- Token expiration handling

### **Data Protection**
- Input validation and sanitization
- SQL injection prevention through JPA
- XSS protection
- CSRF protection

## 📈 Performance Features

### **Database Optimization**
- Efficient JPA queries
- Lazy loading for relationships
- Database indexing on key fields
- Connection pooling

### **Application Performance**
- Stateless architecture
- Efficient caching strategies
- Optimized API responses
- Modular design for scalability

## 🧪 Testing

### **Recommended Testing Strategy**
- **Unit Tests:** Service layer business logic
- **Integration Tests:** Repository layer database operations
- **End-to-End Tests:** Complete API workflow testing
- **Security Tests:** Authentication and authorization

### **Test Frameworks**
- JUnit 5 for unit testing
- Spring Boot Test for integration testing
- TestContainers for database testing
- MockMvc for controller testing

## 🚀 Deployment

### **Production Deployment**
1. **Build the application:**
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Docker deployment (optional):**
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY target/smsystem-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```

3. **Environment-specific configuration:**
   - Use Spring profiles for different environments
   - External configuration for database credentials
   - SSL/TLS configuration for production

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Support

For support and questions:
- Create an issue on GitHub
- Contact the development team
- Check the documentation and API reference

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- React community for frontend best practices
- PostgreSQL for reliable database management
- All contributors who help improve this system

---

**Built with ❤️ for educational institutions worldwide**
