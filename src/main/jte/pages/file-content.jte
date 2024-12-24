@param String fileName
@param String content

<!DOCTYPE html>
<html>
<head>
    <title>File Content - ${fileName}</title>
    <meta charset="UTF-8">
    <style>
        pre {
            background-color: #f5f5f5;
            padding: 15px;
            border: 1px solid #ccc;
            overflow-x: auto;
        }
    </style>
</head>
<body>
<h1>File: ${fileName}</h1>
<pre>${content}</pre>
</body>
</html>