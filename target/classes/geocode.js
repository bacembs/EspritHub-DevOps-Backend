google.charts.load("current", {
    "packages":["map"],
    "mapsApiKey": "AIzaSyAgza--UtekhSDNA9ReNKZ36B8oqN24Xqk"
});

google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    $.ajax({
        url: 'http://localhost:8080/geocode?address=Tunis', // Assurez-vous que cette URL correspond à votre endpoint Spring Boot
        success: function(data) {
            // Construisez les données à partir de la réponse AJAX
            var locations = [];
            for (var i = 0; i < data.results.length; i++) {
                var lat = data.results[i].geometry.location.lat;
                var lng = data.results[i].geometry.location.lng;
                var name = data.results[i].formatted_address;
                locations.push([lat, lng, name]);
            }

            // Ajoutez l'en-tête pour le tableau de données
            locations.unshift(['Lat', 'Long', 'Name']);

            var dataTable = google.visualization.arrayToDataTable(locations);

            var map = new google.visualization.Map(document.getElementById('map_div'));
            map.draw(dataTable, {
                showTooltip: true,
                showInfoWindow: true
            });
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}
