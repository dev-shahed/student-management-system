# 🔒 Security Guidelines for School Management System

## 🚨 **IMPORTANT: Environment Variables Setup**

### ⚠️ **Critical Security Changes Made:**

1. **Added `.gitignore`** to prevent sensitive files from being committed
2. **Created `.env.example`** template for new developers
3. **Updated `application.properties`** to use environment variables
4. **Added `dotenv-java` dependency** for .env file support

### 📋 **Setup Instructions:**

#### 1. **Copy Environment Template:**
```bash
cp .env.example .env
```

#### 2. **Edit .env with your actual credentials:**
```bash
nano .env  # or use your preferred editor
```

#### 3. **Never commit .env file:**
The `.env` file is automatically ignored by git and contains your real credentials.

### 🔐 **Environment Variables Used:**

| Variable | Description | Example |
|----------|-------------|---------|
| `NEON_DB_URL` | Neon PostgreSQL connection URL | `jdbc:postgresql://...` |
| `NEON_DB_USERNAME` | Database username | `your_username` |
| `NEON_DB_PASSWORD` | Database password | `your_password` |
| `JWT_SECRET` | JWT signing secret (256-bit) | `openssl rand -hex 32` |
| `JWT_EXPIRATION_TIME` | Token expiration (milliseconds) | `86400000` (24 hours) |
| `SPRING_SECURITY_USERNAME` | Admin username | `admin` |
| `SPRING_SECURITY_PASSWORD` | Admin password | `secure_password` |

### 🛡️ **Security Best Practices:**

#### ✅ **DO:**
- Use strong, unique passwords
- Generate secure JWT secrets: `openssl rand -hex 32`
- Use different credentials for development/production
- Regularly rotate JWT secrets
- Use HTTPS in production
- Enable database SSL (sslmode=require)

#### ❌ **DON'T:**
- Commit `.env` files to git
- Share credentials in chat/email
- Use default passwords in production
- Store secrets in code comments
- Use weak JWT secrets

### 🔄 **Credential Rotation:**

1. **Database Credentials:**
   - Generate new password in Neon console
   - Update `.env` file
   - Restart application

2. **JWT Secret:**
   ```bash
   openssl rand -hex 32
   # Copy output to JWT_SECRET in .env
   ```

3. **Application Restart:**
   ```bash
   # Kill current application
   pkill -f "java.*smsystem"
   
   # Rebuild and restart
   cd smsystem-backend
   mvn clean package -DskipTests
   java -jar target/smsystem-0.0.1-SNAPSHOT.jar
   ```

### 📁 **Files Protected by .gitignore:**

- `.env` - Environment variables
- `*.env` - All environment files
- `application-prod.properties` - Production configs
- `application-local.properties` - Local configs
- `target/` - Maven build artifacts
- `node_modules/` - NPM dependencies
- `*.log` - Log files
- IDE files (`.idea/`, `.vscode/`)

### 🚀 **Production Deployment:**

1. **Server Environment Variables:**
   ```bash
   export NEON_DB_URL="your_production_url"
   export NEON_DB_USERNAME="your_production_user"
   export NEON_DB_PASSWORD="your_production_password"
   export JWT_SECRET="your_256_bit_secret"
   ```

2. **Docker Environment:**
   ```dockerfile
   ENV NEON_DB_URL=${NEON_DB_URL}
   ENV NEON_DB_USERNAME=${NEON_DB_USERNAME}
   ENV NEON_DB_PASSWORD=${NEON_DB_PASSWORD}
   ENV JWT_SECRET=${JWT_SECRET}
   ```

3. **Cloud Deployment:**
   - AWS: Use Parameter Store or Secrets Manager
   - Heroku: Use Config Vars
   - Azure: Use Key Vault
   - Google Cloud: Use Secret Manager

### 🆘 **Emergency Procedures:**

#### If credentials are compromised:
1. **Immediately change all passwords**
2. **Generate new JWT secret**
3. **Revoke all existing JWT tokens**
4. **Review access logs**
5. **Update .env file**
6. **Restart application**

#### If .env was accidentally committed:
1. **Remove file from git history:**
   ```bash
   git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch .env' --prune-empty --tag-name-filter cat -- --all
   ```
2. **Change all credentials immediately**
3. **Force push to remote repository**

### 📞 **Contact:**

For security concerns or credential reset requests, contact:
- System Administrator: admin@school.com
- IT Security: security@school.com

---
**Remember: Security is everyone's responsibility! 🛡️**
