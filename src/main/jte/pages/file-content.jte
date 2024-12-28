@param String fileName
@param String fileType
@param String content
@param String base64Content
@param String mimeType
@param String programmingLanguage

@template.layout.main(isUserLogged = true, appName = fileName,content = @`
@if("text".equals(fileType))
    <pre>${content}</pre>

@elseif("code".equals(fileType))
    <pre class="w-100">
        <code style="background-color: white">${content}</code>
    </pre>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/default.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script>hljs.highlightAll();</script>
@elseif("image".equals(fileType))
    <div>
        <img class="image-zoom" id="image" src="data:${mimeType};base64,${base64Content}" alt="${fileName}">
    </div>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.10.5/viewer.min.js"
        integrity="sha512-i5q29evO2Z4FHGCO+d5VLrwgre/l+vaud5qsVqQbPXvHmD9obORDrPIGFpP2+ep+HY+z41kAmVFRHqQAjSROmA=="
        crossorigin="anonymous"
        referrerpolicy="no-referrer"
    ></script>
    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.10.5/viewer.css"
        integrity="sha512-c7kgo7PyRiLnl7mPdTDaH0dUhJMpij4aXRMOHmXaFCu96jInpKc8sZ2U6lby3+mOpLSSlAndRtH6dIonO9qVEQ=="
        crossorigin="anonymous"
        referrerpolicy="no-referrer"
    />
@elseif("pdf".equals(fileType))
    <embed src="data:application/pdf;base64,${base64Content}" type="application/pdf" width="100%" height="100%" />

@else
    <p>The file format is not supported for direct viewing. Please download the file.</p>
    <a href="/files/download/${fileName}" download>Download ${fileName}</a>
@endif
`)