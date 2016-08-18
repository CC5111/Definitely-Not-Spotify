/**
 * Created by Blopa on 18-08-2016.
 */
function playSong(source){
    console.log (source)
    jQuery(document).ready(function($) {
        document.getElementById("audio-player").src = source
        document.getElementById("audio-player").autoplay = true;
        document.getElementById("audio-player").load();
    });
}
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {

        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}