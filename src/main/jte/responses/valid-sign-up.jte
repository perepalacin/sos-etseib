@param String userEmail

<div id="auth-card">
    <h1>Registrat correctament!</h1>
    <h3 class="text-center font-normal">Has sigut registrat correctament a SOS - ETSEIB en breus rebràs un correu per poder validar la teva conta.</h3>
    <p>No t'oblidis de revisar també la carpeta de SPAM.</p>
    <p style="font-size: 0.9rem"> No has rebut el correu? Demana'n un de nou</p>
    <button hx-get="/api/v1/auth/validation?email=${userEmail}" hx-swap="outerHTML" hx-target="#auth-card" hx-target-error="#auth-card" type="submit" class="btn btn-primary">
        Tornar a enviar el correu de validació
    </button>
</div>