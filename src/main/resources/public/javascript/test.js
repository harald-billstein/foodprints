$(document).ready(function () {
  $.get("https://localhost:8443/v1/restaurants/suggestion",
      function (data, status) {
        console.log("Restaurant name: " + data.name);
        console.log("Restaurant address: " + data.address);
        console.log("Restaurant grade: " + data.grade);
      });
});
