"use strict";

$(document).ready(function () {
  $.get("https://localhost:8443/v1/restaurants/suggestion",
      function (data, status) {

        $("#restaurantname").html("Name:" + data.name);
        $("#restaurantaddress").html("Address: " + data.address);
        $("#restaurantrating").append(getStars(data.grade));

      });
});

function getStars(grade) {

  var testgrade = grade;
  var ratingValue = Math.floor(testgrade);
  var returnString = "";
  // GET ALL FULL STARS
  for (var i = 0; i < ratingValue; i++) {
    returnString += '<i class="fa fa-star"></i>';
  }
  // GET HALF STARS
  var dif = (Math.ceil(testgrade) - testgrade);
  if (dif > 0) {
    returnString += '<i class="fa fa-star-half-o"></i>';
  }
  // GET EMPTY STARS
  for (var j = parseInt(dif + testgrade); j < 5; j++) {
    returnString += '<i class="fa fa-star-o"></i>';
  }
  return returnString;
}