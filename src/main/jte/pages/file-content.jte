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
        <img id="image" src="data:${mimeType};base64,${base64Content}" alt="${fileName}">
    </div>
<%--    <img width="100%" height="100%" src="data:${mimeType};base64,${base64Content}" alt="${fileName}" />--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.11.7/viewer.min.js" integrity="sha512-lZD0JiwhtP4UkFD1mc96NiTZ14L7MjyX5Khk8PMxJszXMLvu7kjq1sp4bb0tcL6MY+/4sIuiUxubOqoueHrW4w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
@elseif("pdf".equals(fileType))
    <embed src="data:application/pdf;base64,${base64Content}" type="application/pdf" width="100%" height="100%" />

@else
    <p>The file format is not supported for direct viewing. Please download the file.</p>
    <a href="/files/download/${fileName}" download>Download ${fileName}</a>
@endif
`)