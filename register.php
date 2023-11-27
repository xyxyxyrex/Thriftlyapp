<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "thriftlyapp_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["error" => "Connection failed"]);
    exit();
}

// Check if the required keys are set in the $_POST array
if (!isset($_POST['username'], $_POST['email'], $_POST['password'], $_POST['mobile_number'])) {
    echo json_encode(["error" => "Missing POST parameters"]);
    exit();
}

$username = mysqli_real_escape_string($conn, $_POST['username']);
$email = mysqli_real_escape_string($conn, $_POST['email']);
$password = mysqli_real_escape_string($conn, $_POST['password']); // Don't hash the password
$mobile_number = mysqli_real_escape_string($conn, $_POST['mobile_number']);

// Use prepared statements to prevent SQL injection
$stmt = $conn->prepare("INSERT INTO users (username, email, password, mobile_number) VALUES (?, ?, ?, ?)");
$stmt->bind_param("ssss", $username, $email, $password, $mobile_number);

$response = [];

if ($stmt->execute()) {
    $response["message"] = "Registration successful";
} else {
    if (strpos($conn->error, "Duplicate entry") !== false) {
        $response["error"] = "Email already registered";
    } else {
        $response["error"] = "Registration failed";
    }
}

$stmt->close();
$conn->close();

echo json_encode($response);
