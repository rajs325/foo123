//const URL = "https://ghibliapi.herokuapp.com/people";
const URL = "https://raw.githubusercontent.com/rajs325/foo123/master/xumo-playlist.json";

const main = document.getElementById("main");
main.innerHTML = "<p>Loading...";

fetch(URL)
  .then((response) => response.json())
  .then((playlist) => main.innerHTML = getListOfNames(playlist));

const getListOfNames = (playlist) => {
  const items = playlist
    .map((item) => `<li>${item.url}</li>`)
    .join("\n");

  return `<ul>${items}</ul>`;
};

var nextsrc = getListOfNames(playlist);
var elm = 0;

var Player = document.getElementById('Player');
Player.src = nextsrc[elm];

Player.onended = function(){
    if(++elm < nextsrc.length){         
         Player.src = nextsrc[elm];
         Player.play();
    } 
}
