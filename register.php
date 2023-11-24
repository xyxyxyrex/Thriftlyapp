<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "thriftlyapp_db";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check for database connection errors
if ($conn->connect_error) {
    echo "Error: Connection failed";
    exit();
}

$username = mysqli_real_escape_string($conn, $_POST['username']);
$email = mysqli_real_escape_string($conn, $_POST['email']);
$password = mysqli_real_escape_string($conn, $_POST['password']);
$confirm_password = mysqli_real_escape_string($conn, $_POST['confirm_password']);
$mobile_number = mysqli_real_escape_string($conn, $_POST['mobile_number']);

// Check if passwords match
if ($password !== $confirm_password) {
    echo "Passwords do not match";
    exit();
}

// Hash the password
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

$sql = "INSERT INTO users (username, email, password_hash, mobile_number) VALUES ('$username', '$email', '$hashed_password', '$mobile_number')";

if ($conn->query($sql) === TRUE) {
    echo "Registration successful";
} else {
    // Check for duplicate entry error
    if (strpos($conn->error, "Duplicate entry") !== false) {
        echo "Error: Email already registered";
    } else {
        echo "Error: Registration failed";
    }
}

$conn->close();
