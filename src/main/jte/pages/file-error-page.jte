@param String appTitle
@param String tabName

@template.layout.main(fileId = -1, isUserLogged = true, appName = tabName,content = @`
    <div class="w-100 flex flex-column gap-1 align-start" style="padding: 1rem 2rem;">
            <div class="flex flex-column gap-0.5 align-center w-100">
                <img width="300" height="300" alt="Drawing of an empty folder" src="/static/images/emptyFolder.webp" style="margin-bottom: 1rem"/>
                <h2>Ups...</h2>
                <p>El fitxer al que intentes accedir no existeix...</p>
                <a href="/files/root">
                    <button class="btn btn-primary">Torna enrere</button>
                </a>
            </div>
    </div>
`)