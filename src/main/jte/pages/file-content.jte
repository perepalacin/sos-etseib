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
    <pre class="w-100 language-${programmingLanguage}" style="background-color: white">
        <code>${content}</code>
    </pre>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/themes/prism.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/prism.min.js"></script>
@elseif("image".equals(fileType))
    <img width="100%" height="100%" src="data:${mimeType};base64,${base64Content}" alt="${fileName}" />

@elseif("pdf".equals(fileType))
    <embed src="data:application/pdf;base64,${base64Content}" type="application/pdf" width="100%" height="100%" />

@else
    <p>The file format is not supported for direct viewing. Please download the file.</p>
    <a href="/files/download/${fileName}" download>Download ${fileName}</a>
@endif
`)