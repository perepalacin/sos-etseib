@param String backRoute
@param String previousLink

@template.layout.main(isUserLogged = true, appName = "Carpeta Buida",content = @`
        <div id="auth-card">
            <h4>Ups...</h4>
            <p>Aquesta carpeta sembla estar buida...</p>
        <p>The file format is not supported for direct viewing. Please download the file.</p>
        <a href="/files/download/${fileName}" download>Download ${fileName}</a>
        </div>
`)