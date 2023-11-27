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
if (!isset($_POST['username'], $_POST['password'])) {
    echo json_encode(["error" => "Missing POST parameters"]);
    exit();
}

$username = mysqli_real_escape_string($conn, $_POST['username']);
$password = mysqli_real_escape_string($conn, $_POST['password']);

$stmt = $conn->prepare("SELECT * FROM users WHERE username=?");
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

$response = [];

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $stored_password = $row['password'];

    // Trim whitespace and compare
    if (trim($password) === trim($stored_password)) {
        $response["message"] = "Login successful";
    } else {
        $response["error"] = "Password mismatch: " . $password . " vs " . $stored_password;
    }
} else {
    $response["error"] = "User not found";
}

$stmt->close();
$conn->close();

echo json_encode($response);
