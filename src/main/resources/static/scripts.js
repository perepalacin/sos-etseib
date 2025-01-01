const searchInput = document.getElementById('search-input');

if (searchInput) {
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
}

const imageView = document.getElementById('image');
if (imageView) {
    const viewer = new Viewer(imageView, {
        toolbar: {
            zoomIn: 4,
            zoomOut: 4,
            oneToOne: 4,
            reset: 4,
            rotateLeft: 4,
            rotateRight: 4,
            flipHorizontal: 4,
            flipVertical: 4,
            download() {
                const a = document.createElement('a');

                a.href = viewer.image.src;
                a.download = viewer.image.alt;
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            },
        },
    });
}


const toggleComments = () => {
    const comments = document.getElementById('comments-container');
    if (comments) {
        if (comments.classList.contains("expanded")) {
            comments.classList.remove("expanded");
        } else {
            comments.classList.add("expanded");
        }
    }
}

const handleOpenResponseForm = (formId, triggerButtonId) => {
    const responseForm = document.getElementById(formId);
    const triggerButton = document.getElementById(triggerButtonId);
    if (responseForm && triggerButton) {
        responseForm.classList.remove("display-none");
        responseForm.classList.add("flex");
        responseForm.classList.add("flex-column");
        triggerButton.style.display = "none";
    }
}