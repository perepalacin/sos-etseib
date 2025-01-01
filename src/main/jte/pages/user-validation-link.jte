@param String appTitle
@param String errorMessage
@param boolean allowResendLink
@param String userEmail

@template.layout.main(fileId = -1, isUserLogged = false, appName = appTitle, content = @`
        <div id="auth-card">
            <h1>Vaja...</h1>
            <p class="text-center">${errorMessage}</p>
            @if (allowResendLink)
                <div class="flex flex-column gap-0.5 align-center">
                    <p>No has rebut el correu?</p>
                    <button hx-get="/api/v1/auth/validation?email=${userEmail}" hx-swap="outerHTML" hx-target="#auth-card" hx-target-error="#auth-card" type="submit" class="btn btn-primary">
                        Tornar a enviar el correu de validació
                    </button>
                </div>
            @endif
        </div>
`)