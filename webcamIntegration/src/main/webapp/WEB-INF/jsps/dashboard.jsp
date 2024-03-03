<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webcam App</title>
</head>
<body>
    <h1>Webcam App</h1>
    <form action="/webcam" method="get">
        <button type="submit">Record</button>
    </form>
    <form action="/history" method="get">
        <button type="submit">History</button>
    </form>
</body>
</html>
