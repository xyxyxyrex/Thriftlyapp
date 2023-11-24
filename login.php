<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "thriftlyapp_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$username = mysqli_real_escape_string($conn, $_POST['username']);
$password = mysqli_real_escape_string($conn, $_POST['password']);

$sql = "SELECT * FROM users WHERE username='$username'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $hashed_password = $row['password_hash'];

    // Use password_verify to check if the entered password matches the hashed password
    if (password_verify($password, $hashed_password)) {
        echo "Login successful";
    } else {
        echo "Login failed";
    }
} else {
    echo "Login failed";
}

$conn->close();
