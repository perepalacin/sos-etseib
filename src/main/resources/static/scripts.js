const searchInput = document.getElementById('search-input');

let searchInputValue = "";
if ( window.location.search.length > 0) {
    searchInputValue = window.location.search.replace("?search=", "");
    searchInput.innerText = searchInputValue;
}

let debounceTimer;
searchInput.addEventListener('input', function() {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(function() {
        const value = searchInput.value;
        if (value.trim() !== '') {
            window.location.href = window.location.origin + window.location.pathname + '?search=' + encodeURIComponent(value);
        }
    }, 300);
});