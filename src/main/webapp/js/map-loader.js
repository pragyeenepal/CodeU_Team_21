var map;
var infowindow;

/**
 * Sends a request api to get the user location and the Map.
 * Then searchs nearby places for the specified place type with in the 500 meters of the users location.
 */

function initialize() {
    var response = [];
    navigator.geolocation.getCurrentPosition(function(position) {

        response['latitude'] = position.coords.latitude;
        response['longitude'] = position.coords.longitude;

        var userLocation = new google.maps.LatLng(response.latitude, response.longitude);
        map = new google.maps.Map(document.getElementById('map-canvas'), {
            center: userLocation,
            zoom: 18
        });

        var request = {
            location: userLocation,
            radius: 500,
            types: ['library'] // this is where you set the map to get related places
        };

        infowindow = new google.maps.InfoWindow();
        var service = new google.maps.places.PlacesService(map);
        service.nearbySearch(request, callback);
    });

}


/**
 * Get the location snd creates a marker for each one if it is true.
 * @param {Object} results - The location returned from the the search 
 * @param {Object} status  - returns True if the response contains a valid result.
 */

function callback(results, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
            console.log(results[i]);
        }
    }
}

/**
 * Creates a marker for each location
 * @param {Object} place - location details
 */

function createMarker(place) {
    var placeLoc = place.geometry.location;
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(place.name);
        infowindow.open(map, this);
    });
}